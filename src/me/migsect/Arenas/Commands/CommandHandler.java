package me.migsect.Arenas.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;

public class CommandHandler implements CommandExecutor
{
	private Arenas plugin;
	private static HashMap<String, ArenaCommand> commands = new HashMap<String, ArenaCommand>();
	
	
	public CommandHandler(Arenas plugin)
	{
		this.plugin = plugin;
	}
	
	public void register(ArenaCommand command)
	{
		commands.put(command.getTag(),command);
	}
	
	public boolean exists(String name)
	{
		return commands.containsKey(name);
	}
	public ArenaCommand getExecutor(String name)
	{
		return commands.get(name);
	}
	public List<ArenaCommand> getCommands()
	{
		List<ArenaCommand> list = new ArrayList<ArenaCommand>();
		list.addAll(commands.values());
		return list;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args)
	{
		// To simplify the process, this will send each command call through a test to see if that command is viable.  Using the command interface we can easily
		// Check to see if these are all correct:
		//  - If the command exists (which will be args[0])
		//  - If the command accepts the user type
		//  - If the command accepts the amount of arguments (args[1] through args[x]
		//  - If the command user has permission. (Only if the user is a player)
		if(args.length == 0) 
		{
			getExecutor("").onCommand(sender, cmd, commandLabel, args);
			return true;
		}
		if(!isCommand(sender, args[0])) return true;
		if(!isUsable(sender, args[0], args.length - 1)) return true;
		if(!isAllowed(sender, args[0])) return true;
		getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
		return true;
	}
	
	// ================================>
	// Helper Methods below this point
	// ================================>
	private boolean isCommand(CommandSender sender, String tag)
	{
		if (!exists(tag))
		{
			String errorMsg = ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.Exists"));
			sender.sendMessage(errorMsg);
			return false;
		}
		return true;
	}
	private boolean isUsable(CommandSender sender, String tag, int argsLength)
	{
		if(!getExecutor(tag).isAble(sender))
		{
			String errorMsg = ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.Sender"));
			sender.sendMessage(errorMsg);
			return false;
		}
		if(!getExecutor(tag).isUsable(argsLength))
		{
			String errorMsg = ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.Arguments"));
			sender.sendMessage(errorMsg);
			return false;
		}
		return true;
	}
	private boolean isAllowed(CommandSender sender, String tag)
	{
		if(!getExecutor(tag).isAllowed(sender))
		{
			String errorMsg = ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.Permission"));
			sender.sendMessage(errorMsg);
			return false;
		}
		return true;
	}
}
