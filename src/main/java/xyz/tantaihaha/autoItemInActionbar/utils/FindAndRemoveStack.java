package xyz.tantaihaha.autoItemInActionbar.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FindAndRemoveStack {

    private final ItemStack refillStack;

    public FindAndRemoveStack(ItemStack refillStack) {
        this.refillStack = refillStack;
    }

    public ItemStack getRefillStack() {
        return refillStack;
    }

    /**
     * Constructor that finds and removes stack from player's inventory
     */
    public FindAndRemoveStack(Player player, Material type, int maxStack, int excludeSlot) {
        int size = player.getInventory().getSize();
        for (int i = 0; i < size; i++) {
            if (i == excludeSlot) continue;
            ItemStack invItem = player.getInventory().getItem(i);
            if (invItem != null && invItem.getType() == type && invItem.getAmount() > 0) {
                int amountToMove = Math.min(invItem.getAmount(), maxStack);
                ItemStack moved = invItem.clone();
                moved.setAmount(amountToMove);

                if (invItem.getAmount() > amountToMove) {
                    invItem.setAmount(invItem.getAmount() - amountToMove);
                } else {
                    player.getInventory().setItem(i, null);
                }

                this.refillStack = moved;
                return;
            }
        }

        // If nothing found
        this.refillStack = null;
    }
}
