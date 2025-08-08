package net.thanachot.autoItemInActionbar.finder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Finder {

    public static FoundItem findFirstMatch(Player player, Material type, int excludedSlot) {

        ItemStack[] playerInventory = player.getInventory().getContents();
        for (int i = 0; i < playerInventory.length; i++) {
            if (i == excludedSlot) continue; // Skip the excluded slot
            ItemStack item = playerInventory[i];
            if (item != null && item.getType() == type) {
                return new FoundItem(i, item);
            }
        }

        return null; // No matching item found
    }
}
