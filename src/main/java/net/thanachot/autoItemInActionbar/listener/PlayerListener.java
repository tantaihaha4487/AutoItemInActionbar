package net.thanachot.autoItemInActionbar.listener;

import net.thanachot.autoItemInActionbar.manager.CommonRefillHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerListener implements Listener {

    CommonRefillHandler commonRefillHandler = new CommonRefillHandler();

    @EventHandler
    public void PlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getItemDrop().getItemStack());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getPlayer().getInventory().getItemInMainHand());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        commonRefillHandler.handle(player, event.getItemInHand());
    }

}
