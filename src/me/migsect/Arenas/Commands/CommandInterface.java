package me.migsect.Arenas.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface
{
	public String getTag();
	public String getInfo();
	public String getPerm();
	public boolean isUsable(CommandSender sender);
	public boolean isUsable(int argsLength);
	public boolean isAllowed(CommandSender sender);
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);
	
}
