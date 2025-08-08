package net.thanachot.autoItemInActionbar.refill;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemRefiller {

    public static void refillAfterDrop(Player player, ItemStack droppedItem) {
        FoundItem foundItem = Finder.findFirstMatch(player, droppedItem.getType(), player.getInventory().getHeldItemSlot());
        if (foundItem != null) {
            ItemStack inventoryStack = foundItem.getItemStack();
            int amountToRefill = Math.min(inventoryStack.getAmount(), droppedItem.getMaxStackSize());

            if (amountToRefill <= 0) {
                return;
            }

            ItemStack refillItem = inventoryStack.clone();
            refillItem.setAmount(amountToRefill);
            inventoryStack.setAmount(inventoryStack.getAmount() - amountToRefill);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), refillItem);
            player.sendMessage("[Refiller] Refilled item in action bar: " + refillItem.getType().name() + " x" + refillItem.getAmount());
            player.sendActionBar("Refilled item in action bar!");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }
    }

}
