package net.thanachot.autoItemInActionbar.listener;

import net.thanachot.autoItemInActionbar.AutoItemInActionbar;
import net.thanachot.autoItemInActionbar.manager.CommonRefillHandler;
import net.thanachot.autoItemInActionbar.manager.RemainderProviderItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private final AutoItemInActionbar plugin;
    CommonRefillHandler commonRefillHandler = new CommonRefillHandler();
    RemainderProviderItemHandler remainderProviderItemHandler = new RemainderProviderItemHandler();


    public PlayerListener(AutoItemInActionbar plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        // Refill is delayed by 1 tick to ensure the game has processed the bucket fill action.
        // When a player fills a bucket:
        // 1. The item in their hand (an empty BUCKET) is replaced by the filled bucket (e.g., WATER_BUCKET).
        // 2. We trigger the refill handler with:
        //    - itemBeforeAction: An empty BUCKET. The handler will search for and consume another empty BUCKET from the inventory.
        //    - remainderItem: The filled bucket from the event. The handler will give this item back to the player.
        Bukkit.getScheduler().runTaskLater(plugin, () -> remainderProviderItemHandler.handle(player, ItemStack.of(Material.BUCKET)), 1L);

    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        // Refill is delayed by 1 tick to ensure the game has processed the bucket empty action.
        // When a player empties a bucket:
        // 1. The item in their hand (e.g., WATER_BUCKET) is replaced by an empty BUCKET.
        // 2. We trigger the refill handler with:
        //    - itemBeforeAction: The filled bucket type. The handler will search for and consume another bucket of this type from the inventory.
        //    - remainderItem: An empty BUCKET. The handler will give this item back to the player as the remainder.
        Bukkit.getScheduler().runTaskLater(plugin, () -> remainderProviderItemHandler.handle(player, ItemStack.of(event.getBucket())), 1L);

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getItemInHand());
    }

    @EventHandler
    public void PlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getItemDrop().getItemStack());
    }

    @EventHandler
    public void onThrow(PlayerEggThrowEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getEgg().getItem());
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getBrokenItem());
    }

    @EventHandler
    public void onPlayerPotionSplash(PotionSplashEvent event) {
        Player player = (Player) event.getPotion().getShooter();
        if (event.getPotion().getItem().getType() != Material.SPLASH_POTION)
            return; // Only splash potion can be refilled.
        if (player != null) {
            commonRefillHandler.handle(player, event.getPotion().getItem());
        }
    }

    @EventHandler
    public void onLingeringPotionSplashEvent(LingeringPotionSplashEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        if (event.getEntity().getItem().getType() != Material.LINGERING_POTION)
            return; // Only splash potion can be refilled.
        if (player != null) {
            commonRefillHandler.handle(player, event.getEntity().getItem());
        }
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        Material consumeItemType = event.getItem().getType();
        switch (consumeItemType) {
            case MILK_BUCKET, SUSPICIOUS_STEW, MUSHROOM_STEW, RABBIT_STEW, BEETROOT_SOUP, POTION, HONEY_BOTTLE ->
                    Bukkit.getScheduler().runTaskLater(plugin, () -> remainderProviderItemHandler.handle(player, event.getItem()), 1L);
        }
    }
}
