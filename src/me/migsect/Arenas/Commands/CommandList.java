package me.migsect.Arenas.Commands;

import java.util.List;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.maps.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandList extends ArenaCommand
{
	public CommandList(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("list");
		this.setInfo("Lists all available game types.");
		this.setPerm("arenas.list");
		this.setMinArgs(0);
		this.setMaxArgs(0);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		player.sendMessage(ArenaHelper.colorEncoding("&6Available Game Types:"));
		List<ArenaGame> gameTypes = plugin.gameHandler.getGameTypes();
		for(int i = 0; i < gameTypes.size(); i++)
			player.sendMessage(ArenaHelper.colorEncoding(" &e- &d"+ gameTypes.get(i).getTag() + " &e: " + gameTypes.get(i).getGameName()));
		
		player.sendMessage(ArenaHelper.colorEncoding("&6Available Maps:"));
		List<Map> maps = plugin.gameHandler.getMapHandler().getMaps();
		for(int i = 0; i < maps.size(); i++)
		{
			player.sendMessage(ArenaHelper.colorEncoding(" &e- &d"+ maps.get(i).getTag() + " &e: " + maps.get(i).getDisplayName()));
		}
		return true;
	}


}
