package me.migsect.Arenas.Events;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemListener implements Listener
{
	Arenas plugin;
	
	public ItemListener(Arenas plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickup(PlayerPickupItemEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerPickupItem(event);
		if(event.isCancelled()) return;
		
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(!player.canPickup()){
			event.isCancelled();
			return;
		}
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerPickup(player, event.getItem());
		if(player.hasTeam()) player.getTeam().onListenPlayerPickup(player, event.getItem());

		
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDrop(PlayerDropItemEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerDropItem(event);
		if(event.isCancelled()) return;
		
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(!player.canDrop())
		{
			event.isCancelled();
			return;
		}
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerDrop(player, event.getItemDrop());
		if(player.hasTeam()) player.getTeam().onListenPlayerDrop(player, event.getItemDrop());
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerItemConsume(PlayerItemConsumeEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerItemConsume(event);
		if(event.isCancelled()) return;
		
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(!player.canConsume())
		{
			event.isCancelled();
			return;
		}
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerConsume(player, event.getItem());
		if(player.hasTeam()) player.getTeam().onListenPlayerConsume(player, event.getItem());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerItemHeld(PlayerItemHeldEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerItemHeld(event);
		if(event.isCancelled()) return;
		
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerConsume(player,  event.getPlayer().getItemInHand());
		if(player.hasTeam()) player.getTeam().onListenPlayerConsume(player, event.getPlayer().getItemInHand());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerExpChange(PlayerExpChangeEvent event)
	{
		// if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerExpChange(event);
		// if(event.isCancelled()) return;
		
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerExpChange(player,  event.getAmount());
		if(player.hasTeam()) player.getTeam().onListenPlayerExpChange(player, event.getAmount());
	}
}
