package net.thanachot.autoItemInActionbar.listener;

import net.thanachot.autoItemInActionbar.AutoItemInActionbar;
import net.thanachot.autoItemInActionbar.manager.BucketFillRefillHandler;
import net.thanachot.autoItemInActionbar.manager.CommonRefillHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class PlayerListener implements Listener {

    private final AutoItemInActionbar plugin;
    CommonRefillHandler commonRefillHandler = new CommonRefillHandler();
    BucketFillRefillHandler bucketFillRefillHandler = new BucketFillRefillHandler();

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

}
