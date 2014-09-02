package me.migsect.Arenas.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;

// Command help command, this will provide basic use of commands and if a player wishes to know more they can use the /a help commandtag to learn more.
public class CommandHelp extends ArenaCommand{
	public CommandHelp(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("help");
		this.setInfo("Lists all commands and basic use");
		this.setPerm("arenas.info");
		this.setMinArgs(0);
		this.setMaxArgs(1);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		
		String tag = "";
		String helpText = "";
		if((args.length > 1) && (plugin.commandHandler.exists(args[1])))
		{
			ArenaCommand command = plugin.commandHandler.getExecutor(args[1]);
			tag = "/a " + command.getTag();
			helpText = command.getInfo();
			player.sendMessage(ArenaHelper.colorEncoding("&d" + tag + "&6 - " + helpText));
			for(int i = 0; i < command.getMoreInfo().size(); i++)
			{
				player.sendMessage(ArenaHelper.colorEncoding("  &e" + command.getMoreInfo().get(i)));
			}
			
			return true;
		}
		player.sendMessage(ArenaHelper.colorEncoding("&6Available Commands:"));
		List<ArenaCommand> commands = plugin.commandHandler.getCommands();
		for(int i = 0; i < commands.size();i++)
		{
			tag = "/a " + commands.get(i).getTag();
			helpText = commands.get(i).getInfo();
			player.sendMessage(ArenaHelper.colorEncoding("&e - &d" + tag + "&e : " + helpText));
		}
		return true;
	}

}
