package com.creepercountry.ccspawners.main;

import com.creepercountry.ccspawners.util.Version;



public class CSInfo
{
	/**
	 * ccSpawner Full Version
	 */
    public static Version FULL_VERSION;

    /**
     * set plugin version
     * 
     * @param version
     */
    public static void setVersion(String version)
    {
        String implementationVersion = CSPlugin.class.getPackage().getImplementationVersion();
        FULL_VERSION = new Version(version + " " + implementationVersion);
    }
}
