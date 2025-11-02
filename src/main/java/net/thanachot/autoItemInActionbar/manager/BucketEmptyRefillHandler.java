package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Refill handler for empty buckets (PlayerBucketEmptyEvent).
 */
public class BucketEmptyRefillHandler extends BaseRefillHandler{
    @Override
    protected FoundItem tryFindSource(Player player, ItemStack itemBeforeAction) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        return Finder.findFirstMatch(player.getInventory(), itemBeforeAction.getType(), heldSlot);
    }

    @Override
    protected void performRefill(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        super.performRefill(player, foundItem, itemBeforeAction);
        player.getInventory().addItem(ItemStack.of(Material.BUCKET));
    }

    @Override
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        super.onRefillSuccess(player, foundItem, itemBeforeAction);
    }
    
}
