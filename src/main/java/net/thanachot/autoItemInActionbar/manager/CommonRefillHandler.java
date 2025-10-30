package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommonRefillHandler extends BaseRefillHandler {


    @Override
    protected FoundItem tryFindSource(Player player, ItemStack triggerItem) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        ItemStack heldItem = player.getInventory().getItem(heldSlot);

        if (heldItem != null && heldItem.getType() != Material.AIR) {
            if (heldItem.getAmount() > 1) return null; // Don't refill if there's more than one item in the stack
        }

        return Finder.findFirstMatch(player.getInventory(), triggerItem.getType(), heldSlot);
    }

    @Override
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack triggerItem) {
        super.onRefillSuccess(player, foundItem, triggerItem);
    }
}
