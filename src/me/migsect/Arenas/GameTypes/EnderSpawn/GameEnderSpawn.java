package me.migsect.Arenas.GameTypes.EnderSpawn;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.Players.ArenaPlayer;

public class GameEnderSpawn extends ArenaGame
{
	TeamEnderSpawn enderSpawn;
	TeamMiners miners;
	
	public GameEnderSpawn(Arenas plugin)
	{
		super(plugin);
		gameName = "EnderSpawn";
		gameTag = "ends";
		maxPlayers = 21;
		minPlayers = 2;
		
		enderSpawn = new TeamEnderSpawn(plugin, this);
		teams.add(enderSpawn);
		miners = new TeamMiners(plugin, this);
		teams.add(miners);
	}
	
	@Override
	public void gameInitiate()
	{
	}

	@Override
	public void gameStart()
	{
	}

	@Override
	public void gameTerminate()
	{
	}

	@Override
	public void gameEnd()
	{
	}

	@Override
	public void gameReset()
	{
	}

	@Override
	public void onPlayerJoinGame(ArenaPlayer player)
	{
	}

	@Override
	public void onPlayerLeaveGame(ArenaPlayer player)
	{
	}

	@Override
	public void respawnPlayer(ArenaPlayer player)
	{
	}

	@Override
	public void onTick(int gameTick)
	{
	}


	@Override
	public void onTagTask(String tag)
	{
	}

	@Override
	public void onTagTask(String tag, List<ArenaPlayer> players)
	{
	}

	@Override
	public void onEventEntityDeath(EntityDeathEvent event)
	{
	}

	@Override
	public void onEventPlayerDeath(PlayerDeathEvent event)
	{
	}

	@Override
	public void onEventEntityDamage(EntityDamageEvent event)
	{
	}

	@Override
	public void onEventEntityDamagedByEntity(EntityDamageByEntityEvent event)
	{
	}

	@Override
	public void onEventEntityShootBowEvent(EntityShootBowEvent event)
	{
	}

	@Override
	public void onEventPlayerRespawn(PlayerRespawnEvent event)
	{
	}

	@Override
	public void onEventPlayerDropItem(PlayerDropItemEvent event)
	{
	}

	@Override
	public void onEventPlayerPickupItem(PlayerPickupItemEvent event)
	{
	}

	@Override
	public void onEventPlayerItemConsume(PlayerItemConsumeEvent event)
	{
	}

	@Override
	public void onEventPlayerItemHeld(PlayerItemHeldEvent event)
	{
	}

	@Override
	public void onEventPlayerExpChange(PlayerExpChangeEvent event)
	{
	}

	@Override
	public void onEventPlayerMove(PlayerMoveEvent event)
	{
	}

	@Override
	public void onEventPlayerToggleSneak(PlayerToggleSneakEvent event)
	{
	}

	@Override
	public void onEventPlayerToggleSprint(PlayerToggleSprintEvent event)
	{
	}

	@Override
	public void oneventPlayerToggleFlight(PlayerToggleFlightEvent event)
	{
	}

	@Override
	public void onEventPlayerInteract(PlayerInteractEvent event)
	{
	}

	@Override
	public void onEventPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
	}

	@Override
	public void onEventBlockPlaceEvent(BlockPlaceEvent event)
	{
	}

	@Override
	public void onEventBlockBreakEvent(BlockBreakEvent event)
	{
	}

	@Override
	public void onListenPlayerDeath(ArenaPlayer player)
	{
	}

	@Override
	public void onListenEntityDeath(Entity entity)
	{
	}

	@Override
	public void onListenPlayerKillPlayer(ArenaPlayer player, ArenaPlayer killed)
	{
	}

	@Override
	public void onListenEntityKillPlayer(Entity entity, ArenaPlayer killed)
	{
	}

	@Override
	public void onListenPlayerKillEntity(ArenaPlayer player, Entity killed)
	{
	}

	@Override
	public void onListenEntityKillEntity(Entity entity, Entity killed)
	{
	}

	@Override
	public void onListenEnviromentKillPlayer(ArenaPlayer player, DamageCause cause)
	{
	}

	@Override
	public void onListenPlayerAttackPlayer(ArenaPlayer player, ArenaPlayer victim)
	{
	}

	@Override
	public void onListenPlayerAttackEntity(ArenaPlayer player, Entity victim)
	{
	}

	@Override
	public void onListenEntityAttackPlayer(Entity entity, ArenaPlayer victum)
	{
	}

	@Override
	public void onListenPlayerArrowHit(ArenaPlayer player, Entity projectile)
	{
	}

	@Override
	public void onListenEntityArrowHit(Entity entity, Entity projectile)
	{
	}

	@Override
	public void onListenPlayerDamaged(ArenaPlayer player, DamageCause cause)
	{
	}

	@Override
	public void onListenEntityDamaged(Entity entity, DamageCause cause)
	{
	}

	@Override
	public void onListenPlayerHitByArrow(ArenaPlayer player, Projectile projectile)
	{
	}

	@Override
	public void onListenEntityHitByArrow(Entity entity, Projectile projectile)
	{
	}

	@Override
	public void onListenPlayerShootBow(ArenaPlayer player, Entity projectile)
	{
	}

	@Override
	public void onListenEntityShootBow(Entity entity, Entity projectile)
	{
	}

	@Override
	public void onListenPlayerDrop(ArenaPlayer player, Item item)
	{
	}

	@Override
	public void onListenPlayerPickup(ArenaPlayer player, Item item)
	{
	}

	@Override
	public void onListenPlayerConsume(ArenaPlayer player, ItemStack item)
	{
	}

	@Override
	public void onListenPlayerItemHeld(ArenaPlayer player, ItemStack item)
	{
	}

	@Override
	public void onListenPlayerExpChange(ArenaPlayer player, int amount)
	{
	}

	@Override
	public void onListenPlayerMove(ArenaPlayer player, Location moveTo,
			Location moveFrom)
	{
	}

	@Override
	public void onListenPlayerSneak(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerUnSneak(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerSprint(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerUnSprint(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerFlight(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerUnFlight(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerJump(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerPortal(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerInteract(ArenaPlayer player, Block block)
	{
	}

	@Override
	public void onListenPlayerInteractEntity(ArenaPlayer player, Entity entity)
	{
	}

	@Override
	public void onListenPlayerBlockBreak(ArenaPlayer player, Block block)
	{
	}

	@Override
	public void onListenPlayerPlaceBlock(ArenaPlayer player, Block block)
	{
	}


}
