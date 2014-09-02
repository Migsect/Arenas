package me.migsect.Arenas.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.GameTypes.GameHandler;
import me.migsect.Arenas.maps.Map;

public class CommandInfo extends ArenaCommand
{

	public CommandInfo(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("info");
		this.setInfo("Displays all current arena info.");
		this.moreInfo.add("  &eType &dMap <maptag>&e to get info on that map.");
		this.moreInfo.add("  &eType &dGame <gametag>&e to get more info on that game.");
		this.setPerm("arenas.info");
		this.setMinArgs(0);
		this.setMaxArgs(2);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		GameHandler handler = plugin.gameHandler;
		Player player = (Player) sender;
		
		if(args.length == 1)
		{
  		player.sendMessage(ArenaHelper.colorEncoding("&6Arena Current Details:"));
  		
  		String loadedGameName = "Nil";
  		if(handler.isGameLoaded()) loadedGameName = handler.getLoadedGame().getGameName();
  		player.sendMessage(ArenaHelper.colorEncoding("  &6Current Game: &e" + loadedGameName));
  		String loadedMapName = "Nil";
  		if(handler.getMapHandler().isMapLoaded()) loadedMapName = handler.getMapHandler().getLoadedMap().getDisplayName();
  		player.sendMessage(ArenaHelper.colorEncoding("  &6Current Map: &e" + loadedMapName));
  		
  		player.sendMessage(ArenaHelper.colorEncoding("  &6Players: &e" + handler.getPlayerCount()));
  		player.sendMessage(ArenaHelper.colorEncoding("    &6Active: &e" + handler.getActivePlayers().size()));
  		player.sendMessage(ArenaHelper.colorEncoding("    &6Spectating: &e" + handler.getSpectators().size()));
  		player.sendMessage(ArenaHelper.colorEncoding("    &6GameMastering: &e" + handler.getGameMasters().size()));
  		return true;
		}
		if(args.length == 2) 
		{
			player.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
			return true;
		}
		if(args.length == 3)
		{
			if(args[1].equalsIgnoreCase("map"))
			{
				if(!handler.getMapHandler().mapExists(args[2]))
				{
					player.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
					return true;
				}
				Map map = handler.getMapHandler().getMap(args[2]);
				player.sendMessage(ArenaHelper.colorEncoding("&6" + map.getDisplayName() + " Details:"));
				player.sendMessage(ArenaHelper.colorEncoding("  &6Lobby Location: &e" + map.getLobby().getBlockX() + ", " + map.getLobby().getBlockY() + ", " + map.getLobby().getBlockZ()));
				player.sendMessage(ArenaHelper.colorEncoding("  &6General Spawns: &e" + map.getGenSpawns().size()));
				String supportedGames = "";
				for(int i = 0; i < map.getGameTypes().size(); i++)
				{
					if(i > 0) supportedGames += ", ";
					supportedGames += map.getGameTypes().get(i);
				}
				player.sendMessage(ArenaHelper.colorEncoding("  &6Supported Games: &e" + supportedGames));
				return true;
			}
			if(args[1].equalsIgnoreCase("game"))
			{
				if(!handler.gameExists(args[2]))
				{
					player.sendMessage(args[2]);
					player.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
  				return true;
				}
				ArenaGame game = handler.getGame(args[2]);
				player.sendMessage(ArenaHelper.colorEncoding("&6" + game.getGameName() + " Details:"));
				player.sendMessage(ArenaHelper.colorEncoding("  &6CanDrop: &e" + game.canDrop()));
				player.sendMessage(ArenaHelper.colorEncoding("  &6CanPickup: &e" + game.canPickup()));
				return true;
			}
		}
		return true;
	}

}
