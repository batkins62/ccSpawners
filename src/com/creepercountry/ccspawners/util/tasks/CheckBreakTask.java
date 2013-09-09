package com.creepercountry.ccspawners.util.tasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.creepercountry.ccspawners.database.Miner;
import com.creepercountry.ccspawners.database.TempStorageObject;
import com.creepercountry.ccspawners.main.CSPlugin;
import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.Colors;
import com.creepercountry.ccspawners.util.exceptions.AlreadyRegisteredException;
import com.creepercountry.ccspawners.util.exceptions.NotRegisteredException;

public class CheckBreakTask extends BukkitRunnable
{
	private final Location location;
	private final Player player;
	private final String entity;
	private final CSPlugin plugin;
	
	public CheckBreakTask (Location loc, Player plr, String ety)
	{
		this.location = loc;
		this.player = plr;
		this.entity = ety;
		this.plugin = CSPlugin.getInstance();
	}
	
	@Override
	public void run()
	{
		if (!location.getBlock().equals(Material.MOB_SPAWNER))
		{
			if (logBreak(player, entity, location))
				processPayment(player, entity);
		}
		else
		{
			BukkitUtils.sendMessage(player, Colors.Red, "[Permissions] You do not have permissions to remove this spawner");
			plugin.getLog().warning("Denied " + player.getName() + " from breaking a " + entity + " at " + location.toString());
		}
	}
	
    /**
     * Log a spawner break
     * 
     * @param plr
     * @param (ety) the entity name
     * @param loc
     */
    public boolean logBreak(Player plr, String ety, Location loc)
    {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String todaysDate = dateFormat.format(System.currentTimeMillis());
    	TempStorageObject tso = plugin.getTempStorageObject();
    	tso.addFindings(plr);
    	
    	// broadcast the find
    	for (Player user : Bukkit.getOnlinePlayers())
    	{
    		if (CSPlugin.perms.has(user, "cs.notification.broadcast"))
    			BukkitUtils.sendMessage(user, Colors.Gold, plr.getName() + " found a " + ety + " spawner");
    	}
    	
    	Miner miner = null;
    	try
    	{
			miner = TempStorageObject.getDataSource().getMiner(plr.getName());
		}
    	catch (NotRegisteredException e)
    	{
    		BukkitUtils.sendMessage(plr, Colors.Gold, "Congradulations on your first mobspawner find!");
    		BukkitUtils.sendMessage(plr, Colors.LightBlue, "keep finding those mobspawners and earn cash for the breaks, " +
    				"some mobspawners are worth even more, and there is no limit on how many you can break. :)");
    		try { TempStorageObject.getDataSource().newMiner(plr.getName()); } catch (AlreadyRegisteredException e1) {}
    		try { miner = TempStorageObject.getDataSource().getMiner(plr.getName()); } catch (NotRegisteredException e2) {}
    		TempStorageObject.getDataSource().saveMinerList();
		}
    	int x = plugin.getConfig().getInt("record.total-breaks", 0) + 1;
    	plugin.getConfig().set("record.total-breaks", x);
    	//TODO REMOVE SAVECONFIG FOR SOMETHING BETTER THIS WIPES CONFIG COMMENTS
    	plugin.saveConfig();
    	
    	miner.incrementCount(ety);
    	miner.setLastBreak(ety, todaysDate);
    	TempStorageObject.getDataSource().saveMiner(miner);
    	return true;
    }
    
    /**
     * process a spawners payout
     * 
     * @param plr
     * @param (ety) the entity name
     */
    public void processPayment(Player plr, String ety)
    {
    	Miner miner = null;
    	try { miner = TempStorageObject.getDataSource().getMiner(plr.getName()); } catch (NotRegisteredException e) {}
    	double payout = plugin.getConf().getPayout(ety, plr);
    	
    	EconomyResponse er = CSPlugin.econ.depositPlayer(plr.getName(), payout);
    	if (er.transactionSuccess())
    	{
    		miner.addMoneyGain(payout);
    		plugin.getConfig().set("record.total-money", payout);
    		//TODO REMOVE SAVECONFIG FOR SOMETHING BETTER THIS WIPES CONFIG COMMENTS
        	plugin.saveConfig();
    	}
    	else
    	{
    		BukkitUtils.info("Post a bug report immediately, something has happened that shouldnt!");
    		BukkitUtils.sendMessage(plr, Colors.Red, "Internal Error has occured! screenshot this and send it to the administration team.");
    	}
    	
    	if (payout > 0)
    		BukkitUtils.sendMessage(plr, Colors.Green, String.format(plugin.getLocale(), "%s was deposited into your account!", payout));
    	else
    		BukkitUtils.sendMessage(plr, Colors.Rose, "no payout for this spawner");
    	TempStorageObject.getDataSource().saveMiner(miner);
    }
}
