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
import org.bukkit.inventory.EquipmentSlot;
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
        final EquipmentSlot hand = event.getHand();
        Bukkit.getScheduler().runTaskLater(plugin,
                () -> remainderProviderItemHandler.handle(player, ItemStack.of(Material.BUCKET), hand), 1L);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        final ItemStack bucket = ItemStack.of(event.getBucket());
        final EquipmentSlot hand = event.getHand();
        Bukkit.getScheduler().runTaskLater(plugin,
                () -> remainderProviderItemHandler.handle(player, bucket, hand), 1L);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        final ItemStack item = event.getItemInHand().clone();
        final EquipmentSlot hand = event.getHand();
        Bukkit.getScheduler().runTaskLater(plugin, () -> commonRefillHandler.handle(player, item, hand), 1L);
    }

    @EventHandler
    public void PlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        final ItemStack item = event.getItemDrop().getItemStack().clone();
        Bukkit.getScheduler().runTaskLater(plugin, () -> commonRefillHandler.handle(player, item), 1L);
    }

    @EventHandler
    public void onThrow(PlayerEggThrowEvent event) {
        Player player = event.getPlayer();
        final ItemStack item = event.getEgg().getItem().clone();
        Bukkit.getScheduler().runTaskLater(plugin, () -> commonRefillHandler.handle(player, item), 1L);
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        final ItemStack item = event.getBrokenItem().clone();
        Bukkit.getScheduler().runTaskLater(plugin, () -> commonRefillHandler.handle(player, item), 1L);
    }

    @EventHandler
    public void onPlayerPotionSplash(PotionSplashEvent event) {
        if (!(event.getPotion().getShooter() instanceof Player player))
            return;
        if (event.getPotion().getItem().getType() != Material.SPLASH_POTION)
            return; // Only splash potion can be refilled.

        final ItemStack item = event.getPotion().getItem().clone();
        Bukkit.getScheduler().runTaskLater(plugin, () -> commonRefillHandler.handle(player, item), 1L);
    }

    @EventHandler
    public void onLingeringPotionSplashEvent(LingeringPotionSplashEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player))
            return;
        if (event.getEntity().getItem().getType() != Material.LINGERING_POTION)
            return; // Only splash potion can be refilled.

        final ItemStack item = event.getEntity().getItem().clone();
        Bukkit.getScheduler().runTaskLater(plugin, () -> commonRefillHandler.handle(player, item), 1L);
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        final ItemStack consumedItem = event.getItem().clone();
        final EquipmentSlot hand = event.getHand();
        Material consumeItemType = consumedItem.getType();

        switch (consumeItemType) {
            case MILK_BUCKET, SUSPICIOUS_STEW, MUSHROOM_STEW, RABBIT_STEW, BEETROOT_SOUP, POTION, HONEY_BOTTLE ->
                Bukkit.getScheduler().runTaskLater(plugin,
                        () -> remainderProviderItemHandler.handle(player, consumedItem, hand), 1L);
            default -> {
                if (consumedItem.getType().isEdible()) {
                    Bukkit.getScheduler().runTaskLater(plugin,
                            () -> commonRefillHandler.handle(player, consumedItem, hand), 1L);
                }
            }
        }
    }
}
