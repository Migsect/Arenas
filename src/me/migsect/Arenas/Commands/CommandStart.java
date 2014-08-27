package me.migsect.Arenas.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.migsect.Arenas.Arenas;

public class CommandStart extends ArenaCommand
{
	public CommandStart(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("start");
		this.setInfo("Starts the current arena game. Both map and game need to be loaded.");
		this.setPerm("arenas.start");
		this.setMinArgs(0);
		this.setMaxArgs(1);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		if(!plugin.gameHandler.isGameLoaded())
		{
			sender.sendMessage(ChatColor.RED + "You need to have a game loaded first.");
			return true;
		}
		if(!plugin.gameHandler.getMapHandler().isMapLoaded())
		{
			sender.sendMessage(ChatColor.RED + "You need to have a map loaded first.");
			return true;
		}
		if(plugin.gameHandler.getLoadedGame().isRunning())
		{
			sender.sendMessage(ChatColor.RED + "A game is currently running.");
			return true;
		}
		if(args.length == 1)
		{
  		sender.sendMessage(ChatColor.YELLOW + "Starting game...");
  		plugin.gameHandler.startGame();
  		return true;
		}
		if(args.length == 2)
		{
			int seconds = 0;
			try
			{
				seconds = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e)
			{
				plugin.logger.warning("Player entered wrong time.");
				return true;
			}
			plugin.gameHandler.getLoadedGame().gameStartDelayed(seconds);
			return true;
		}
		return true;
	}
}
