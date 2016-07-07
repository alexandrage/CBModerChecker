package me.DestroStar.cubegaming.ru;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 03.07.2016.
 */
public class User implements Listener {

	public static Map <String, Integer> db_moders = new HashMap<String, Integer>();
	private boolean timerStart, taskRun, playerMove;

	public boolean isModer(Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player);
		for(int i = 0; i < user.getGroups().length; i++ ){
			PermissionGroup group = user.getGroups()[i];
			if(group.getName().equalsIgnoreCase("moder")) {
				return true;
			}
		}
		return false;
	}

	@EventHandler


	public void onJoin(PlayerJoinEvent event)
	{
		if(isModer(event.getPlayer())){

			Player moder = event.getPlayer();
			if(!db_moders.containsKey(moder.getName()))
			{
				addModerToDB(moder);
			}
			startAfkTimer(moder);
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		if(isModer(event.getPlayer()))
		{
		}
	}
	@EventHandler
	public void onModerMove(final PlayerMoveEvent event) {


		if (db_moders.containsKey(event.getPlayer().getName())) {
			playerMove = true;
			if(taskRun) {
				taskRun = false;
			}
			startAfkTimer(event.getPlayer());
			}
		}


	private void afkCounter(final Player player)
	{
		final Player moder = player;
		taskRun = true;
		BukkitTask task = new BukkitRunnable() {

			int i;

			@Override
			public void run() {

				if(playerMove || !player.isOnline() ) {
					db_moders.put(moder.getName(), db_moders.get(moder.getName()) + i);
					Core.pushIntoDB();
					this.cancel();
				}
				i++;
				Bukkit.getServer().getLogger().info("i: " + i);
				Bukkit.getServer().getLogger().info("db: " + db_moders.get(moder.getName()));
			}

		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("CBModChecker"), 20, 20);

	}
	
	public void startAfkTimer(final Player player) {

		if (!timerStart && player.isOnline()) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("CBModChecker"), new Runnable() {

				@Override
				public void run() {

					playerMove = false;
					timerStart = false;
					Bukkit.getServer().getLogger().info("120");
					afkCounter(player);

				}

			}, 240);
			timerStart = true;
		}
	}

	public Map<String, Integer> getModersFromDB()
	{
		return db_moders;
	}


	public void addModerToDB(Player player)
	{
		Core.sql.execute("INSERT INTO moderchecker (name) VALUES (?)", player.getName());
		db_moders.put(player.getName(), 0);
	}

	public int getModerAfkTime(Player moder)
	{
		return db_moders.get(moder.getName());
	}



}
