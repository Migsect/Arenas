package me.migsect.Arenas.GameTypes.Golemnaut;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import org.bukkit.scoreboard.DisplaySlot;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.GameTypes.ArenaScoreboard;
import me.migsect.Arenas.Players.ArenaPlayer;
import me.migsect.Arenas.Tasks.CountdownTask;
import me.migsect.Arenas.Tasks.RespawnUpdateTask;

public class GameGolemnaut extends ArenaGame
{
	TeamGolem golemnaut;
	TeamVillagers villagers;
	
	ArenaScoreboard scoreboard = new ArenaScoreboard(gameHandler);
	
	public GameGolemnaut(Arenas plugin)
	{
		super(plugin);
		gameName = "Golemnaut";
		gameTag = "gole";
		maxPlayers = 21;
		minPlayers = 2;
		
		golemnaut = new TeamGolem(plugin, this, "gole");
		teams.add(golemnaut);
		villagers = new TeamVillagers(plugin, this, "vill");
		teams.add(villagers);
	}
	// For selecting the player who killed a golem.
	private void switchGolem(ArenaPlayer player)
	{
		List<ArenaPlayer> oldGolems = new ArrayList<ArenaPlayer>(golemnaut.getPlayers());
		golemnaut.removeAllPlayers();
		if(!oldGolems.isEmpty()) 
		{
			villagers.addPlayers(oldGolems);
			for(int i = 0; i < oldGolems.size(); i++)
			{
				villagers.setLoadout(oldGolems.get(i));
			}
		}
		player.getTeam().removePlayer(player);
		golemnaut.addPlayer(player);
		golemnaut.setLoadout(player);
		player.getLoadout().equipPlayer(player);
		gameHandler.messageAllPlayers(ArenaHelper.colorEncoding(player.getPlayer().getDisplayName() + "&e is now the Golemnaut!"));
	
	}
	// For selecting a random player.  This is on the case of a accidental golem death.
	private void randomGolem()
	{
		List<ArenaPlayer> players = villagers.getPlayers();
		Random rand = new Random();
		switchGolem(players.get(rand.nextInt(players.size()-1)));
	}
	private void randomGolem(List<ArenaPlayer> doNotChoose)
	{
		List<ArenaPlayer> players = villagers.getPlayers();
		for(int i = 0; i < players.size(); i++)
		{
			if(doNotChoose.contains(players.get(i))) players.remove(i);
		}
		Random rand = new Random();
		switchGolem(players.get(rand.nextInt(players.size()-1)));
	}

	@Override
	public void gameInitiate()
	{
		
	}

	@Override
	public void gameStart()
	{
		if(gameHandler.getActivePlayers().size() < 2)
		{
			gameHandler.messageAllPlayers(ArenaHelper.colorEncoding("&4The game cannot be started because there aren't enough players."));
			return;
		}
		this.isRunning = true;
		gameHandler.messageAllPlayers("Game has been started");
		villagers.addPlayers(this.gameHandler.getActivePlayers());
		for(int i = 0; i < this.gameHandler.getActivePlayers().size(); i++)
		{
			villagers.setLoadout(this.gameHandler.getActivePlayers().get(i));
			this.respawnPlayer(this.gameHandler.getActivePlayers().get(i));
		}
		randomGolem();
		
		// Setup Scores
		scoreboard.newScoreList("score", true);
		scoreboard.getScoreList("score").setDisplayName("Score");
		scoreboard.getScoreList("score").setDisplaySlot(DisplaySlot.SIDEBAR);
		scoreboard.showPlayers(gameHandler.getActivePlayers());
		
		// Timed game
		CountdownTask countdownTask = new CountdownTask(300, "gameend");
		countdownTask.setRecievers(gameHandler.getPlayers());
		countdownTask.addMessage(300, ArenaHelper.colorEncoding("&e<<< <<< 5 Minutes Remain >>> >>>"));
		countdownTask.addMessage(240, ArenaHelper.colorEncoding("&e<<< <<< 4 Minutes Remain >>> >>>"));
		countdownTask.addMessage(180, ArenaHelper.colorEncoding("&e<<< <<< 3 Minutes Remain >>> >>>"));
		countdownTask.addMessage(120, ArenaHelper.colorEncoding("&e<<< <<< 2 Minutes Remain >>> >>>"));
		countdownTask.addMessage(60, ArenaHelper.colorEncoding("&e<<< <<< 1 Minute Remains >>> >>>"));

		countdownTask.addMessage(45, ArenaHelper.colorEncoding("&e<<< <<< 45 Seconds Remain >>> >>>"));
		countdownTask.addMessage(30, ArenaHelper.colorEncoding("&e<<< <<< 30 Seconds Remain >>> >>>"));
		countdownTask.addMessage(15, ArenaHelper.colorEncoding("&e<<< <<< 15 Seconds Remain >>> >>>"));
		countdownTask.addMessage(10, ArenaHelper.colorEncoding("&e10"));
		countdownTask.addMessage(9, ArenaHelper.colorEncoding("&e9"));
		countdownTask.addMessage(8, ArenaHelper.colorEncoding("&e8"));
		countdownTask.addMessage(7, ArenaHelper.colorEncoding("&e7"));
		countdownTask.addMessage(6, ArenaHelper.colorEncoding("&e6"));
		countdownTask.addMessage(5, ArenaHelper.colorEncoding("&e5"));
		countdownTask.addMessage(4, ArenaHelper.colorEncoding("&e4"));
		countdownTask.addMessage(3, ArenaHelper.colorEncoding("&e3"));
		countdownTask.addMessage(2, ArenaHelper.colorEncoding("&e2"));
		countdownTask.addMessage(1, ArenaHelper.colorEncoding("&e1"));

		taskhandler.startCountdown(countdownTask);
		taskhandler.startTagTask(this, "endgame", 6000);
		
		
	}

