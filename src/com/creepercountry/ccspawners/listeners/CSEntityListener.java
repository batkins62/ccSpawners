package com.creepercountry.ccspawners.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.creepercountry.ccspawners.main.CSPlugin;

public class CSEntityListener implements Listener
{
	/**
     * The plugin instance
     */
	private CSPlugin plugin;

	/**
	 * constructor
	 * @param plugin
	 */
	public CSEntityListener(CSPlugin instance)
    {
		plugin = instance;
    }
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCreatureSpawnEvent(CreatureSpawnEvent event)
	{
		// TODO pointless
		plugin.totalSpawned++;
	}
}
