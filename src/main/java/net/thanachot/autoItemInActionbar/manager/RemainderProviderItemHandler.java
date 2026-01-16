package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * A refill handler for items that provide a remainder item after use (e.g.,
 * buckets, potions).
 * Swaps the remainder item with a matching item from the inventory.
 */
public class RemainderProviderItemHandler extends BaseRefillHandler {

    @Override
    protected FoundItem tryFindSource(Player player, ItemStack itemBeforeAction, EquipmentSlot hand) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        return Finder.findFirstMatch(player.getInventory(), itemBeforeAction.getType(), heldSlot);
    }

    @Override
    protected void performRefill(Player player, FoundItem foundItem, ItemStack itemBeforeAction, EquipmentSlot hand) {
        ItemStack remainder;
        ItemStack sourceStack = foundItem.getItemStack();

        if (hand == EquipmentSlot.OFF_HAND) {
            remainder = player.getInventory().getItemInOffHand();
            if (remainder == null)
                return;
            player.getInventory().setItemInOffHand(sourceStack);
        } else {
            int heldSlot = player.getInventory().getHeldItemSlot();
            remainder = player.getInventory().getItem(heldSlot);
            if (remainder == null)
                return;
            player.getInventory().setItem(heldSlot, sourceStack);
        }

        player.getInventory().setItem(foundItem.getFoundSlot(), remainder);
    }
}
