package me.DestroStar.cubegaming.ru;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 07.07.2016.
 */
public class CommandManager implements CommandExecutor
{
	private Plugin plugin = Core.getPlugin();
	private User user;
	private Map<String, Integer> moders = User.db_moders;
	int index = 1;

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
	{

		if (!(commandSender instanceof Player))
		{
			commandSender.sendMessage("Console is not allowed!");
			return false;
		}

		Player player = (Player) commandSender;
		if (args.length == 0)
		{
			if (label.equalsIgnoreCase("modcheck"))
			{
				if (!player.hasPermission("moderchecker.list"))
				{
					player.sendMessage("У вас нет прав!");
					return false;
				}

				player.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "  Модератор " + "         Время AFK");
				player.sendMessage("");
				for(Map.Entry<String, Integer> entry : moders.entrySet())
				{
					String name = entry.getKey();
					Integer time = entry.getValue();

					int minute = time / 60;
					int hour = minute / 60;

					player.sendMessage(ChatColor.GREEN +""+ index + ". " +  name  + "         " + ChatColor.GOLD + hour + " часов " + minute + " минут");

				}
			}
			if(label.equalsIgnoreCase("afk"))
			{

			}
		}
		return false;
	}

}