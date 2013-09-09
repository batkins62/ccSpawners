package com.creepercountry.ccspawners.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.bukkit.entity.Player;

import com.creepercountry.ccspawners.main.CSPlugin;
import com.creepercountry.ccspawners.util.DebugMode;
import com.creepercountry.ccspawners.util.exceptions.AlreadyRegisteredException;
import com.creepercountry.ccspawners.util.exceptions.NotRegisteredException;
import com.creepercountry.ccspawners.database.Miner;

public abstract class DataSource
{
	protected TempStorageObject tso;
	protected CSPlugin plugin;
	protected boolean firstRun = true;

	public void initialize(CSPlugin plugin, TempStorageObject universe)
	{
		this.tso = universe;
		this.plugin = plugin;
	}
	
	public void deleteUnusedResidentFiles()
	{

	}

	public boolean confirmContinuation(String msg)
	{
		Boolean choice = null;
		String input = null;
		while (choice == null) {
			System.out.println(msg);
			System.out.print(" Continue (y/n): ");
			Scanner in = new Scanner(System.in);
			input = in.next();
			input = input.toLowerCase();
			if (input.equals("y") || input.equals("yes"))
			{
				in.close();
				return true;
			}
			else if (input.equals("n") || input.equals("no"))
			{
				in.close();
				return false;
			}
		}
		System.out.println("[ccSpawners] Error recieving input, exiting.");
		return false;
	}

	public boolean loadAll()
	{
		System.out.println("[ccSpawners] Loading data...");
		return loadMinerList() && loadMiners();
	}

	public boolean saveAll()
	{
		return saveMinerList() && saveMiners();
	}

	abstract public boolean loadMinerList();
	abstract public boolean loadMiner(Miner miner);

	abstract public boolean saveMinerList();
	abstract public boolean saveMiner(Miner miner);

	abstract public void deleteMiner(Miner miner);

	/*
	 * Load Functions
	 */
	
	public boolean loadMiners()
	{
		DebugMode.log("Loading Breaks");

		List<Miner> toRemove = new ArrayList<Miner>();

		for (Miner miner : new ArrayList<Miner>(getMiners()))
			if (!loadMiner(miner))
			{
				System.out.println("[ccSpawners] Loading Error: Could not read miner data '" + miner.getName() + "'.");
				toRemove.add(miner);
				//return false;
			}

		// Remove any miner which failed to load.
		for (Miner miner : toRemove)
		{
			System.out.println("[ccSpawner] Loading Error: Removing miner data for '" + miner.getName() + "'.");
			removeMinerList(miner);
		}

		return true;
	}
	
	/*
	 * Save functions
	 */
	
	public boolean saveMiners()
	{
		DebugMode.log("Saving miners");
		for (Miner miner : getMiners())
			saveMiner(miner);
		return true;
	}
	
	// Database functions
	abstract public List<Miner> getMiners(Player player, String[] names);
	abstract public List<Miner> getMiners();
	abstract public List<Miner> getMiners(String[] names);
	abstract public Miner getMiner(String name) throws NotRegisteredException;
	
	abstract public void deleteUnusedMinerFiles();

	abstract public void removeMinerList(Miner miner);
	abstract public boolean hasMiner(String name);

	abstract public void removeMiner(Miner miner);
	abstract public void newMiner(String name) throws AlreadyRegisteredException;

	abstract public Set<String> getMinerKeys();
}
