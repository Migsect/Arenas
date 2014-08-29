package me.migsect.Arenas.GameTypes;

import java.util.ArrayList;
import java.util.List;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;
import me.migsect.Arenas.Tasks.CountdownTask;
import me.migsect.Arenas.Tasks.TaskHandler;
import me.migsect.Arenas.maps.MapHandler;
import me.migsect.Arenas.maps.SpawnHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

public abstract class ArenaGame
{
	protected Arenas plugin;
	protected GameHandler gameHandler;
	protected SpawnHandler spawnHandler;
	protected MapHandler mapHandler;
	protected TaskHandler taskhandler;
	
	protected ArenaGamePhase phase = null;
	protected boolean isRunning = false;
	protected String gameName = "Default";
	protected String gameTag = "Dflt";
	protected int maxPlayers = 1;
	protected int minPlayers = 1;
	protected List<ArenaTeam> teams;
	
  //combat allowances
	boolean canDamage = true;
	boolean canBeDamaged = true;
	boolean canCollide = true;
	boolean canEffectSpawn = true;
	
	// item allowances
	boolean canConsume = true;
	boolean canPickup = true;
	boolean canDrop = true;
	boolean canDeathDrop = true;
	boolean canKeepItems = false;
	boolean canMoveArmor = true;
	
	// movement allowances
	
	boolean canMove = true;
	boolean canFly = true;
	boolean canSprint = true;
	boolean canJump = true;
	boolean canSneak = true;
	
	
	public ArenaGame(Arenas plugin)
	{
		this.plugin = plugin;
		this.teams = new ArrayList<ArenaTeam>();
		this.gameHandler = plugin.gameHandler;
		this.mapHandler = gameHandler.getMapHandler();
		this.spawnHandler = gameHandler.getSpawnHandler();
		this.taskhandler = gameHandler.getTaskHandler();
	}
	
	final protected void setGameName(String gameName){this.gameName = gameName;}
	final protected void setGameTag(String gameTag){this.gameTag = gameTag;}
	final public void setPhase(ArenaGamePhase phase){this.phase = phase;}
	final protected void setMaxPlayers(int maxPlayers){this.maxPlayers = maxPlayers;}
	final protected void MinPlayers(int minPlayers){this.minPlayers = maxPlayers;}
	
	
	final public String getGameName(){return gameName;}
	final public String getTag(){return gameTag;}
	final public int getGameMaxPlayers(){return maxPlayers;}
	final public int getGameMinPlayers(){return minPlayers;}
	final public List<ArenaTeam> getTeams(){return teams;}
	final public GameHandler getHandler(){return gameHandler;}
	
	// item allowances get
	final public boolean canKeepItems(){return canKeepItems;}
	final public boolean canConsume(){return canConsume;}
	final public boolean canPickup(){return canPickup;}
	final public boolean canDrop(){return canDrop;}
	final public boolean canDeathDrop(){return canDeathDrop;}
	final public boolean canMoveArmor(){return canMoveArmor;}
	
	// combat allowances get
	final public boolean canDamage(){return canDamage;}
	final public boolean canBeDamaged(){return canBeDamaged;}
	final public boolean canCollide(){return canCollide;}
	final public boolean canEffectSpawn(){return canEffectSpawn;}
	
	// movement allowances get
	final public boolean canMove(){return canMove;}
	final public boolean canFly(){return canFly;}
	final public boolean canSprint(){return canSprint;}
	final public boolean canSneak(){return canSneak;}
	
	
	abstract public void gameInitiate(); // This is called when a game is first loaded.
	abstract public void gameStart(); // This is called when the game will actually be started.
	
	abstract public void gameTerminate(); // This is called when a game is unloaded.
	abstract public void gameEnd(); // This is called when a game is actually ended but not when it is unloaded.
	
	abstract public void gameReset(); // This will end the game and then restart it.
	
	final public void gameStartDelayed(int seconds)
	{
		gameHandler.getTaskHandler().delayedStart(this, seconds);
		
		CountdownTask task = new CountdownTask(seconds, "startCountdown");
		task.setRecievers(gameHandler.getPlayers());
		for(int i = 1; (i < seconds) && (i <= 10); i ++)
		{
			task.addMessage(i, ChatColor.YELLOW + "Starting in...   " + i);
		}
		if(seconds >= 15)
		{
			for(int i = 15; (i < seconds) && ((i % 5) == 0); i++)
			{
				task.addMessage(i, ChatColor.YELLOW + "The Game will start in " + i + " seconds.");
			}
		}
		task.addMessage(seconds, ChatColor.YELLOW + "The Game will start in " + seconds + " seconds");
		
		gameHandler.getTaskHandler().startCountdown(task);
	}
	
	final public boolean isRunning(){return isRunning;}
	final public ArenaGamePhase getGamePhase(){return phase;}
	
	abstract public void addPlayer(ArenaPlayer player);
	abstract public void removePlayer(ArenaPlayer player);
	
	abstract public void respawnPlayer(ArenaPlayer player);
	abstract public void onTick(int gameTick); // deals with the clock and having things change.
	abstract public void onTagTask(String tag); // this is meant to be a means to perform a future task without
																							// having to make a taks for everything.
	abstract public void onTagTask(String tag, List<ArenaPlayer> players);
	
	final public void teleportPlayers(Location loc)
	{
		for(int i = 0; i < gameHandler.getPlayerCount(); i++)
		{
			gameHandler.getPlayers().get(i).getPlayer().teleport(loc);
		}
	}
	final public void teleportTeam(ArenaTeam team, Location loc)
	{
		for(int i = 0; i < team.getTeamPlayers().size(); i++)
		{
			team.getTeamPlayers().get(i).getPlayer().teleport(loc);
		}
	}
	
