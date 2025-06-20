package xyz.tantaihaha.autoItemInActionbar.core;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.tantaihaha.autoItemInActionbar.enums.RefillType;

public class Refill {
    public static void refill(Player player, RefillType type) {
        if (type == RefillType.ON_DROP) {
            throw new IllegalArgumentException("RefillType.ON_DROP requires a droppedStack argument. Use refill(player, RefillType.ON_DROP, droppedStack)");
        }
        int slot = player.getInventory().getHeldItemSlot();
        var item = player.getInventory().getItem(slot);
        Material mat = (item != null && item.getType() != Material.AIR) ? item.getType() : null;
        if (mat == null) return;

        switch (type) {
            case ON_BUCKET:
                Bucket.refillBucket(player);
                break;
            case DEFAULT:
            default:
                if (isBucketType(mat)) {
                    Bucket.refillBucket(player);
                } else {
                    Item.refillItem(player);
                }
        }
    }

    public static void refill(Player player, RefillType type, ItemStack droppedStack) {
        if (type == RefillType.ON_DROP) {
            Item.refillOnDropItem(player, droppedStack);
            return;
        }
        refill(player, type);
    }

    public static boolean isBucketType(Material type) {
        return type == Material.BUCKET
                || type == Material.WATER_BUCKET
                || type == Material.LAVA_BUCKET
                || type == Material.MILK_BUCKET
                || type == Material.POWDER_SNOW_BUCKET;
    }
}
