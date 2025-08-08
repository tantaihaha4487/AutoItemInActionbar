package net.thanachot.autoItemInActionbar.listener;

import net.thanachot.autoItemInActionbar.finder.Finder;
import net.thanachot.autoItemInActionbar.finder.FoundItem;
import net.thanachot.autoItemInActionbar.refill.ItemRefiller;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class onPlayerDrop implements Listener {

    @EventHandler
    public void PlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemRefiller.refillAfterDrop(event.getPlayer(), event.getItemDrop().getItemStack());

    }
}
