package me.migsect.Arenas.Commands;

import java.util.List;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCommands extends ArenaCommand
{
	public CommandCommands(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("");
		this.setInfo("Lists all commands to you");
		this.setPerm("arenas.info");
		this.setMinArgs(0);
		this.setMaxArgs(0);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		player.sendMessage(ArenaHelper.colorEncoding("&6Available Commands:"));
		List<ArenaCommand> commands = plugin.commandHandler.getCommands();
		for(int i = 0; i < commands.size();i++)
		{
			player.sendMessage(ArenaHelper.colorEncoding(" &e- &d/a " + commands.get(i).getTag() + " &e: " + commands.get(i).getInfo()));
		}
		return true;
	}

}
