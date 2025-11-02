package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


/**
 * A refill handler for common items that can be stacked.
 * This handler will find the same item type from the inventory and refill the hotbar slot.
 */
public class CommonRefillHandler extends BaseRefillHandler {


    @Override
    protected FoundItem tryFindSource(Player player, ItemStack triggerItem) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        return Finder.findFirstMatch(player.getInventory(), triggerItem.getType(), heldSlot);
    }

    @Override
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack triggerItem) {
        super.onRefillSuccess(player, foundItem, triggerItem);
    }
}
