package me.migsect.Arenas.Events;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class LoginListener implements Listener
{
	Arenas plugin;
	
	public LoginListener(Arenas plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		// Player teleport: To Lobby or the Loaded map.  This will occur on every single login.
		if(plugin.gameHandler.getMapHandler().isMapLoaded()) player.teleport(plugin.gameHandler.getMapHandler().getLoadedMap().getLobby());
		else player.teleport(plugin.gameHandler.getMapHandler().getMainLobby());
		
		ArenaPlayer gamePlayer = plugin.gameHandler.registerPlayer(player);
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onPlayerJoinGame(gamePlayer);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogout(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		ArenaPlayer gamePlayer = plugin.gameHandler.getPlayer(player);
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onPlayerLeaveGame(gamePlayer);
		plugin.gameHandler.deregisterPlayer(player);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerRespawn(event);
	}
}
