package com.creepercountry.ccspawners.util;

import java.util.logging.Logger;

public class DebugMode
{
    private final Logger logger;
    private static DebugMode instance = null;
    
    /**
     * Constructor
     */
    public DebugMode()
    {
        this.logger = Logger.getLogger("Minecraft");
    }

    public static void go()
    {
        BukkitUtils.warning("Debug enabled. Disable via /ccspawner debugoff");
        DebugMode.instance = new DebugMode();
    }

    public static void log(String message)
    {
        if (DebugMode.instance != null)
        {
        	DebugMode.instance.logger.info("[CS DEBUG] " + message);
        }
    }

    public static void stop()
    {
    	BukkitUtils.info("Debug disabled. Enable via /ccspawner debugon");
    	DebugMode.instance = null;
    }
}
