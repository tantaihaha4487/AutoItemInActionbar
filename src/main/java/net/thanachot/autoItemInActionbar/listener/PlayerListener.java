package net.thanachot.autoItemInActionbar.listener;

import net.thanachot.autoItemInActionbar.AutoItemInActionbar;
import net.thanachot.autoItemInActionbar.manager.BucketEmptyRefillHandler;
import net.thanachot.autoItemInActionbar.manager.BucketFillRefillHandler;
import net.thanachot.autoItemInActionbar.manager.CommonRefillHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private final AutoItemInActionbar plugin;
    CommonRefillHandler commonRefillHandler = new CommonRefillHandler();
    BucketFillRefillHandler bucketFillRefillHandler = new BucketFillRefillHandler();
    BucketEmptyRefillHandler bucketEmptyRefillHandler = new BucketEmptyRefillHandler();


    public PlayerListener(AutoItemInActionbar plugin) {
        this.plugin = plugin;
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
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        // Refill should be delay 1 tick. Have to wait game logic fill water bucket first.
        Bukkit.getScheduler().runTaskLater(plugin, () -> bucketFillRefillHandler.handle(player, event.getItemStack()), 1L);
    }

    @EventHandler
    public void onThrow(PlayerEggThrowEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getEgg().getItem());
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(plugin, () -> bucketEmptyRefillHandler.handle(player, ItemStack.of(event.getBucket())), 1L);
    }

    @EventHandler
    public void onPlayerItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getBrokenItem());
    }

}
