package me.migsect.Arenas.Commands;

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
	
	// Just a note: commands cannot be bigger than 12 chars, this is to make them easier to type.
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		Player player = (Player) sender;
		player.sendMessage(ArenaHelper.colorEncoding("&6For more commands use &d/a help &6for basic command use."));
		return true;
	}

}
