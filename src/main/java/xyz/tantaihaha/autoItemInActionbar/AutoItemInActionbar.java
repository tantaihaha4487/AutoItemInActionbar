package xyz.tantaihaha.autoItemInActionbar;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tantaihaha.autoItemInActionbar.listeners.PlayerListener;

public final class AutoItemInActionbar extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register events
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Register Classes
        new xyz.tantaihaha.autoItemInActionbar.core.Item(this);
        new xyz.tantaihaha.autoItemInActionbar.core.Bucket(this);

        // Enable logging
        this.getServer().getLogger().info("✔ Enabled autoItemInActionbar! >");

    }

    @Override
    public void onDisable() {
        this.getServer().getLogger().info(ChatColor.RED + "✘ Disabled autoItemInActionbar! <");
    }
}