package com.creepercountry.ccspawners.main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public final class CSConfig
{
	/**
	 * our plugin instance
	 */
	private CSPlugin plugin;
	
	/**
	 * the JavaPlugin's configuration
	 */
	private FileConfiguration config;
	
	/**
	 * Our global variables
	 * 
	 * retrievable variables set by our global configuration (config.yml)
	 */
	public String country, language;
	public boolean debug;
	
	/**
	 * Creates a config object (constructor).
	 * 
	 * @param instance of the main plugin
	 */
	protected CSConfig(CSPlugin instance)
	{
		this.plugin = instance;
		config = plugin.getConfig();
		load();
	}
	
	private void load()
	{
		// load strings
		country = config.getString("plugin.locale.coun", "us");
		language = config.getString("plugin.locale.lang", "en");
		
		// load booleans
		debug = config.getBoolean("plugin.debug", false);
	}
	
	public double getPayout(String entity, Player plr)
	{
		if (config.getBoolean("player.usepermissions", false))
		{
			for (int i = config.getInt("player.largest"); i >= config.getInt("player.lowest"); i--)
	            if (CSPlugin.perms.has(plr, "cs.payout." + i))
	                return i;
		}
			
		return config.getDouble("default." + entity, 0.00);
	}
}
