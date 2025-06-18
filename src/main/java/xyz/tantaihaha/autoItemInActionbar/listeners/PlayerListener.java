package xyz.tantaihaha.autoItemInActionbar.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import xyz.tantaihaha.autoItemInActionbar.core.Bucket;
import xyz.tantaihaha.autoItemInActionbar.core.Item;

public class PlayerListener implements Listener {
    @EventHandler public void onPlace(BlockPlaceEvent e) { Item.refill(e.getPlayer()); }
    @EventHandler public void onBreak(BlockBreakEvent e) { Item.refill(e.getPlayer()); }
    @EventHandler public void onBucketEmpty(PlayerBucketEmptyEvent e){ Bucket.refill(e.getPlayer());}
    @EventHandler public void onBucketFill(PlayerBucketFillEvent e) { Bucket.refill(e.getPlayer());}
    @EventHandler public void onConsume(PlayerItemConsumeEvent e) { Item.refill(e.getPlayer()); }
    @EventHandler public void onInteract(PlayerInteractEvent e) { Item.refill(e.getPlayer()); }
    @EventHandler public void onItemDrop(PlayerDropItemEvent e) { Item.refillOnDrop(e.getPlayer(), e.getItemDrop().getItemStack()); }
    @EventHandler public void onThrowEgg(PlayerEggThrowEvent e) { Item.refill(e.getPlayer());}

}
