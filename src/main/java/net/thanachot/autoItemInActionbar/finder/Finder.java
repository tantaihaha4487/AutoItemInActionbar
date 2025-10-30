package net.thanachot.autoItemInActionbar.finder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Finder {


    @Deprecated
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


    public static FoundItem findFirstMatch(Inventory inventory, Material type, int excludedSlot) {
        ItemStack[] inventoryContents = inventory.getContents();
        for (int i = 0; i < inventoryContents.length; i++) {
            if (i == excludedSlot) continue; // Skip the excluded slot
            ItemStack item = inventoryContents[i];
            if (item != null && item.getType() == type) {
                return new FoundItem(i, item);
            }
        }
        return null; // No matching item found
    }

    @Deprecated
    public static List<FoundItem> findAllMatch(Player player, Material type, int excludedSlot) {
        List<FoundItem> foundItems = new ArrayList<>();
        ItemStack[] playerInventory = player.getInventory().getContents();
        for (int i = 0; i < playerInventory.length; i++) {
            if (i == excludedSlot) continue; // Skip the excluded slot
            ItemStack item = playerInventory[i];
            if (item != null && item.getType() == type) {
                foundItems.add(new FoundItem(i, item));
            }
        }
        return foundItems;
    }


    public static List<FoundItem> findAllMatch(Inventory inventory, Material type, int excludedSlot) {
        List<FoundItem> foundItems = new ArrayList<>();
        ItemStack[] inventoryContents = inventory.getContents();
        for (int i = 0; i < inventoryContents.length; i++) {
            if (i == excludedSlot) continue; // Skip the excluded slot
            ItemStack item = inventoryContents[i];
            if (item != null && item.getType() == type) {
                foundItems.add(new FoundItem(i, item));
            }
        }
        return foundItems;
    }
    

}
