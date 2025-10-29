package net.thanachot.autoItemInActionbar.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
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
    */

    public final void handle(Player player, ItemStack triggerItem) {
        UUID id = player.getUniqueId();

        if (!processing.add(id)) return; // add returns false if already present

        try {
            FoundItem foundItem = tryFindSource(player, triggerItem);

            if (foundItem == null) return; // No item found to refill with

            performRefill(player, foundItem, triggerItem);
            onRefillSuccess(player, foundItem, triggerItem);
        } finally {
            processing.remove(id);
        }
    }

    /**
     * Subclasses should implement how to find the refill source.
     * Return null if no refill should happen.
     */
    protected abstract FoundItem tryFindSource(Player player, ItemStack triggerItem);




    /**
     * Common refill logic (can be overridden if needed).
     */
    protected void performRefill(Player player, FoundItem foundItem, ItemStack triggerItem) {
        ItemStack sourceStack = foundItem.getItemStack();
        int amountInSource = sourceStack.getAmount();
        int maxStackSize = triggerItem.getMaxStackSize();
        int heldSlot = player.getInventory().getHeldItemSlot();

        int amountToMove = Math.min(amountInSource, maxStackSize);

        if (amountToMove <= 0) return;

        ItemStack refillStack = triggerItem.clone();
        refillStack.setAmount(amountToMove);

        player.getInventory().setItem(heldSlot, refillStack);

        sourceStack.setAmount(amountInSource - amountToMove);
        player.getInventory().setItem(foundItem.getFoundSlot(), sourceStack.getAmount() > 0 ? sourceStack : null);
    }

    /**
     * Hook called after refill. Subclass can override for specific use cases.
     */
    protected void onRefillSuccess(Player player, FoundItem foundItem, ItemStack triggerItem) {
        Component message = MiniMessage.miniMessage().deserialize("<b><gold>(<yellow>i</yellow>)</gold></b> <gradient:#9eee22:#55EA80:#246FD6>Auto Item In Actionbar</gradient>");
        player.sendActionBar(message);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.6f, 0.7f);
    }
}
