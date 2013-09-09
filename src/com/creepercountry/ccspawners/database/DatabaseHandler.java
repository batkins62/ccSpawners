package com.creepercountry.ccspawners.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.Colors;
import com.creepercountry.ccspawners.util.exceptions.AlreadyRegisteredException;
import com.creepercountry.ccspawners.util.exceptions.NotRegisteredException;

public abstract class DatabaseHandler extends DataSource
{
	@Override
	public boolean hasMiner(String name)
	{
		return tso.getMinerMap().containsKey(name.toLowerCase());
	}
	
	@Override
	public List<Miner> getMiners(Player player, String[] names)
	{
		List<Miner> invited = new ArrayList<Miner>();
		for (String name : names)
		{
			try
			{
				Miner target = getMiner(name);
				invited.add(target);
			}
			catch (Exception x)
			{
				BukkitUtils.sendMessage((CommandSender)player, Colors.Red, x.getMessage());
			}
		}
		return invited;
	}

	@Override
	public List<Miner> getMiners(String[] names)
	{
		List<Miner> matches = new ArrayList<Miner>();
		for (String name : names)
		{
			try
			{
				matches.add(getMiner(name));
			}
			catch (NotRegisteredException e)
			{
			}
		}
		return matches;
	}
	
	@Override
	public List<Miner> getMiners()
	{
		return new ArrayList<Miner>(tso.getMinerMap().values());
	}

	@Override
	public Miner getMiner(String name) throws NotRegisteredException
	{
		Miner miner = null;
		miner = tso.getMinerMap().get(name.toLowerCase());
		
		if (miner == null)
			throw new NotRegisteredException(String.format("The miner '%s' is not registered.", name));

		return miner;
	}
	
	@Override
	public void removeMiner(Miner miner)
	{
		miner.clear();
	}
	
	@Override
	public void newMiner(String name) throws AlreadyRegisteredException
	{	
		if (tso.getMinerMap().containsKey(name.toLowerCase()))
			throw new AlreadyRegisteredException("A miner with the name " + name + " is already in use.");

		tso.getMinerMap().put(name.toLowerCase(), new Miner(name));
	}
	
	@Override
	public void removeMinerList(Miner miner)
	{
		String name = miner.getName();

		//search and remove from all friends lists
		List<Miner> toSave = new ArrayList<Miner>();

		for (Miner toCheck : toSave)
			saveMiner(toCheck);

		//Wipe and delete miner
		miner.clear();
		deleteMiner(miner);

		tso.getMinerMap().remove(name.toLowerCase());
		saveMinerList();
	}
	
	@Override
	public Set<String> getMinerKeys()
	{
		return tso.getMinerMap().keySet();
	}
}