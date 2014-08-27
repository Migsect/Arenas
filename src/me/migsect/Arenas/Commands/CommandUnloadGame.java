package me.migsect.Arenas.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;

public class CommandUnloadGame extends ArenaCommand
{

	public CommandUnloadGame(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("unload");
		this.setInfo("Unloads the current game.");
		this.setPerm("arenas.load");
		this.setMinArgs(0);
		this.setMaxArgs(0);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		
		sender.sendMessage(ArenaHelper.colorEncoding("&6Game Unloaded: &e" + plugin.gameHandler.getLoadedGame().getGameName()));
		plugin.gameHandler.unloadGame();
		return true;
	}

}
