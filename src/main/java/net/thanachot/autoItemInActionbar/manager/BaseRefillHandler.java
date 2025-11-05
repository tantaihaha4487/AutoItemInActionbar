package net.thanachot.autoItemInActionbar.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseRefillHandler {

    private final Set<UUID> processing = ConcurrentHashMap.newKeySet();

    /**
     * Handle common refill behaviour.
     *
     * @param player           The player who triggered the action.
     * @param itemBeforeAction The item that was in the player's hand before the action occurred (Trigger Item).
     */
    public void handle(Player player, ItemStack itemBeforeAction) {

        UUID id = player.getUniqueId();
        if (!processing.add(id)) return; // add returns false if already present

        try {
            FoundItem foundItem = tryFindSource(player, itemBeforeAction);

            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)
                return; // Player should in game mode survival
            if (isArmor(itemBeforeAction)) return; // Ignore is an armor
            if (foundItem == null) return; // Return if finding item doesn't exit

            performRefill(player, foundItem, itemBeforeAction);
            onRefillSuccess(player, foundItem, itemBeforeAction);
        } finally {
            processing.remove(id);
        }

    }


    /**
     * Subclasses should implement how to find the refill source.
     *
     * @param player           The player to check for a refill source.
     * @param itemBeforeAction The item that was in the player's hand before the action that triggered the refill check.
     * @return a {@link FoundItem} if a suitable item to refill from is found, otherwise null.
     */
    protected abstract FoundItem tryFindSource(Player player, ItemStack itemBeforeAction);


    /**
     * Common refill logic (can be overridden if needed).
     *
     * @param player           The player for whom the refill is being performed.
     * @param foundItem        The item and its location in the inventory that will be used for the refill.
     * @param itemBeforeAction The item that was in the player's hand before the action (Trigger Item), used here to determine some properties of the refill.
     */
    protected void performRefill(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        ItemStack sourceStack = foundItem.getItemStack();
        int amountInSource = sourceStack.getAmount();
        int maxStackSize = sourceStack.getMaxStackSize();
        int heldSlot = player.getInventory().getHeldItemSlot();

        int amountToMove = Math.min(amountInSource, maxStackSize);
        if (amountToMove <= 0) return;

        ItemStack refillStack = sourceStack.clone();
        refillStack.setAmount(amountToMove);

        player.getInventory().setItem(heldSlot, refillStack);
        sourceStack.setAmount(amountInSource - amountToMove);
        player.getInventory().setItem(foundItem.getFoundSlot(), sourceStack.getAmount() > 0 ? sourceStack : null);
    }

    /**
     * Hook called after refill. Subclass can override for specific use cases.
     *
     * @param player           The player for whom the refill was successful.
     * @param foundItem        The item that was used for the refill.
     * @param itemBeforeAction The item that was in the player's hand before the action.
     */
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack itemBeforeAction) {
        Component message = MiniMessage.miniMessage().deserialize("<b><gold>(<yellow>i</yellow>)</gold></b> <gradient:#9eee22:#55EA80:#246FD6>Auto Item In Actionbar</gradient>");
        player.sendActionBar(message);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.6f, 0.7f);
    }


    /**
     * @return {@link Boolean} If Bucket are filled.
     *
     */
    @Deprecated
    public boolean isFilledBucketType(ItemStack itemStack) {
        Material type = itemStack.getType();
        return type == Material.WATER_BUCKET || type == Material.LAVA_BUCKET || type == Material.MILK_BUCKET ||
                type == Material.PUFFERFISH_BUCKET || type == Material.SALMON_BUCKET || type == Material.COD_BUCKET ||
                type == Material.TROPICAL_FISH_BUCKET || type == Material.AXOLOTL_BUCKET;
    }

    /**
     * @return {@link Boolean} If Item is Armor.
     *
     */
    public boolean isArmor(ItemStack itemStack) {
        String itemTypeName = itemStack.getType().name();
        return itemTypeName.endsWith("_HELMET") || itemTypeName.endsWith("_CHESTPLATE")
                || itemTypeName.endsWith("_LEGGINGS") || itemTypeName.endsWith("_BOOTS");
    }
}
