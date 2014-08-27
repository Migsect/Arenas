package me.migsect.Arenas.Commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public enum SenderType
{
	PLAYER, CONSOLE, BLOCK, REMOTE;
	
	public static SenderType getSenderType(CommandSender sender)
	{
		if(sender instanceof Player) return SenderType.PLAYER;
		if(sender instanceof BlockCommandSender) return SenderType.BLOCK;
		if(sender instanceof ConsoleCommandSender) return SenderType.CONSOLE;
		if(sender instanceof RemoteConsoleCommandSender) return SenderType.REMOTE;
		return null;
	}
}
