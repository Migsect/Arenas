package me.migsect.Arenas.Events;

import me.migsect.Arenas.Arenas;

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
		plugin.gameHandler.registerPlayer(player);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogout(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		plugin.gameHandler.deregisterPlayer(player);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerRespawn(event);
	}
}
