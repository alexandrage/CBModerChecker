package me.DestroStar.cubegaming.ru;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Created by Дмитрий on 03.07.2016.
 */
public class User implements Listener {

	Player player;

	public void getUserGroup(Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		PermissionGroup group = user.getGroups()[0];
		player.sendMessage(ChatColor.AQUA + "" + group);
	}

	@EventHandler

	public void onJoin(PlayerJoinEvent event)
	{
		getUserGroup(event.getPlayer());
	}

}
