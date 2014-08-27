package me.migsect.Arenas.Commands;

import java.util.List;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandLoadGame extends ArenaCommand
{
	public CommandLoadGame(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("load");
		this.setInfo("Loads the game using a game tag.");
		this.setPerm("arenas.load");
		this.setMinArgs(1);
		this.setMaxArgs(1);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		String gameTag = args[1];
		List<ArenaGame> games = plugin.gameHandler.getGameTypes();
		for(int i = 0;i < games.size();i++)
		{
			if(games.get(i).getTag().equalsIgnoreCase(gameTag))
			{
				plugin.gameHandler.loadGame(games.get(i));
				sender.sendMessage(ArenaHelper.colorEncoding("&6Game Loaded: &e" + plugin.gameHandler.getLoadedGame().getGameName()));
				return true;
			}
		}
		sender.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
		return true;
	}


}
