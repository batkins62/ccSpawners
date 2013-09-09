package com.creepercountry.ccspawners.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.creepercountry.ccspawners.database.Miner;
import com.creepercountry.ccspawners.database.TempStorageObject;
import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.Colors;
import com.creepercountry.ccspawners.util.exceptions.AlreadyRegisteredException;
import com.creepercountry.ccspawners.util.exceptions.NotRegisteredException;

public class Spawners
{
    /**
     * If ccSpawners is currently enabled
     */
    public static boolean ENABLED = false;
    
    /**
     * The current instance of Spawners
     */
    private static Spawners instance;
    
    /**
     * Plugin instance
     */
    private CSPlugin plugin;
	
	/**
	 * Config instance
	 */
	private FileConfiguration config;
    
    /**
     * Constructor
     * @param plugin
     */
    public Spawners(CSPlugin plugin)
    {
        this.plugin = plugin;
        Spawners.instance = this;
		config = plugin.getConfig();
    }
    
    /**
     * Get the currently loaded Spawners instance
     *
     * @return
     */
    public static Spawners getInstance()
    {
        return instance;
    }
    
    /**
     * Check if a player can do mod functions on ccSpawners
     *
     * @param player the player to check
     * @return true if the player is an cc mod
     */
    public boolean isMod(Player player)
    {
        return hasPermission(player, "cc.mod");
    }
    
    /**
     * Check if a player can do admin functions on ccSpawners
     *
     * @param player the player to check
     * @return true if the player is an cc admin
     */
    public boolean isAdmin(Player player)
    {
        if (player.isOp())
        {
            if (config.getBoolean("plugin.opIsAdmin", true))
            {
                return true;
            }
        }

        return hasPermission(player, "cc.admin");
    }
    
    /**
     * Check if a player has a permissions node
     *
     * @param player
     * @param node
     * @return
     */
    public boolean hasPermission(Player player, String node)
    {
        try
        {
            return player.hasPermission(node);
        }
        catch (NoSuchMethodError e)
        {
            // their server does not support Superperms..
            return !node.contains("admin") && !node.contains("mod");
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
    	
    	TempStorageObject.getDataSource().saveMiner(miner);
    }
}
