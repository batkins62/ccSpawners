package com.creepercountry.ccspawners.util.tasks;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.creepercountry.ccspawners.database.TempStorageObject;
import com.creepercountry.ccspawners.main.CSPlugin;
import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.Colors;

public class NotificationTask extends BukkitRunnable
{
	private final Player player;
	private final CSPlugin plugin;
	private final TempStorageObject tso;
	
	public NotificationTask(Player plr, CSPlugin instance)
	{
		this.player = plr;
		this.plugin = instance;
		this.tso = instance.getTempStorageObject();
	}
	
	@Override
	public void run()
	{
		Set<Player> players = tso.finds.keySet();
		
		cleanTitle("spawners broke since last server reboot", "=");
		for (Player plr : players)
		{
			BukkitUtils.sendMessage(player, Colors.LightBlue, plr.getName() + " found " + Integer.toString(tso.getFindings(plr)) + " spawners since last reboot");
		}
		BukkitUtils.sendMessage(player, Colors.Gold, String.format(plugin.getLocale(), "Use '/cspawner lookup <player>' to view detailed information on spawner breaks") );
	}
	
	/**
	 * Formats a string with a provided title and padding and centers title.
	 * 
	 * @param title
	 * @param fill
	 * @return
	 */
    protected String cleanTitle(String title, String fill)
    {
        int chatWidthMax = 53; 											// Vanilla client line character max
        int titleWidth = title.length() + 2; 							// Title's character width with 2 spaces padding
        int fillWidth = (int) ((chatWidthMax - titleWidth) / 2D); 		// Fill string calculation for padding either side
        String cleanTitle = "";
        
        for(int i = 0; i < fillWidth; i++)
            cleanTitle += fill;
        cleanTitle += " " + Colors.LightBlue + title + Colors.Blue + " ";
        for(int i = 0; i < fillWidth; i++)
            cleanTitle += fill;
        
        return cleanTitle;
    }
}