	//  Each of these methods will either be extreme vague or extremely specific.  The latter
	//  are included for ease of handling events in a PvP scenario.  The former is for more flexibility.
	//  If it turns out that the former is not ever really used, then the latter will be kept and the former
	//  discarded.
	
	//	These methods will be between two types: onEvent<>() and onListen<>().
	//	  Listeners will be implemented in such a way that gameTypes have the ability to
	//    cancel events.  onEvents will only be able to have any effect in this matter.
	//    as such onListen will not have any pointers to the event and are instead abstracted
	//    away as to make them a more attractive source for simple uses.
	
	abstract public void onEventEntityDeath(EntityDeathEvent event); //
	abstract public void onEventPlayerDeath(PlayerDeathEvent event); //
	abstract public void onEventEntityDamage(EntityDamageEvent event); //
	abstract public void onEventEntityDamagedByEntity(EntityDamageByEntityEvent event); //
	abstract public void onEventEntityShootBowEvent(EntityShootBowEvent event); //
	
	abstract public void onEventPlayerRespawn(PlayerRespawnEvent event); //
	
	abstract public void onEventPlayerDropItem(PlayerDropItemEvent event); //
	abstract public void onEventPlayerPickupItem(PlayerPickupItemEvent event); // 
	abstract public void onEventPlayerItemConsume(PlayerItemConsumeEvent event); //
	abstract public void onEventPlayerItemHeld(PlayerItemHeldEvent event); //
	abstract public void onEventPlayerExpChange(PlayerExpChangeEvent event); //
	
	abstract public void onEventPlayerMove(PlayerMoveEvent event); //
	abstract public void onEventPlayerToggleSneak(PlayerToggleSneakEvent event); //
	abstract public void onEventPlayerToggleSprint(PlayerToggleSprintEvent event); //
	abstract public void oneventPlayerToggleFlight(PlayerToggleFlightEvent event); //
	
	abstract public void onEventPlayerInteract(PlayerInteractEvent event); //
	abstract public void onEventPlayerInteractEntity(PlayerInteractEntityEvent event); //
	abstract public void onEventBlockPlaceEvent(BlockPlaceEvent event); //
	abstract public void onEventBlockBreakEvent(BlockBreakEvent event); //
	
	
	
	abstract public void onListenPlayerDeath(ArenaPlayer player); //
	abstract public void onListenEntityDeath(Entity entity); //
	
	abstract public void onListenPlayerKillPlayer(ArenaPlayer player, ArenaPlayer killed); //
	abstract public void onListenEntityKillPlayer(Entity entity, ArenaPlayer killed); //
	abstract public void onListenPlayerKillEntity(ArenaPlayer player, Entity killed); //
	abstract public void onListenEntityKillEntity(Entity entity, Entity killed); //
	abstract public void onListenEnviromentKillPlayer(ArenaPlayer player, DamageCause cause); //
	
	abstract public void onListenPlayerAttackPlayer(ArenaPlayer player, ArenaPlayer victim); //
	abstract public void onListenPlayerAttackEntity(ArenaPlayer player, Entity victim); //
	abstract public void onListenEntityAttackPlayer(Entity entity, ArenaPlayer victum); //
	abstract public void onListenPlayerArrowHit(ArenaPlayer player, Entity projectile); //
	abstract public void onListenEntityArrowHit(Entity entity, Entity projectile); //
	
	abstract public void onListenPlayerDamaged(ArenaPlayer player, DamageCause cause); //
	abstract public void onListenEntityDamaged(Entity entity, DamageCause cause); //
	abstract public void onListenPlayerHitByArrow(ArenaPlayer player, Projectile projectile); //
	abstract public void onListenEntityHitByArrow(Entity entity, Projectile projectile); //
	
	abstract public void onListenPlayerShootBow(ArenaPlayer player, Entity projectile); //
	abstract public void onListenEntityShootBow(Entity entity, Entity projectile);
	 //
	abstract public void onListenPlayerDrop(ArenaPlayer player, Item item); //
	abstract public void onListenPlayerPickup(ArenaPlayer player, Item item); //
	abstract public void onListenPlayerConsume(ArenaPlayer player, ItemStack item); //
	abstract public void onListenPlayerItemHeld(ArenaPlayer player, ItemStack item); //
	abstract public void onListenPlayerExpChange(ArenaPlayer player, int amount); //
	
	abstract public void onListenPlayerMove(ArenaPlayer player, Location moveTo, Location moveFrom); //
	abstract public void onListenPlayerSneak(ArenaPlayer player); //
	abstract public void onListenPlayerUnSneak(ArenaPlayer player); //
	abstract public void onListenPlayerSprint(ArenaPlayer player); //
	abstract public void onListenPlayerUnSprint(ArenaPlayer player); //
	abstract public void onListenPlayerFlight(ArenaPlayer player); //
	abstract public void onListenPlayerUnFlight(ArenaPlayer player); //
	abstract public void onListenPlayerJump(ArenaPlayer player); // TODO: Figure this shit out
	abstract public void onListenPlayerPortal(ArenaPlayer player);
	
	abstract public void onListenPlayerInteract(ArenaPlayer player, Block block); //
	abstract public void onListenPlayerInteractEntity(ArenaPlayer player, Entity entity); //
	abstract public void onListenPlayerBlockBreak(ArenaPlayer player, Block block); //
	abstract public void onListenPlayerPlaceBlock(ArenaPlayer player, Block block); //
	
}
