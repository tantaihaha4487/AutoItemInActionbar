package net.thanachot.autoItemInActionbar.listener;

import net.thanachot.autoItemInActionbar.manager.DropRefillHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class onPlayerDrop implements Listener {

    DropRefillHandler handler = new DropRefillHandler();

    @EventHandler
    public void PlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        handler.handle(player, event.getItemDrop().getItemStack());
    }
}
