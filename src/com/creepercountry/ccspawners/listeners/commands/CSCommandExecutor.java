package com.creepercountry.ccspawners.listeners.commands;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CSCommandExecutor implements CommandExecutor
{
    private List<BaseCommand> commands = new ArrayList<BaseCommand>();

    public CSCommandExecutor()
    {
        // Register commands
        commands.add(new HelpCommand());
        commands.add(new DebugOnCommand());
        commands.add(new DebugOffCommand());
        commands.add(new LookupCommand());
        commands.add(new TotalEntitysCommand());
        // TODO: create payout
        //commands.add(new SetPayoutCommand());
        commands.add(new ReloadCommand());
        // TODO: create confirm
        //commands.add(new ConfirmCommand());
    }

    /**
     * Command manager for ccspawner
     *
     * @param sender - {@link CommandSender}
     * @param command - {@link Command}
     * @param label command name
     * @param args arguments
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        // If no arg provided for ccspawner command, set to help by default
        if (args.length == 0)
        {
            args = new String[]{"help"};
        }

        // Loop through commands to find match. Supports sub-commands
        BaseCommand CSCmd;
        BaseCommand[] csCmdArray = commands.toArray(new BaseCommand[commands.size()]);
        int index = 0;
        String[] tempArgs = args;
        
        while (index < csCmdArray.length && tempArgs.length > 0)
        {
            CSCmd = csCmdArray[index];
            if(tempArgs[0].equalsIgnoreCase(CSCmd.name))
            {
                if(CSCmd.subCommands.size() > 0 && tempArgs.length > 1)
                {
                    csCmdArray = CSCmd.subCommands.toArray(new BaseCommand[CSCmd.subCommands.size()]);
                    index = 0;
                    tempArgs = (String[]) ArrayUtils.remove(tempArgs, 0);
                }
                else
                {
                    tempArgs = (String[]) ArrayUtils.remove(tempArgs, 0);
                    return CSCmd.newInstance().run(sender, tempArgs, label);
                }
            }
            else
            {
                index++;
            }
        }
        
        new HelpCommand().run(sender, args, label);
        return true;
    }

    /**
     * @return the commands
     */
    public List<BaseCommand> getCommands()
    {
        return commands;
    }
}