	@Override
	public void gameTerminate()
	{
	}

	@Override
	public void gameEnd()
	{
		this.isRunning = false;
		villagers.removeAllPlayers();
		golemnaut.removeAllPlayers();
		for(int i = 0; i < gameHandler.getActivePlayers().size();i++)
		{
			gameHandler.getActivePlayers().get(i).setState(gameHandler.getStateHandler().getUniversal("lobb"));
			ArenaHelper.emptyPlayer(gameHandler.getActivePlayers().get(i));
		}
		taskhandler.stopGameClock();
		taskhandler.stopCountdownType("gameend");
		taskhandler.cancelTagTasks("endgame");
		
		gameHandler.messageAllPlayers(ArenaHelper.colorEncoding("&e" + scoreboard.getScoreList("score").getTopPlayer().getPlayer().getDisplayName() + "&f wins with &e" + scoreboard.getScoreList("score").getTopScore() + " points&f!" ));
	}

	@Override
	public void gameReset()
	{
	}

	@Override
	public void onPlayerJoinGame(ArenaPlayer player)
	{
		if(!this.isRunning()) return;
		villagers.addPlayer(player);
		villagers.setLoadout(player);
		this.respawnPlayer(player);
		this.scoreboard.showPlayer(player);
		
	}

	@Override
	public void onPlayerLeaveGame(ArenaPlayer player)
	{
		List<ArenaPlayer> nonGolem = new ArrayList<ArenaPlayer>();
		nonGolem.add(player);
		if(golemnaut.containsPlayer(player)) randomGolem(nonGolem);
	}

	@Override
	public void respawnPlayer(ArenaPlayer player)
	{
		player.setState(gameHandler.getStateHandler().getUniversal("play"));
		player.getPlayer().teleport(spawnHandler.smartSpawn(30));
		if(player.hasLoadout()) player.getLoadout().equipPlayer(player);
		if(player.hasTeam()) player.getTeam().grantEffects(player);
	}

	@Override
	public void onTick(int gameTick)
	{
		// this will point all the villager's compasses towards the golemnaut.
		if(!this.isRunning()) return;
		if(gameTick % 20 == 0)
		{
			if(villagers.getPlayers().size() >= 1)
			{
				for(int i = 0; i < villagers.getPlayers().size(); i++)
				{
					villagers.getPlayers().get(i).getPlayer().setCompassTarget(golemnaut.getPlayers().get(0).getPlayer().getLocation());
				}
			}
		}
		if(gameTick % 100 == 0)
		{
			if(golemnaut.getPlayers().size() >= 1)
			{
				for(int i = 0; i <  golemnaut.getPlayers().size(); i++)
				{
					scoreboard.getScoreList("score").addScore(golemnaut.getPlayers().get(i), 1);
				}
			}
		}
	}

	@Override
	public void onTagTask(String tag)
	{
		if(tag.equals("endgame"))
		{
			gameHandler.endGame();
		}
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

	
	// This will occur when the player physically respawns after they die.
	//   
	@Override
	public void onEventPlayerRespawn(PlayerRespawnEvent event)
	{
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getPlayer());
		event.setRespawnLocation(player.getLastDeathLocation());
		player.setState(gameHandler.getStateHandler().getUniversal("ghos"));
		
		
		RespawnUpdateTask task = new RespawnUpdateTask(player);
		task.runTask(plugin);
		
		taskhandler.startRespawn(this, player, 100);
		
		CountdownTask countdown = new CountdownTask(5, "respawn");
		countdown.addReciever(player);
		countdown.addMessage(5, ArenaHelper.colorEncoding("&eYou will respawn in 5 seconds."));
		countdown.addMessage(3, ArenaHelper.colorEncoding("&e3"));
		countdown.addMessage(2, ArenaHelper.colorEncoding("&e2"));
		countdown.addMessage(1, ArenaHelper.colorEncoding("&e1"));
		taskhandler.startCountdown(countdown);
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
		if(killed.getTeam().equals(golemnaut))
		{
			scoreboard.getScoreList("score").addScore(player, 20);
			switchGolem(player);
			return;
		}
		if(player.getTeam().equals(golemnaut))
		{
			scoreboard.getScoreList("score").addScore(player, 5);
			return;
		}
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
