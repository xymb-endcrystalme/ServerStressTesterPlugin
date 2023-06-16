package xymb.serverstresstester;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.permissions.DefaultPermissions;
import xymb.serverstresstester.commands.GiveKitsToEveryone;

@SuppressWarnings("unused")
public final class Main extends JavaPlugin {
    public Main() {}

    @Override
    public void onEnable() {
        getServer().getCommandMap().register("xymbgivekitstoeveryone", new GiveKitsToEveryone(this));
    }

    public void reloadPlugin() throws Exception {
//        this.configuration = Loader.loadConfiguration(pluginpath);
    }
/*
    public Configuration getConfiguration() {
//        return configuration;
    }*/
}
