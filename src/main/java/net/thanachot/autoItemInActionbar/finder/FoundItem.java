package net.thanachot.autoItemInActionbar.finder;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FoundItem {

    private Player player;
    private int foundSlot;
    private ItemStack itemStack;


    /**
     * Constructs a FoundItem with the specified slot and item stack.
     *
     * @param foundSlot  The slot where the item was found.
     * @param itemStack  The ItemStack that was found.
     */
    public FoundItem(int foundSlot, ItemStack itemStack) {
        this.foundSlot = foundSlot;
        this.itemStack = itemStack;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getFoundSlot() {
        return foundSlot;
    }

    public void setFoundSlot(int foundSlot) {
        this.foundSlot = foundSlot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
