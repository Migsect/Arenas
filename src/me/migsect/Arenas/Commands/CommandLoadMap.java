package me.migsect.Arenas.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.maps.Map;
import me.migsect.Arenas.maps.MapHandler;

public class CommandLoadMap extends ArenaCommand
{
	public CommandLoadMap(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("loadmap");
		this.setInfo("Loads the map using a map tag.");
		this.setPerm("arenas.load");
		this.setMinArgs(1);
		this.setMaxArgs(1);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		String mapTag = args[1];
		MapHandler handler = plugin.gameHandler.getMapHandler();
		if(!handler.mapExists(mapTag))
		{
			sender.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
			return true;
		}
		Map map = handler.getMap(mapTag);
		if(!plugin.gameHandler.isGameLoaded())
		{
			sender.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
			return true;
		}
		ArenaGame game = plugin.gameHandler.getLoadedGame();
		if(!map.canSupportGame(game.getTag()))
		{
			sender.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
			return true;
		}
		handler.loadMap(map);
		sender.sendMessage(ArenaHelper.colorEncoding("&6Map Loaded: &e" + plugin.gameHandler.getMapHandler().getLoadedMap().getDisplayName()));
		return true;
	}
}
