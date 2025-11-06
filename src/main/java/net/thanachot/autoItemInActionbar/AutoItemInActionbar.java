package net.thanachot.autoItemInActionbar;

import net.thanachot.autoItemInActionbar.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoItemInActionbar extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
