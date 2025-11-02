package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Refill handler for filled buckets (PlayerBucketFillEvent).
 */
public class BucketFillRefillHandler extends BaseRefillHandler {

    @Override
    protected FoundItem tryFindSource(Player player, ItemStack itemBeforeAction) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        if (!isFilledBucketType(itemBeforeAction)) return null; // Is not an filled bucket
        return Finder.findFirstMatch(player.getInventory(), Material.BUCKET, heldSlot);
    }

    @Override
    protected void performRefill(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        super.performRefill(player, foundItem, itemBeforeAction);
        player.getInventory().addItem(itemBeforeAction);
    }

    @Override
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        super.onRefillSuccess(player, foundItem, itemBeforeAction);
    }

    
}
