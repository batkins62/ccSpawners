package com.creepercountry.ccspawners.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.material.MaterialData;

import com.creepercountry.ccspawners.main.CSInfo;

public class BukkitUtils
{
	/**
	 * Logger static instance
	 */
	private static final Logger log = Logger.getLogger("ccSpawner");
	
	/**
	 * Static permissions instance
	 */
	static Permission permission;

	public static String materialName(int type)
	{
		final Material mat = Material.getMaterial(type);
		return mat != null ? mat.toString().replace('_', ' ').toLowerCase() : String.valueOf(type);
	}

	public static String materialName(int type, byte rawData)
	{
		final Material mat = Material.getMaterial(type);
		if (mat != null)
		{
			if ((type == 6 || type == 17 || type == 18) && rawData > 0 || type == 35 || type == 43 || type == 44)
			{
				final MaterialData data = mat.getNewData(rawData);
				if (data != null)
				{
					return data.toString().toLowerCase().replace('_', ' ').replaceAll("[^a-z ]", "");
				}
			}
			return mat.toString().replace('_', ' ').toLowerCase();
		}
		return String.valueOf(type);
	}

	/**
	 * Send a message to a CommandSender (can be a player or console).
	 *
	 * @param player sender to send to
	 * @param msg message to send
	 * @param clr color of message
	 */
	public static void sendMessage(CommandSender player, String clr, String msg)
	{
		if (player != null)
		{
			player.sendMessage(clr + msg);
		}
		// TODO: add in line-length checking, color wrapping etc
	}
	
    /**
     * Returns true if the given Player has the specific permission
     *
     * @param player The Player who is being checked for permission
     * @param type The String of the permission, ex. admin
     * @return True if the given Player has the specific permission
     */
    public static boolean hasPermission(Player player, String type)
    {
        return permission.has(player, "cs." + type);
    }

	/**
	 * Send an info level log message to console
	 * includes: version 
	 *
	 * @param msg
	 */
	public static void info(String msg)
	{
		final StringBuilder out = new StringBuilder();
		out.append("[ccSpawner v" + CSInfo.FULL_VERSION.toString() + "] ");
		out.append(msg);
		log.log(Level.INFO, out.toString());
	}

	/**
	 * Send a warn level log message to console
	 * includes: version
	 *
	 * @param msg
	 */
	public static void warning(String msg)
	{
		final StringBuilder out = new StringBuilder();
		out.append("[ccSpawner v" + CSInfo.FULL_VERSION.toString() + "] ");
		out.append(msg);
		log.log(Level.WARNING, out.toString());
	}

	/**
	 * Send a warn level stacktrace to console
	 * includes: version
	 *
	 * @param msg
	 * @param ex
	 */
	public static void warning(String msg, Exception ex)
	{
		log.log(Level.WARNING, "[ccSpawner v" + CSInfo.FULL_VERSION.toString() + "] " + msg + ":", ex);
	}

	/**
	 * Send a severe level log message to console
	 * includes: version
	 *
	 * @param msg
	 */
	public static void severe(String msg)
	{
		final StringBuilder out = new StringBuilder();
		out.append("[ccSpawner v" + CSInfo.FULL_VERSION.toString() + "] ");
		out.append(msg);
		log.log(Level.SEVERE, out.toString());
	}

	/**
	 * Send a severe level stacktrace to console
	 * includes: version
	 *
	 * @param msg
	 * @param ex
	 */
	public static void severe(String msg, Exception ex)
	{
		log.log(Level.SEVERE, "[ccSpawner v" + CSInfo.FULL_VERSION.toString() + "] " + msg + ":", ex);
	}
	
	/**
	 * Send a severe level stacktrace to console
	 * includes: version
	 *
	 * @param msg
	 * @param ex
	 */
	public static void severe(String msg, NoSuchFieldError ex)
	{
		log.log(Level.SEVERE, "[ccSpawner v" + CSInfo.FULL_VERSION.toString() + "] " + msg + ":", ex);
	}
	
    /**
     * Join an array into a String, where the array values are delimited by spaces.
     *
     * @param arr
     * @return
     */
    public static String join(String[] arr)
    {
        return join(arr, 0);
    }

    /**
     * Join an array into a String, where the array values are delimited by spaces, starting at the given offset.
     *
     * @param arr
     * @param offset
     * @return
     */
    public static String join(String[] arr, int offset)
    {
        return join(arr, offset, " ");
    }

    /**
     * Join an array into a String, where the array values are delimited by the given string, starting at the given offset.
     *
     * @param arr
     * @param offset
     * @param delim
     * @return
     */
    public static String join(String[] arr, int offset, String delim)
    {
        String str = "";

        if (arr == null || arr.length == 0)
        {
            return str;
        }

        for (int i = offset; i < arr.length; i++)
        {
            str += arr[i] + delim;
        }

        return str.trim();
    }
	
	/**
	 * Log a FATAL error, this will provide features to continue.
	 * 
	 * @param msg
	 * @param disable
	 * @param log
	 */
	public static void fatal(String msg, boolean disable, boolean log)
	{
		// TODO: finish...
	}

	/**
	 * Returns the friendly bridgeName of an entity
	 *
	 * @param entity
	 * @return
	 */
	public static String getEntityName(Entity entity)
	{
		if (entity instanceof Player)
		{
			return ((Player) entity).getName();
		}
		if (entity instanceof TNTPrimed)
		{
			return "TNT";
		}
		if (entity.getClass().getSimpleName().startsWith("Craft"))
		{
			return entity.getClass().getSimpleName().substring(5);
		}
		return "Unknown";
	}

	/**
	 * Returns the distance between two Locations
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static double distance(Location from, Location to)
	{
		return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2) + Math.pow(from.getZ() - to.getZ(), 2));
	}
}
