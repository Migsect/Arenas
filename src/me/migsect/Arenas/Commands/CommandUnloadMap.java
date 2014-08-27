package me.migsect.Arenas.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;

public class CommandUnloadMap extends ArenaCommand
{
	public CommandUnloadMap(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("unloadmap");
		this.setInfo("Unloads the current map.");
		this.setPerm("arenas.load");
		this.setMinArgs(0);
		this.setMaxArgs(0);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		if(!plugin.gameHandler.getMapHandler().isMapLoaded())
		{
			sender.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
			return true;
		}
		sender.sendMessage(ArenaHelper.colorEncoding("&6Map Unloaded: &e" + plugin.gameHandler.getMapHandler().getLoadedMap().getDisplayName()));
		plugin.gameHandler.getMapHandler().unloadMap();
		return true;
	}	
}
