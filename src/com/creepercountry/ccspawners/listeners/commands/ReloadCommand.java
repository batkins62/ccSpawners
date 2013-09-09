package com.creepercountry.ccspawners.listeners.commands;

import org.bukkit.command.CommandSender;

public class ReloadCommand extends BaseCommand
{
    public ReloadCommand()
    {
        name = "reload";
        usage = "";
    }

    @Override
    public boolean execute()
    {
    	plugin.saveConfig();
        return true;
    }

    @Override
    public boolean permission(CommandSender csender)
    {
        return csender.hasPermission("cs.reload");
    }

    @Override
    public BaseCommand newInstance()
    {
        // TODO Auto-generated method stub
        return new ReloadCommand();
    }
}
