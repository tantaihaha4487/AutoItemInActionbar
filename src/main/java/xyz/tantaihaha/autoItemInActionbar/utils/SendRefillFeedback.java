package xyz.tantaihaha.autoItemInActionbar.utils;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SendRefillFeedback {
    public SendRefillFeedback(Player player) {
        String message = "" + ChatColor.GOLD + ChatColor.BOLD + "(" +
                ChatColor.YELLOW + ChatColor.BOLD + "i" +
                ChatColor.GOLD + ChatColor.BOLD + ") " +
                ChatColor.GREEN + ChatColor.BOLD + "Auto Item In Actionbar";
        player.sendActionBar(message);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.2f);
    }
}
