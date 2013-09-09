package com.creepercountry.ccspawners.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import com.creepercountry.ccspawners.main.CSPlugin;
import com.creepercountry.ccspawners.util.DebugMode;
import com.creepercountry.ccspawners.util.exceptions.AlreadyRegisteredException;

public class FlatFileSource extends DatabaseHandler
{
	protected final String newLine = System.getProperty("line.separator");
	protected String rootFolder = "";
	protected String dataFolder = FileMgmt.fileSeparator() + "data";

	@Override
	public void initialize(CSPlugin plugin, TempStorageObject tso)
	{
		System.out.println("[ccSpawners] Initialising dataSource for use...");
		this.tso = tso;
		this.plugin = plugin;
		this.rootFolder = tso.getRootFolder();

		// Create files and folders if non-existent
		try
		{
			FileMgmt.checkFolders(new String[]
				{ 
					rootFolder,
					rootFolder + dataFolder,
					rootFolder + dataFolder + FileMgmt.fileSeparator() + "miners",
					rootFolder + dataFolder + FileMgmt.fileSeparator() + "miners" + FileMgmt.fileSeparator() + "deleted",
				});
				FileMgmt.checkFiles(new String[]
					{
						rootFolder + dataFolder + FileMgmt.fileSeparator() + "miners.txt",
					});
		}
		catch (IOException e)
		{
			System.out.println("[ccTowns] Error: Could not create flatfile default files and folders.");
		}
	}
	
	@Override
	public void deleteUnusedMinerFiles()
	{
		String path;
		Set<String> names;

		path = rootFolder + dataFolder + FileMgmt.fileSeparator() + "miners";
		names = getMinerKeys();

		FileMgmt.deleteUnusedFiles(new File(path), names);
	}
	
	public String getMinerFilename(Miner miner)
	{
		return rootFolder + dataFolder + FileMgmt.fileSeparator() + "miners" + FileMgmt.fileSeparator() + miner.getName() + ".txt";
	}
	
	/*
	 * Load keys
	 */

