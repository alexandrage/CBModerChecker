package me.DestroStar.cubegaming.ru;

/**
 * Created by Дмитрий on 05.07.2016.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class SQLUtil
{
	public static Connection connection = null;
	private final Plugin plugin;

	public SQLUtil(Plugin plugin)
	{
		this.plugin = plugin;
	}

	public void openConnection()
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://" + this.plugin.getConfig().getString("mysql.host") + ":" + this.plugin.getConfig().getString("mysql.port") + "/" + this.plugin.getConfig().getString("mysql.database") + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&" + "user=" + this.plugin.getConfig().getString("mysql.user") + "&password=" + this.plugin.getConfig().getString("mysql.pass"));
		}
		catch (Exception e)
		{
			Bukkit.getLogger().info("####################################################");
			Bukkit.getLogger().info(" ");
			Bukkit.getLogger().info("[CBModerChecker]: Check your config! Plugin can not connect to the database!");
			Bukkit.getLogger().info(" ");
			Bukkit.getLogger().info("####################################################");
		}
	}

	public void execute(String query, Object... values)
	{
		try
		{
			if ((connection == null) || (connection.isClosed()))
			{
				openConnection();
			}
			PreparedStatement ps = connection.prepareStatement(query);
			for (int i = 0; i < values.length; i++)
			{
				ps.setObject(i + 1, values[i]);
			}
			ps.executeUpdate();
		}
		catch (Exception var2)
		{
			var2.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query, Object... values)
	{
		ResultSet rs = null;
		try
		{
			if ((connection == null) || (connection.isClosed()))
			{
				openConnection();
			}
			PreparedStatement ps = connection.prepareStatement(query);
			for (int i = 0; i < values.length; i++)
			{
				ps.setObject(i + 1, values[i]);
			}
			rs = ps.executeQuery();
		}
		catch (Exception var3)
		{
			var3.printStackTrace();
		}
		return rs;
	}
}