package me.migsect.Arenas.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.maps.Map;
import me.migsect.Arenas.maps.MapHandler;

public class CommandMapTeleport extends ArenaCommand
{

	public CommandMapTeleport(Arenas plugin) {
		super(plugin);
		
		this.senderTypes.add(SenderType.PLAYER);
		this.setSenderTypes(senderTypes);
		this.setTag("maptp");
		this.setInfo("Teleports you to a lobby of a map");
		this.setPerm("arenas.load");
		this.setMinArgs(1);
		this.setMaxArgs(1);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		// TODO Auto-generated method stub
		Player player = (Player) sender;
		String mapTag = args[1];
		MapHandler handler = plugin.gameHandler.getMapHandler();
		if(!handler.mapExists(mapTag))
		{
			sender.sendMessage(ArenaHelper.colorEncoding(plugin.getConfig().getString("Commands.ErrorMessage.WrongArguments")));
			return true;
		}
		Map map = handler.getMap(mapTag);
		player.teleport(map.getLobby());
		player.sendMessage(ArenaHelper.colorEncoding("&eTeleported to &f" + map.getDisplayName()));
		return true;
	}

}
