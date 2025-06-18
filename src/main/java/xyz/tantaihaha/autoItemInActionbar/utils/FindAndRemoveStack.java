package xyz.tantaihaha.autoItemInActionbar.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FindAndRemoveStack {

    private ItemStack refillStack;
    private ItemStack removedStack;
    private int currentSlot;

    public FindAndRemoveStack(ItemStack refillStack, ItemStack removedStack, int currentSlot) {
        this.refillStack = refillStack;
        this.removedStack = removedStack;
        this.currentSlot = currentSlot;
    }

    public ItemStack getRefillStack() {
        return refillStack;
    }

    public void setRefillStack(ItemStack refillStack) {
        this.refillStack = refillStack;
    }

    public ItemStack getRemovedStack() {
        return removedStack;
    }

    public void setRemovedStack(ItemStack removedStack) {
        this.removedStack = removedStack;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(int currentSlot) {
        this.currentSlot = currentSlot;
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
                this.removedStack = invItem;
                this.currentSlot = i;
                return;
            }
        }

        // If nothing found
        this.refillStack = null;
        this.removedStack = null;
        this.currentSlot = -1;
    }
}
