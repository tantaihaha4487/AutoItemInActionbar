package xyz.tantaihaha.autoItemInActionbar.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import xyz.tantaihaha.autoItemInActionbar.core.Refill;
import xyz.tantaihaha.autoItemInActionbar.enums.RefillType;

public class PlayerListener implements Listener {
    @EventHandler public void onPlace(BlockPlaceEvent e) {
        Refill.refill(e.getPlayer(), RefillType.DEFAULT);
    }
    @EventHandler public void onBreak(BlockBreakEvent e) {
        Refill.refill(e.getPlayer(), RefillType.DEFAULT);
    }
    @EventHandler public void onBucketEmpty(PlayerBucketEmptyEvent e) {
        Refill.refill(e.getPlayer(), RefillType.ON_BUCKET);
    }
    @EventHandler public void onBucketFill(PlayerBucketFillEvent e) {
        Refill.refill(e.getPlayer(), RefillType.ON_BUCKET);
    }
    @EventHandler public void onConsume(PlayerItemConsumeEvent e) {
        Refill.refill(e.getPlayer(), RefillType.DEFAULT);
    }
    @EventHandler public void onInteract(PlayerInteractEvent e) {
        Refill.refill(e.getPlayer(), RefillType.DEFAULT);
    }
    @EventHandler public void onItemDrop(PlayerDropItemEvent e) {
        Refill.refill(e.getPlayer(), RefillType.ON_DROP, e.getItemDrop().getItemStack());
    }
    @EventHandler public void onThrowEgg(PlayerEggThrowEvent e) {
        Refill.refill(e.getPlayer(), RefillType.DEFAULT);
    }
}


