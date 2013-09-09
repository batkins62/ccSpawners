package com.creepercountry.ccspawners.listeners.commands;

import org.bukkit.command.CommandSender;

import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.DebugMode;
import com.griefcraft.util.Colors;

public class DebugOffCommand extends BaseCommand
{
    public DebugOffCommand()
    {
        name = "debugoff";
        usage = "";
    }

    @Override
    public boolean execute()
    {
    	DebugMode.stop();
    	BukkitUtils.sendMessage(sender, Colors.Blue, "Debug mode disabled!");
        return true;
    }

    @Override
    public boolean permission(CommandSender csender)
    {
        return csender.hasPermission("cs.debug.off");
    }

    @Override
    public BaseCommand newInstance()
    {
        // TODO Auto-generated method stub
        return new DebugOffCommand();
    }
}
