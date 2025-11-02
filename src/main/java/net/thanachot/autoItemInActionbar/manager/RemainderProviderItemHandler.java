package net.thanachot.autoItemInActionbar.manager;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A refill handler for items that provide a remainder item after use, such as buckets or potions.
 * This handler will find the same item type from the inventory and refill the hotbar slot,
 * and also add the remainder item to the player's inventory.
 */
public class RemainderProviderItemHandler extends BaseRefillHandler {

    private ItemStack remainderItem;

    /**
     * Handles the refill process for items that leave a remainder (e.g., buckets).
     *
     * @param player           The player performing the action.
     * @param itemBeforeAction The item in the player's hand before the action.
     * @param remainderItem    The ItemStack of the item that should be left in the inventory.
     */
    public void handle(Player player, ItemStack itemBeforeAction, ItemStack remainderItem) {
        this.remainderItem = remainderItem;
        super.handle(player, itemBeforeAction);
    }

    @Override
    protected FoundItem tryFindSource(Player player, ItemStack itemBeforeAction) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        return Finder.findFirstMatch(player.getInventory(), itemBeforeAction.getType(), heldSlot);
    }

    @Override
    protected void performRefill(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        int heldSlot = player.getInventory().getHeldItemSlot();
        ItemStack remainder = player.getInventory().getItem(heldSlot);

        if (remainder == null) return;

        // The source of items for the refill.
        ItemStack sourceStack = foundItem.getItemStack();

        // Swap the remainder item in the player's hand with the stack from the inventory. Prevent empty stacks.
        player.getInventory().setItem(heldSlot, sourceStack);
        player.getInventory().setItem(foundItem.getFoundSlot(), remainder);
    }

    @Override
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        super.onRefillSuccess(player, foundItem, itemBeforeAction);
    }
}