	@Override
	public boolean loadMinerList()
	{
		DebugMode.log("Loading Miner List");
		String line;
		BufferedReader fin;

		try
		{
			fin = new BufferedReader(new FileReader(rootFolder + dataFolder + FileMgmt.fileSeparator() + "miners.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			while ((line = fin.readLine()) != null)
				if (!line.equals(""))
					newMiner(line);
		}
		catch (AlreadyRegisteredException e)
		{
			e.printStackTrace();
			confirmContinuation(e.getMessage() + " | Continuing will delete it's data.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				fin.close();
			}
			catch (IOException e)
			{
				// Failed to close file.
			}
		}
		return true;
	}
	
	/*
	 * Load individual town object
	 */

	@Override
	public boolean loadMiner(Miner miner)
	{
		String line;
		String path = getMinerFilename(miner);
		File fileMiner = new File(path);
		
		if (fileMiner.exists() && fileMiner.isFile())
		{
			try
			{
				KeyValueFile kvFile = new KeyValueFile(path);
				line = kvFile.get("moneyGain");
				if (line != null)
					miner.setMoneyGain(Double.parseDouble(line));

				line = kvFile.get("lastBreakName");
				if (line != null)
					miner.setLastBreak(line, kvFile.get("lastBreakDate"));
				
				line = kvFile.get("spider");
				if (line != null)
					miner.setCount("spider", Integer.parseInt(line));
				
				line = kvFile.get("blaze");
				if (line != null)
					miner.setCount("blaze", Integer.parseInt(line));
				
				line = kvFile.get("cavespider");
				if (line != null)
					miner.setCount("cavespider", Integer.parseInt(line));
				
				line = kvFile.get("creeper");
				if (line != null)
					miner.setCount("creeper", Integer.parseInt(line));
				
				line = kvFile.get("pig");
				if (line != null)
					miner.setCount("pig", Integer.parseInt(line));
				
				line = kvFile.get("pigzombie");
				if (line != null)
					miner.setCount("pigzombie", Integer.parseInt(line));
				
				line = kvFile.get("skeleton");
				if (line != null)
					miner.setCount("skeleton", Integer.parseInt(line));
				
				line = kvFile.get("zombie");
				if (line != null)
					miner.setCount("zombie", Integer.parseInt(line));
				
				line = kvFile.get("ocelot");
				if (line != null)
					miner.setCount("ocelot", Integer.parseInt(line));
				
				line = kvFile.get("magmacube");
				if (line != null)
					miner.setCount("magmacube", Integer.parseInt(line));
				
				line = kvFile.get("cow");
				if (line != null)
					miner.setCount("cow", Integer.parseInt(line));
				
				line = kvFile.get("enderman");
				if (line != null)
					miner.setCount("enderman", Integer.parseInt(line));
				
				line = kvFile.get("villager");
				if (line != null)
					miner.setCount("villager", Integer.parseInt(line));
				
				line = kvFile.get("enderdragon");
				if (line != null)
					miner.setCount("enderdragon", Integer.parseInt(line));
				
				line = kvFile.get("chicken");
				if (line != null)
					miner.setCount("chicken", Integer.parseInt(line));
				
				line = kvFile.get("slime");
				if (line != null)
					miner.setCount("slime", Integer.parseInt(line));
				
				line = kvFile.get("sheep");
				if (line != null)
					miner.setCount("sheep", Integer.parseInt(line));
				
				line = kvFile.get("irongolem");
				if (line != null)
					miner.setCount("irongolem", Integer.parseInt(line));
				
				line = kvFile.get("mushroomcow");
				if (line != null)
					miner.setCount("mushroomcow", Integer.parseInt(line));
				
				line = kvFile.get("squid");
				if (line != null)
					miner.setCount("squid", Integer.parseInt(line));
				
				line = kvFile.get("ghast");
				if (line != null)
					miner.setCount("ghast", Integer.parseInt(line));
				
				line = kvFile.get("wolf");
				if (line != null)
					miner.setCount("wolf", Integer.parseInt(line));
				
				line = kvFile.get("snowman");
				if (line != null)
					miner.setCount("snowman", Integer.parseInt(line));
				
				line = kvFile.get("silverfish");
				if (line != null)
					miner.setCount("silverfish", Integer.parseInt(line));
				
				line = kvFile.get("witch");
				if (line != null)
					miner.setCount("witch", Integer.parseInt(line));
				
				line = kvFile.get("bat");
				if (line != null)
					miner.setCount("bat", Integer.parseInt(line));
				
				line = kvFile.get("wither");
				if (line != null)
					miner.setCount("wither", Integer.parseInt(line));
				
				line = kvFile.get("giant");
				if (line != null)
					miner.setCount("giant", Integer.parseInt(line));
			}
			catch (Exception e)
			{
				System.out.println("[ccSpawners] Loading Error: Exception while reading miner file " + miner.getName());
				return false;
			}

			return true;
		}
		else
			return false;
	}

	/*
	 * Save keys
	 */

	@Override
	public boolean saveMinerList()
	{
		try
		{
			BufferedWriter fout = new BufferedWriter(new FileWriter(rootFolder + dataFolder + FileMgmt.fileSeparator() + "miners.txt"));
			for (Miner miner : getMiners())
				fout.write((miner.getName()) + newLine);
			fout.close();
			return true;
		}
		catch (Exception e)
		{
			System.out.println("[ccSpawner] Saving Error: Exception while saving miners list file");
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Save individual objects
	 */

	@Override
	public boolean saveMiner(Miner miner)
	{
		BufferedWriter fout;
		String path = getMinerFilename(miner);
		try
		{
			fout = new BufferedWriter(new FileWriter(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			// Money Gain
			fout.write("moneyGain=" + Double.toString(miner.getMoneyGain()) + newLine);

			// Last Break
			fout.write("lastBreakName=" + miner.getLastBreakName() + newLine);
			fout.write("lastBreakDate=" + miner.getLastBreakDate() + newLine);
			
			// Entitys Count
			fout.write("spider=" + Integer.toString(miner.getCount("spider")) + newLine);
			fout.write("blaze=" + Integer.toString(miner.getCount("blaze")) + newLine);
			fout.write("cavespider=" + Integer.toString(miner.getCount("cavespider")) + newLine);
			fout.write("creeper=" + Integer.toString(miner.getCount("creeper")) + newLine);
			fout.write("pig=" + Integer.toString(miner.getCount("pig")) + newLine);
			fout.write("pigzombie=" + Integer.toString(miner.getCount("pigzombie")) + newLine);
			fout.write("skeleton=" + Integer.toString(miner.getCount("skeleton")) + newLine);
			fout.write("zombie=" + Integer.toString(miner.getCount("zombie")) + newLine);
			fout.write("ocelot=" + Integer.toString(miner.getCount("ocelot")) + newLine);
			fout.write("magmacube=" + Integer.toString(miner.getCount("magmacube")) + newLine);
			fout.write("cow=" + Integer.toString(miner.getCount("cow")) + newLine);
			fout.write("enderman=" + Integer.toString(miner.getCount("enderman")) + newLine);
			fout.write("villager=" + Integer.toString(miner.getCount("villager")) + newLine);
			fout.write("enderdragon" + Integer.toString(miner.getCount("enderdragon")) + newLine);
			fout.write("chicken=" + Integer.toString(miner.getCount("chicken")) + newLine);
			fout.write("slime=" + Integer.toString(miner.getCount("slime")) + newLine);
			fout.write("sheep=" + Integer.toString(miner.getCount("sheep")) + newLine);
			fout.write("irongolem=" + Integer.toString(miner.getCount("irongolem")) + newLine);
			fout.write("mushroomcow=" + Integer.toString(miner.getCount("mushroomcow")) + newLine);
			fout.write("squid=" + Integer.toString(miner.getCount("squid")) + newLine);
			fout.write("ghast=" + Integer.toString(miner.getCount("ghast")) + newLine);
			fout.write("wolf=" + Integer.toString(miner.getCount("wolf")) + newLine);
			fout.write("snowman=" + Integer.toString(miner.getCount("snowman")) + newLine);
			fout.write("silverfish=" + Integer.toString(miner.getCount("silverfish")) + newLine);
			fout.write("witch=" + Integer.toString(miner.getCount("witch")) + newLine);
			fout.write("bat=" + Integer.toString(miner.getCount("bat")) + newLine);
			fout.write("wither=" + Integer.toString(miner.getCount("wither")) + newLine);
			fout.write("giant=" + Integer.toString(miner.getCount("giant")));
		
			fout.close();
		}
		catch (Exception e)
		{
			try
			{
				fout.close();
			}
			catch (IOException ioe)
			{
			}
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public void deleteMiner(Miner miner)
	{		
		File file = new File(getMinerFilename(miner));
		if (file.exists())
		{
			try
			{
				FileMgmt.moveFile(file, ("deleted"));
			}
			catch (IOException e)
			{
				System.out.println("[ccSpawners] Error moving miner txt file.");
			}
		}
	}
}