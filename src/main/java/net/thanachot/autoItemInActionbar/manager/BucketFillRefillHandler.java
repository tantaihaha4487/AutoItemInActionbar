package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BucketFillRefillHandler extends BaseRefillHandler {

    @Override
    protected FoundItem tryFindSource(Player player, ItemStack itemBeforeAction) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        if (!isFilledBucket(itemBeforeAction)) return null;
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

    private boolean isFilledBucket(ItemStack itemStack) {
        Material type = itemStack.getType();
        return type == Material.WATER_BUCKET || type == Material.LAVA_BUCKET || type == Material.MILK_BUCKET ||
                type == Material.PUFFERFISH_BUCKET || type == Material.SALMON_BUCKET || type == Material.COD_BUCKET ||
                type == Material.TROPICAL_FISH_BUCKET || type == Material.AXOLOTL_BUCKET;
    }
}
