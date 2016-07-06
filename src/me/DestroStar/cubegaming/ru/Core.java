package me.DestroStar.cubegaming.ru;




import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Дмитрий on 03.07.2016.
 */
public class Core extends JavaPlugin {

    public static SQLUtil sql;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("ModerCheck System are Enabled");
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new User(),this);
        Plugin PEX = this.getServer().getPluginManager().getPlugin("PermissionsEx");
        if (PEX != null) {
            this.getServer().getLogger().info("[ModerCheck] Found PermissionsEx. Will use it for groups!");
        }
        connect();
    }

    public void onDisable(){

        getLogger().info("ModerCheck System are Disabled");
        pullIntoDB();

    }

    public void connect() {

        SQLUtil sq = new SQLUtil(this);


        try {
            sq.openConnection();
            sql = sq;

            sql.execute("CREATE TABLE IF NOT EXISTS moderchecker (name VARCHAR(16) NOT NULL, time INT DEFAULT 0)");
            ResultSet res = sql.executeQuery("SELECT * FROM moderchecker");
            if (res != null) {
                while (res.next()) {
                    User.db_moders.put(res.getString("name"), res.getInt("time"));

                }
                res.close();
                SQLUtil.connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void pullIntoDB()
    {

        for(Map.Entry<String, Integer> entry : User.db_moders.entrySet()){
            String s = entry.getKey();
            Integer time = entry.getValue();
            Bukkit.getServer().getLogger().info(s+":"+time);
            Bukkit.getLogger().info(time + " time");
            Core.sql.execute("UPDATE moderchecker SET time = ? WHERE name = ?", time,s);
        }

    }


}
