package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * A refill handler for common items that can be stacked.
 * This handler will find the same item type from the inventory and refill the
 * hotbar slot or off-hand slot depending on which hand was used.
 */
public class CommonRefillHandler extends BaseRefillHandler {

    @Override
    protected FoundItem tryFindSource(Player player, ItemStack triggerItem, EquipmentSlot hand) {
        ItemStack handItem;

        if (hand == EquipmentSlot.OFF_HAND) {
            handItem = player.getInventory().getItemInOffHand();
        } else {
            handItem = player.getInventory().getItemInMainHand();
        }

        // Only refill if the hand that was used is now empty
        if (handItem.getType() != Material.AIR)
            return null;

        int heldSlot = player.getInventory().getHeldItemSlot();
        return Finder.findFirstMatch(player.getInventory(), triggerItem.getType(), heldSlot);
    }

    @Override
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack triggerItem) {
        super.onRefillSuccess(player, foundItem, triggerItem);
    }
}
