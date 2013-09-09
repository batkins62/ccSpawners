package com.creepercountry.ccspawners.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.creepercountry.ccspawners.database.TempStorageObject;
import com.creepercountry.ccspawners.main.CSPlugin;
import com.creepercountry.ccspawners.util.tasks.NotificationTask;

public class CSPlayerListener implements Listener
{
	/**
     * The plugin instance
     */
	private CSPlugin plugin;
	
	/**
	 * The TempStorageObject instance
	 */
	private TempStorageObject tso;

	/**
	 * constructor
	 * @param plugin
	 */
	public CSPlayerListener(CSPlugin instance)
    {
		plugin = instance;
		tso = instance.getTempStorageObject();
    }
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		if (tso.finds.isEmpty())
			return;
		
		if (CSPlugin.perms.has(event.getPlayer(), "cs.notification.login"))
			new NotificationTask(event.getPlayer(), plugin).runTaskLater(plugin, 100L);
	}
}
