package me.migsect.Arenas.Events;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener
{
	Arenas plugin;
	public BlockListener(Arenas plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		// onBlockBreak is special because we HAVE to let it happen due to lobby.  It doesn't matter if a game
		//   is loaded.  We will check that later, first we need to chek the player's state.
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventBlockBreakEvent(event);
		if(event.isCancelled()) return;
		
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		Block block = event.getBlock();
		if(player.hasState() && !player.getState().canBreak(block)) 
		{
			event.setCancelled(true);
			return;
		}
		if(!plugin.gameHandler.isGameLoaded()) return;
		plugin.gameHandler.getLoadedGame().onListenPlayerBlockBreak(player,block);
		if(player.hasTeam()) player.getTeam().onListenPlayerBlockBreak(player, block);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventBlockPlaceEvent(event);
		if(event.isCancelled()) return;
		
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		Block block = event.getBlock();
		if(player.hasState() && !player.getState().canPlace()) 
		{
			event.setCancelled(true);
			return;
		}
		
		if(!plugin.gameHandler.isGameLoaded()) return;
		plugin.gameHandler.getLoadedGame().onListenPlayerPlaceBlock(player,block);
		if(player.hasTeam()) player.getTeam().onListenPlayerPlaceBlock(player, block);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerInteract(event);
		if(event.isCancelled()) return;
		if(!plugin.gameHandler.isGameLoaded()) return;
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		Block block  = event.getClickedBlock();
		
		plugin.gameHandler.getLoadedGame().onListenPlayerInteract(player, block);
		if(player.hasTeam()) player.getTeam().onListenPlayerInteract(player, block);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerInteractEntity(event);
		if(event.isCancelled()) return;
		if(!plugin.gameHandler.isGameLoaded()) return;
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		Entity entity = event.getRightClicked();
		

		plugin.gameHandler.getLoadedGame().onListenPlayerInteractEntity(player, entity);
		if(player.hasTeam()) player.getTeam().onListenPlayerInteractEntity(player, entity);
	}

}
