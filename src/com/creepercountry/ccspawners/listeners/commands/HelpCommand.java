package com.creepercountry.ccspawners.listeners.commands;

import org.bukkit.command.CommandSender;

import com.creepercountry.ccspawners.main.CSInfo;
import com.creepercountry.ccspawners.util.BukkitUtils;
import com.creepercountry.ccspawners.util.Colors;

public class HelpCommand extends BaseCommand
{	
    public HelpCommand()
    {
        name = "help";
        maxArgs = 1;
        minArgs = 0;
        usage = "<- lists all ccSpawner commands";
    }

    @Override
    public boolean execute()
    {
        // General help
        if (args.isEmpty())
        {
            BukkitUtils.sendMessage(sender, Colors.LightBlue, cleanTitle("ccSpawner v" + CSInfo.FULL_VERSION.toString(), "="));
            BukkitUtils.sendMessage(sender, Colors.LightBlue, "Type /cc help <command> for more info on that command");
            /*
            List<BaseCommand> var = plugin.getCommandExecutor().getCommands();
            for (BaseCommand cmd : var.toArray(new BaseCommand[var.size()]))
            {
                if (cmd.permission(sender))
                {
                    BukkitUtils.sendMessage(sender, Colors.LightBlue, "- " + Colors.Green + "/" + usedCommand + " " + cmd.name + Colors.Green + " " + cmd.usage);
                }
            }
            */
        }
        else // Command-specific help
        {
        	/*
            List<BaseCommand> var = plugin.getCommandExecutor().getCommands();
            for (BaseCommand cmd : var.toArray(new BaseCommand[var.size()]))
            {
                if (cmd.permission(sender) && cmd.name.equalsIgnoreCase(args.get(0)))
                {
                    BukkitUtils.sendMessage(sender, Colors.LightBlue, "---------------------- ccSpawner - " + cmd.name);
                    BukkitUtils.sendMessage(sender, Colors.LightBlue, "- " + Colors.Green + "/" + usedCommand + " " + cmd.name + Colors.Green + " " + cmd.usage);
                    cmd.sender = sender;
                    cmd.moreHelp();
                    return true;
                }
            }
            */
            BukkitUtils.sendMessage(sender, Colors.Red, "No command found by that name");
        }
        return true;
    }

    @Override
    public void moreHelp()
    {
        BukkitUtils.sendMessage(sender, Colors.LightBlue, "Shows all Guardian commands");
        BukkitUtils.sendMessage(sender, Colors.Red, "Type " + Colors.Gray + "/cc help <command>" + Colors.Red + " for help on that command");
    }

    @Override
    public boolean permission(CommandSender csender)
    {
        return true;
    }

    @Override
    public BaseCommand newInstance()
    {
        return new HelpCommand();
    }
    
    public String cleanTitle(String title, String fill)
    {
    	// Formats a string with a provided title and padding and centers title
        int chatWidthMax = 53;
        // Vanilla client line character max
        int titleWidth = title.length() + 2;
        // Title's character width with 2 spaces padding
        int fillWidth = (int) ((chatWidthMax - titleWidth) / 2D);
        // Fill string calculation for padding either side
        String cleanTitle = "";
        
        for(int i = 0; i < fillWidth; i++)
            cleanTitle += fill;
        cleanTitle += " " + title + " ";
        for(int i = 0; i < fillWidth; i++)
            cleanTitle += fill;
        
        return cleanTitle;
    }
}