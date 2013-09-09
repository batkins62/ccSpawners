package com.creepercountry.ccspawners.listeners.commands;

import org.bukkit.command.CommandSender;

import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.Colors;

public class TotalEntitysCommand extends BaseCommand
{
    public TotalEntitysCommand()
    {
        name = "total";
        usage = "";
    }

    @Override
    public boolean execute()
    {
    	BukkitUtils.sendMessage(player, Colors.LightBlue, "Total entitys spawned: " + plugin.totalSpawned);
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
        return new TotalEntitysCommand();
    }
}
