package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropRefillHandler extends BaseRefillHandler {

    @Override
    protected FoundItem tryFindSource(Player player, ItemStack triggerItem) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        return Finder.findFirstMatch(player, triggerItem.getType(), heldSlot);
    }

    @Override
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack triggerItem) {
        super.onRefillSuccess(player, foundItem, triggerItem);
    }
}
