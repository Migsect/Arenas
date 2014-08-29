package me.migsect.Arenas.Commands;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShowState extends ArenaCommand
{
	public CommandShowState(Arenas plugin)
	{
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("state");
		this.setInfo("Shows your state!");
		this.setPerm("arenas.list");
		this.setMinArgs(0);
		this.setMaxArgs(0);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		ArenaPlayer player = plugin.gameHandler.getPlayer((Player) sender);
		player.getPlayer().sendMessage(ArenaHelper.colorEncoding("&eYour current state is: " + player.getState().getStateName()));
		return true;
	}
}
