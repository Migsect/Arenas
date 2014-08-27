package me.migsect.Arenas.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.migsect.Arenas.Arenas;

public class CommandStop extends ArenaCommand
{
	public CommandStop(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("stop");
		this.setInfo("Stops the currently running game. Does not unload the game.");
		this.setPerm("arenas.start");
		this.setMinArgs(0);
		this.setMaxArgs(0);
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
		if(!plugin.gameHandler.getLoadedGame().isRunning())
		{
			sender.sendMessage(ChatColor.RED + "No game is currently running.");
			return true;
		}
		plugin.gameHandler.getLoadedGame().gameEnd();
		plugin.gameHandler.messageAllPlayers(ChatColor.YELLOW + "The currently running game has been stopped.");
		return true;
	
	}

}
