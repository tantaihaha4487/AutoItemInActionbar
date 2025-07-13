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

public class Item {
    private static Plugin plugin;
    private static final Set<UUID> processing = new HashSet<>();
    public Item(Plugin plugin) {
        Item.plugin = plugin;
    }


    /**
     * Refills the player's held item based on the current item in hand.
     *
     * @param player The player whose inventory will be refilled.
     */
    public static void refillItem(Player player) {
        if (processing.contains(player.getUniqueId())) {
            return;
        }
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack usedItem = player.getInventory().getItem(slot);
        Material usedType = (usedItem != null && usedItem.getType() != Material.AIR) ? usedItem.getType() : null;
        int maxStack = (usedType != null) ? usedType.getMaxStackSize() : 64;

        // Check if the used item is a bucket return
        if (usedItem == null || isBucketType(usedType)) return;
        // If the used item is armor, should not refill
        if (usedItem.getType().name().endsWith("_HELMET") || usedItem.getType().name().endsWith("_CHESTPLATE")
                || usedItem.getType().name().endsWith("_LEGGINGS") || usedItem.getType().name().endsWith("_BOOTS")) return;


        processing.add(player.getUniqueId());
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            try {
                ItemStack currentItem = player.getInventory().getItem(slot);

                if (currentItem == null || currentItem.getType() == Material.AIR || currentItem.getAmount() == 0) {
                    if (usedType != null) {
                        ItemStack refill = new FindAndRemoveStack(player, usedType, maxStack, slot).getRefillStack();
                        if (refill != null) {
                            player.getInventory().setItem(slot, refill);
                            new SendRefillFeedback(player);
                        }
                    }
                }
            } finally {
                processing.remove(player.getUniqueId());
            }
        }, 1L);
    }


    /**
     * Refills the player's held item when an item is dropped.
     *
     * @param player The player who dropped the item.
     * @param dropped The item that was dropped.
     */
    public static void refillOnDropItem(Player player, ItemStack dropped) {
        if (processing.contains(player.getUniqueId())) {
            return;
        }
        int slot = player.getInventory().getHeldItemSlot();
        // Only refill if the dropped item was from the held slot
        ItemStack handItem = player.getInventory().getItem(slot);
        if (handItem == null || handItem.getType() == Material.AIR) {
            Material dropType = dropped.getType();
            int maxStack = dropType.getMaxStackSize();
            processing.add(player.getUniqueId());
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                try {
                    ItemStack refill = new FindAndRemoveStack(player, dropType, maxStack, slot).getRefillStack();
                    if (refill != null) {
                        player.getInventory().setItem(slot, refill);
                        new SendRefillFeedback(player);
                    }
                } finally {
                    processing.remove(player.getUniqueId());
                }
            }, 1L);
        }
    }
}

