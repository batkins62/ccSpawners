package com.creepercountry.ccspawners.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.creepercountry.ccspawners.main.CSPlugin;
import com.creepercountry.ccspawners.main.Spawners;
import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.Colors;
import com.creepercountry.ccspawners.util.DebugMode;
import com.creepercountry.ccspawners.util.EntitySpawners;
import com.creepercountry.ccspawners.util.tasks.CheckBreakTask;

public class CSBlockListener implements Listener
{
	/**
     * The plugin instance
     */
	private CSPlugin plugin;
	
	/**
	 * constructor
	 * @param plugin
	 */
	public CSBlockListener(CSPlugin instance)
    {
		plugin = instance;
    }
    
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreakEvent(BlockBreakEvent event)
	{
		// get the block broke
		final Block block = event.getBlock();

		// Verify match of mobspawner, dont do anything if block broke wasnt a spawner
		if (block.getType().equals(Material.MOB_SPAWNER) && Spawners.ENABLED)
		{
			final Player player = event.getPlayer();
			final World world = event.getBlock().getWorld();
			final Location location = event.getBlock().getLocation();
			
			// Loop through entityspawners until a match is found
			for (EntitySpawners et : EntitySpawners.values())
			{
				if (((CreatureSpawner)block.getState()).getSpawnedType().equals(et.getEntityType()))
				{
					// match found, check if player can destroy block.
					if (plugin.canBuild(player, block) && CSPlugin.perms.has(world, player.getName(), "cc.spawner." + et.getName().toLowerCase(plugin.getLocale())))
					{
						// let the player know he is about to be paid
        				BukkitUtils.sendMessage(player, Colors.Yellow, "Please wait...");
        				
        				// Grab a final constant of the entity name
        				final String name = et.getName().toLowerCase(plugin.getLocale());
        				
        				// 3 sec delay (60L/20) to confirm if block really was broke
        				new CheckBreakTask(location, player, name).runTaskLater(plugin, 60L);
					}
					else
					{
						BukkitUtils.sendMessage(player, Colors.Red, "[Permissions] You do not have permissions to remove this spawner");
						plugin.getLog().warning("Denied " + player.getName() + " from breaking a " + et.getName() + " at " + location.toString());
					}
					
					DebugMode.log("(Matched mobspawner to Entitytype) " + event.getPlayer().getName());
				}
			}
			DebugMode.log("(Was a mob spawner block broke.) " + event.getPlayer().getName());
		}
		DebugMode.log("(Ran Event.onMobSpawnerBreak) " + event.getPlayer().getName());
	}
}
