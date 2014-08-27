package me.migsect.Arenas.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

public class CommandGM extends ArenaCommand
{

	public CommandGM(Arenas plugin)
	{

		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("gm");
		this.setInfo("Toggles the GM state");
		this.setPerm("arenas.gm");
		this.setMinArgs(0);
		this.setMaxArgs(0);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		ArenaPlayer player = plugin.gameHandler.getPlayer((Player) sender);
		if(plugin.gameHandler.isGameLoaded()) return true;
		if(player.getState().getTag().equalsIgnoreCase("gamm"))
		{
			plugin.gameHandler.setStateLobby(player);
		}
		else
		{
			plugin.gameHandler.setStateGM(player);
		}
		return true;
	}
	
}
