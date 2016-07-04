package me.DestroStar.cubegaming.ru;



import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Created by Дмитрий on 03.07.2016.
 */
public class Core extends JavaPlugin {

    public static PermissionsEx PEX;

    @Override
    public void onEnable() {

        getLogger().info("ModerCheck System are Enabled");
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new User(),this);

        Plugin pexPlugin = this.getServer().getPluginManager().getPlugin("PermissionsEx");
        if (pexPlugin != null) {
            this.getServer().getLogger().info("[ColorMe] Found PermissionsEx. Will use it for groups!");
        }
    }

    public void onDisable(){

        getLogger().info("ModerCheck System are Disabled");

    }
}
