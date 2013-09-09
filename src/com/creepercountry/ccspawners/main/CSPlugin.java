package com.creepercountry.ccspawners.main;

import java.io.File;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.creepercountry.ccspawners.database.TempStorageObject;
import com.creepercountry.ccspawners.listeners.CSBlockListener;
import com.creepercountry.ccspawners.listeners.CSEntityListener;
import com.creepercountry.ccspawners.listeners.CSPlayerListener;
import com.creepercountry.ccspawners.listeners.commands.CSCommandExecutor;
import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.DebugMode;
import com.creepercountry.ccspawners.util.Version;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class CSPlugin extends JavaPlugin
{
    /**
     * Our Plugin instance
     */
    private static CSPlugin instance;
    
    /**
     * our vault/hooks instances and variables
     */
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
    public WorldGuardPlugin WorldGuard;
    public PreciousStones pstones;
    
    /**
     * our config class instance
     */
    private CSConfig config;
    
    /**
     * Config version
     */
    public static int cversion = 8;
    
    /**
     * TempStorageObject variable
     */
	private TempStorageObject tso;
    
    /**
     * Our Locale used in plugin.
     */
    private Locale locale;
    
    /**
     * The listeners
     */
    private CSBlockListener blockListener;
    private CSPlayerListener playerListener;
    private CSEntityListener entityListener;
    
    /**
     * The Executor
     */
    private CSCommandExecutor commandExecutor;
    
    /**
     * Total entitys spawned
     * NOTE: this sorta sucks TODO
     */
    public long totalSpawned = 0;
    
    /**
     * Logger object
     */
    private static final Logger logger = Logger.getLogger("ccSpawner");
    
	@Override
    public void onEnable()
    {	
		// this object is our plugin... so, load our config.
		instance = this;
		load();
		
		// set the plugins Locale.
		logger.info("Setting plugin Locale to: (config.language, config.country)");
		try { locale = new Locale(config.language, config.country); }
		catch (NullPointerException n)
		{
			logger.severe("INCORRECT LOCALE FORMAT! Using default Locale: EN,US");
			locale = Locale.US;
		}
		
		// Are we debuging?
		if (getConfig().getBoolean("debug"))
		{
			System.out.println("[ccSpawners] WE ARE DEBUGING ACCORDING TO THE CONFIG!");
			System.out.println("[ccSpawners] DEBUG GO, DEBUG GO, DEBUG GO");
			System.out.println("[ccSpawners] DEBUG GO, DEBUG GO, DEBUG GO");
			System.out.println("[ccSpawners] DEBUG GO, DEBUG GO, DEBUG GO");
			System.out.println("[ccSpawners] DEBUG GO, DEBUG GO, DEBUG GO");
			DebugMode.go();
		}
		
		// load the towns
		tso = new TempStorageObject(this);
		if (tso.loadSettings())
			logger.info("loaded settings");
		
		//hook into depends
		pluginHooks();

		// register the listeners & executors
        try
        {
            registerEvents();
            registerCommands();
        }
        catch (NoSuchFieldError e)
        {
        	BukkitUtils.severe("NoSuchFieldError", e);
        }

		// set version, get version, and display
		CSInfo.setVersion(this.getDescription().getVersion());
		Version version = CSInfo.FULL_VERSION;
		getConfig().set("plugin.pluginversion", version.toString());
		logger.log(Level.INFO, "At version: " + version.toString());

		// Enable the plugin
		Spawners.ENABLED = true;
    }
     
	@Override
    public void onDisable()
    {
		// Disable the plugin
		Spawners.ENABLED = false;
		
		// Save our config
		this.saveConfig();
		
		// Stop Debug
		DebugMode.stop();
		
		// cancel ALL tasks we created
        getServer().getScheduler().cancelTasks(this);
        getServer().getServicesManager().unregisterAll(this);
        
        // unregister our events
        HandlerList.unregisterAll(this);
    }
	
    /**
     * Register all of the events used by ccSpawner
     */
    private void registerEvents()
    {
        // Shared Objects
        blockListener = new CSBlockListener(this);
        playerListener = new CSPlayerListener(this);
        entityListener = new CSEntityListener(this);
        
        // register event listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(blockListener, this);
        pm.registerEvents(playerListener, this);
        pm.registerEvents(entityListener, this);
        
        // debug what we registered
        DebugMode.log(HandlerList.getRegisteredListeners(this).toString());
    }
    
    /**
     * Register all of the commands used, point them to executor
     */
    private void registerCommands()
    {
    	// Shared Ojects
        commandExecutor = new CSCommandExecutor();
        
        // point commands to executor
        getCommand("ccspawner").setExecutor(commandExecutor);
        
        // debug what we loaded
        DebugMode.log("registered command: " + commandExecutor.getCommands());
    }
	
    /**
     * Check for required plugins to be loaded
     */
    private void pluginHooks()
    {
        if (!setupEconomy() )
        {
    		getConfig().set("plugin.vault", false);
        	// TODO: LOG FATAL ERROR
        }
        setupPermissions();
        setupChat();
        getWorldGuard();
        getPstones();
    }
    
    /**
     * Depend on Vault.jar
     * @return
     */
    private boolean setupEconomy()
    {
    	if (getServer().getPluginManager().getPlugin("Vault") == null)
    	{
    		return false;
    	}
    	RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
        {
        	return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    /**
     * Setup chat (part of vault) dependancy
     * @return
     */
    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    /**
     * Setup permissions (part of vault) dependancy
     * @return
     */
    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
    /**
     * Hook into WorldGuard
     * @return
     */
    private WorldGuardPlugin getWorldGuard()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
     
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin))
        {
        	getConfig().set("plugin.worldguard", false);
            return null; // TODO: Maybe you want throw an exception instead
        }
        else
        	getConfig().set("plugin.worldguard", true);
     
        return (WorldGuardPlugin) plugin;
    }
    
    private PreciousStones getPstones()
    {
    	Plugin plugin = getServer().getPluginManager().getPlugin("PreciousStones");
    	
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof PreciousStones))
        {
    		getConfig().set("plugin.preciousstones", false);
            return null; // TODO: Maybe you want throw an exception instead
        }
        else
        	getConfig().set("plugin.preciousstones", true);
     
        return (PreciousStones) plugin;
    }
    
    /**
     *  load the config
     */
    public void load()
    {	
        // if the config isnt there, create a new one
        if(!(new File(getDataFolder(),"config.yml").exists()))
        {
        	logger.info("[ccSpawner] No config.yml found... creating blank file.");
        	saveDefaultConfig();
        }
        
    	// if config is there we load the config.yml, so we can append data
        else if(new File(getDataFolder(),"config.yml").exists())
        {
        	if (getConfig().getInt("plugin.configversion") < cversion)
        	{		
        		logger.warning("[ccSpawner] Outdated config file found...");
        		getConfig().options().copyDefaults(true);
        		getConfig().set("plugin.configversion", cversion);
        		logger.info("[ccSpawner] Updated config file to v" + cversion);
        	}
        	else
        	{
        		getConfig().options().copyDefaults(true);
        		this.saveConfig();
        		logger.info(String.format("[ccSpawner] Config v%s file found!", cversion));
        	}
        }
        
        // create a new sharable object
        config = new CSConfig(this);
    }
    
    /**
     * see if a player can destroy a block.
     * 
     * @param player
     * @param block
     * @return true if player can destroy a block, if plugin doesnt exist default is to allow.
     */
    public boolean canBuild(Player player, Block block)
    {
    	// Default FALSE return unless conditions are true
    	boolean canwg = false;
    	boolean canps = false;
    	
        if (pstones != null)
        {
            if (PreciousStones.API().canBreak(player, block.getLocation()))
            	canps = true;
            DebugMode.log("ps:" + canps);
        }
        else
        	canps = true;
        
        if (WorldGuard != null)
        {
        	// Check if WorldGuard prohibits player
        	if (getWorldGuard().canBuild(player, block) || !getWorldGuard().isEnabled())
        	{
        		canwg = true;
        	}
        	DebugMode.log("wg:" + canwg);
        }
        else
        	canwg = true;
        
        if (canps && canwg)
        	return true;
        
		return false;
    }
    
    /**
     * checks if a player is online
     * 
     * @param playerName (string - use player.getName() if necessary)
     * @return false if player isnt online
     */
	public boolean isOnline(String playerName)
	{
		for (Player player : getServer().getOnlinePlayers())
			if (player.getName().equalsIgnoreCase(playerName))
				return true;

		return false;
	}
	
	/**
	 * @return TownUniverse
	 */
    public TempStorageObject getTempStorageObject()
    {
    	return tso;
    }
    
    /**
     * @return the vault economy instance
     */
    public Economy getEcon()
    {
    	return econ;
    }
    
    /**
     * @return the config
     */
    public CSConfig getConf()
    {
        return config;
    }
    
    /**
     * @return the Locale
     */
    public Locale getLocale()
    {
    	return locale;
    }
    
    /**
     * @return the current plugin instance
     */
    public static CSPlugin getInstance()
    {
        return instance;
    }
    
    /**
     * @return the logger object
     */
    public Logger getLog()
    {
    	return logger;
    }
    
    /**
     * @return the main command executor
     */
    public CSCommandExecutor getCommandExecutor()
    {
        return commandExecutor;
    }
}
