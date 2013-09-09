package com.creepercountry.ccspawners.database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.creepercountry.ccspawners.main.CSPlugin;

public class TempStorageObject extends MasterObject
{
	private static CSPlugin plugin;
	private HashSet<BukkitTask> breaks = new HashSet<BukkitTask>();
	
	protected Hashtable<String, Miner> miners = new Hashtable<String, Miner>();
	
	public HashMap<Player, Integer> finds = new HashMap<Player, Integer>();
	
	private static DataSource dataSource;
	private String rootFolder;

	public TempStorageObject()
	{
		setName("");
		rootFolder = "";
	}

	public TempStorageObject(String rootFolder)
	{
		setName("");
		this.rootFolder = rootFolder;
	}

	public TempStorageObject(CSPlugin plugin)
	{
		setName("");
		TempStorageObject.plugin = plugin;
	}
	
	public boolean loadSettings()
	{
		System.out.println("[ccSpawners] Loading Settings...");
		FileMgmt.checkFolders(new String[] { getRootFolder(), getRootFolder() });
		
		if (!loadDatabase("FlatFileSource"))
		{
			System.out.println("[ccSpawners] Error: Failed to load!");
			return false;
		}

		return true;
	}
	
	public String getRootFolder()
	{
		if (plugin != null)
			return plugin.getDataFolder().getPath();
		else
			return rootFolder;
	}
	
	public void addBreak(BukkitTask bt)
	{
		if (!breaks.contains(bt))
			this.breaks.add(bt);
	}
	
	public HashSet<BukkitTask> getBreak()
	{
		return this.breaks;
	}
	
	public void addFindings(Player plr)
	{
		if (finds.containsKey(plr))
		{
			Integer i = finds.get(plr) + 1;
			finds.put(plr, i);
		}
		else
		{
			finds.put(plr, new Integer(1));
		}
	}
	
	public Integer getFindings(Player plr)
	{
		if (finds.containsKey(plr))
			return finds.get(plr);
		return new Integer(1);
	}
	
	/**
	 * @return Hashtable of miners
	 */
	public Hashtable<String, Miner> getMinerMap()
	{
		return this.miners;
	}
	
	public boolean loadDatabase(String databaseType)
	{
		System.out.println("[ccSpawners] Loading Database...");
		try
		{
			setDataSource(databaseType);
		}
		catch (UnsupportedOperationException e)
		{
			return false;
		}
		getDataSource().initialize(plugin, this);

		return getDataSource().loadAll();
	}

	public void setDataSource(String databaseType)
	{
		setDataSource(new FlatFileSource());
	}

	public void setDataSource(DataSource dataSource)
	{
		TempStorageObject.dataSource = dataSource;
	}

	public static DataSource getDataSource()
	{
		return dataSource;
	}
	
	public static CSPlugin getPlugin()
	{
		return plugin;
	}

	public void setPlugin(CSPlugin plugin)
	{
		TempStorageObject.plugin = plugin;
	}
}
