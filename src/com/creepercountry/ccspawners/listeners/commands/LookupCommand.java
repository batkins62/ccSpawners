package com.creepercountry.ccspawners.listeners.commands;

import org.bukkit.command.CommandSender;

import com.creepercountry.ccspawners.database.Miner;
import com.creepercountry.ccspawners.database.TempStorageObject;
import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.EntitySpawners;
import com.creepercountry.ccspawners.util.exceptions.NotRegisteredException;
import com.griefcraft.util.Colors;

public class LookupCommand extends BaseCommand
{
    public LookupCommand()
    {
        name = "lookup";
        usage = "<player>";
    }

    @Override
    public boolean execute()
    {
    	int counter = 0;
    	Miner miner;
    	try
    	{
			miner = TempStorageObject.getDataSource().getMiner(args.get(0));
		}
    	catch (NotRegisteredException e)
    	{
    		BukkitUtils.sendMessage(sender, Colors.Red, e.getLocalizedMessage());
			return true;
		}
    	
    	BukkitUtils.sendMessage(player, Colors.Gold, "Player " + miner.getName() + " Stats:");
    	BukkitUtils.sendMessage(player, Colors.LightBlue, "Last Find: " + miner.getLastBreakDate());
    	BukkitUtils.sendMessage(player, Colors.LightBlue, "Last Find Was: " + miner.getLastBreakName());
    	for (EntitySpawners type : EntitySpawners.values())
    	{
    		String t = type.getName().toLowerCase();
    		int count = miner.getCount(t);
    		if (count > 0)
    		{
    			BukkitUtils.sendMessage(player, Colors.LightBlue, ": " + t + ": " + count);
    			counter = counter + count;
    		}
    	}
    	BukkitUtils.sendMessage(player, Colors.Gold, "Total Breaks: " + Integer.toString(counter));
    	BukkitUtils.sendMessage(player, Colors.Gold, "Total Money Recieved: " + Double.toString(miner.getMoneyGain()));
        return true;
    }

    @Override
    public boolean permission(CommandSender csender)
    {
        return csender.hasPermission("cs.lookup");
    }

    @Override
    public BaseCommand newInstance()
    {
        // TODO Auto-generated method stub
        return new LookupCommand();
    }
}
