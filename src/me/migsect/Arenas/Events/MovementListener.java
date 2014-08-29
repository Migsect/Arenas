package me.migsect.Arenas.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

public class MovementListener implements Listener
{
	Arenas plugin;
	
	public MovementListener(Arenas plugin)
	{
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMovement(PlayerMoveEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerMove(event);
		if(event.isCancelled()) return;
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(!player.canMove()) 
		{
			// Note, we are not going to cancel the event.  Instead we are going to just reset the player's position to their
			//   last and not change their facing direction.
			event.getTo().setX(event.getFrom().getX());
			event.getTo().setY(event.getFrom().getY());
			event.getTo().setZ(event.getFrom().getZ());
			return;
		}
		
		// Void Handling
		int mapBottom = 0;
		if(plugin.gameHandler.getMapHandler().isMapLoaded()) mapBottom = plugin.gameHandler.getMapHandler().getLoadedMap().getMapBottom();
  	if(event.getTo().getBlockY() < mapBottom)
  	{
  		if(!player.getState().canBeDamaged())
  		{
  			if(player.hasLastDeathLocation())  player.getPlayer().teleport(player.getLastDeathLocation());
  			else if(plugin.gameHandler.getMapHandler().isMapLoaded()) player.getPlayer().teleport(plugin.gameHandler.getMapHandler().getLoadedMap().getCenter());
  		}
  		else
  		{
  			
  			if(!player.getPlayer().isDead()) player.getPlayer().setHealth(0);
  		}
  	}
		// if the player's state allows it, then the loaded game
		//   will then handle the event if it does anything.
		if(plugin.gameHandler.isGameLoaded())  plugin.gameHandler.getLoadedGame().onListenPlayerMove(player, event.getTo(), event.getFrom());
		if(player.hasTeam()) player.getTeam().onListenPlayerMove(player, event.getTo(), event.getFrom());
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerToggleSneak(event);
		if(event.isCancelled()) return;
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(!player.canSneak() && !player.getPlayer().isSneaking())
		{
			event.setCancelled(true);
			return;
		}
		if(player.getPlayer().isSneaking())
		{
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerSneak(player);
			if(player.hasTeam()) player.getTeam().onListenPlayerSneak(player);
		}
		else
		{
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerUnSneak(player);
			if(player.hasTeam()) player.getTeam().onListenPlayerUnSneak(player);
		}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerSprintToggle(PlayerToggleSprintEvent event)
	{
		// if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerToggleSprint(event);
		// if(event.isCancelled()) return;
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(player.getPlayer().isSprinting())
		{
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerSprint(player);
			if(player.hasTeam()) player.getTeam().onListenPlayerSprint(player);
		}
		else
		{
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerUnSprint(player);
			if(player.hasTeam()) player.getTeam().onListenPlayerUnSprint(player);
		}
		
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerFlightToggle(PlayerToggleFlightEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().oneventPlayerToggleFlight(event);
		if(event.isCancelled()) return;
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		if(!player.canFly() && !player.getPlayer().isFlying())
		{
			event.setCancelled(true);
			return;
		}
		if(player.getPlayer().isFlying())
		{

			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerFlight(player);
			if(player.hasTeam()) player.getTeam().onListenPlayerFlight(player);
		}
		else
		{

			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerUnFlight(player);
			if(player.hasTeam()) player.getTeam().onListenPlayerUnFlight(player);
		}
	}
}
