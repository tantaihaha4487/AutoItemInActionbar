package xyz.tantaihaha.autoItemInActionbar.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.tantaihaha.autoItemInActionbar.utils.FindAndRemoveStack;
import xyz.tantaihaha.autoItemInActionbar.utils.SendRefillFeedback;

public class Item {
    private static Plugin plugin;

    public Item(Plugin plugin) {
        Item.plugin = plugin;
    }


    /**
     * Refills the player's held item based on the current item in hand.
     *
     * @param player The player whose inventory will be refilled.
     */
    public static void refill(Player player) {
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack usedItem = player.getInventory().getItem(slot);
        Material usedType = (usedItem != null && usedItem.getType() != Material.AIR) ? usedItem.getType() : null;
        int maxStack = (usedType != null) ? usedType.getMaxStackSize() : 64;

        // Check if the used item is a bucket return
        if (usedItem == null || (usedItem.displayName().toString().contains("Bucket"))) return;

        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
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
        }, 1L);
    }

    public static void refillOnDrop(Player player, ItemStack dropped) {
        int slot = player.getInventory().getHeldItemSlot();
        // Only refill if the dropped item was from the held slot
        ItemStack handItem = player.getInventory().getItem(slot);
        if (handItem == null || handItem.getType() == Material.AIR) {
            Material dropType = dropped.getType();
            int maxStack = dropType.getMaxStackSize();
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                ItemStack refill = new FindAndRemoveStack(player, dropType, maxStack, slot).getRefillStack();
                if (refill != null) {
                    player.getInventory().setItem(slot, refill);
                    new SendRefillFeedback(player);
                }
            }, 1L);
        }
    }
}
