package xyz.tantaihaha.autoItemInActionbar.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.tantaihaha.autoItemInActionbar.utils.FindAndRemoveStack;
import xyz.tantaihaha.autoItemInActionbar.utils.SendRefillFeedback;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static xyz.tantaihaha.autoItemInActionbar.core.Refill.isBucketType;

public class Bucket {
    private static Plugin plugin;
    private static final Set<UUID> processing = new HashSet<>();
    public Bucket(Plugin plugin) {
        Bucket.plugin = plugin;
    }

    /**
     * Refills the player's held item based on the current item in hand.
     * Handles bucket refills specifically for water and lava buckets.
     *
     * @param player The player whose inventory will be refilled.
     */
    public static void refillBucket (Player player) {
        if (processing.contains(player.getUniqueId())) {
            return;
        }
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack usedItem = player.getInventory().getItem(slot);
        Material usedType = (usedItem != null && usedItem.getType() != Material.AIR) ? usedItem.getType() : null;

        // Only handle bucket types
        if (usedItem == null || !isBucketType(usedType)) return;

        processing.add(player.getUniqueId());
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            try {
                ItemStack currentItem = player.getInventory().getItem(slot);

                // If player filled a bucket (now holding water/lava/milk/powder_snow bucket)
                if (usedType == Material.BUCKET && currentItem != null &&
                        (currentItem.getType() == Material.WATER_BUCKET || currentItem.getType() == Material.LAVA_BUCKET
                                || currentItem.getType() == Material.MILK_BUCKET || currentItem.getType() == Material.POWDER_SNOW_BUCKET)) {
                    ItemStack refill = new FindAndRemoveStack(player, Material.BUCKET, usedType.getMaxStackSize(), slot).getRefillStack();
                    if (refill != null) {
                        player.getInventory().setItem(slot, refill);
                        player.getInventory().addItem(new ItemStack(currentItem.getType()));
                        new SendRefillFeedback(player);
                    }
                    return;
                }

//            // If player emptied a water bucket (now holding air or empty bucket), refill entire stack
                if ((usedType == Material.WATER_BUCKET || usedType == Material.LAVA_BUCKET || usedType == Material.POWDER_SNOW_BUCKET) &&
                        (currentItem == null || currentItem.getType() == Material.AIR || currentItem.getType() == Material.BUCKET)) {
                    ItemStack refill = new FindAndRemoveStack(player, usedType, 1, slot).getRefillStack();
                    if (refill != null) {
                        player.getInventory().setItem(slot, refill);
                        player.getInventory().addItem(new ItemStack(Material.BUCKET));
                        new SendRefillFeedback(player);
                    }
                }
            } finally {
                processing.remove(player.getUniqueId());
            }
        }, 1L);
    }
}