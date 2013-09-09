package com.creepercountry.ccspawners.listeners.commands;

import org.bukkit.command.CommandSender;

import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.DebugMode;
import com.griefcraft.util.Colors;

public class DebugOnCommand extends BaseCommand
{
    public DebugOnCommand()
    {
        name = "debugon";
        usage = "";
    }

    @Override
    public boolean execute()
    {
    	DebugMode.go();
    	BukkitUtils.sendMessage(sender, Colors.Blue, "Debug mode activated!");
        return true;
    }

    @Override
    public boolean permission(CommandSender csender)
    {
        return csender.hasPermission("cs.debug.on");
    }

    @Override
    public BaseCommand newInstance()
    {
        // TODO Auto-generated method stub
        return new DebugOnCommand();
    }
}
