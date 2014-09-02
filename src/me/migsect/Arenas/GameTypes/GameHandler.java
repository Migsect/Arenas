package me.migsect.Arenas.GameTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import me.migsect.Arenas.ArenaHelper;
import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;
import me.migsect.Arenas.Players.StateGM;
import me.migsect.Arenas.Players.StateGhost;
import me.migsect.Arenas.Players.StateHandler;
import me.migsect.Arenas.Players.StateLobby;
import me.migsect.Arenas.Players.StatePlaying;
import me.migsect.Arenas.Players.StateSpectator;
import me.migsect.Arenas.Tasks.TaskHandler;
import me.migsect.Arenas.maps.MapHandler;
import me.migsect.Arenas.maps.SpawnHandler;

public class GameHandler
{
	// The GameHandler stores all class instances of the games and will act as the overall game executor.
	
	private Arenas plugin;
	
	private MapHandler mapHandler;
	private TaskHandler taskHandler;
	private SpawnHandler spawnHandler;
	private ScoreHandler scoreHandler;
	private StateHandler stateHandler;
	
	private HashMap<String,ArenaGame> gameTypes = new HashMap<String,ArenaGame>();;
	private HashMap<String,ArenaPlayer> gamePlayers = new HashMap<String,ArenaPlayer>();;
	private ArenaGame loadedGame = null;
	
	private Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	
	
	
	// Constructor
	public GameHandler(Arenas instance)
	{
		plugin = instance;
		
		mapHandler= new MapHandler(plugin, this);
		taskHandler = new TaskHandler(plugin);
		spawnHandler = new SpawnHandler(this);
		scoreHandler = new ScoreHandler(this);
		stateHandler = new StateHandler(this);
		
		registerStates();
	}
	
	
	// ACTIONS: These include all that goes beyond getters and setters.. These will
	//   usually start with a verb aside from set, get, is, contains, etc.
	
	// Hidden Player Handler
	//   Because hidden players are somewhat annoying, this will handle all hiding
	//   And unhiding of players.
	//   Because we have a player list, we can easily update whenever a new player
	//   is registered.
	public void updateHidden()
	{
	}
	public void registerStates()
	{
		stateHandler.registerUniversal(new StateGM(this));
		stateHandler.registerUniversal(new StateGhost(this));
		stateHandler.registerUniversal(new StateLobby(this));
		stateHandler.registerUniversal(new StatePlaying(this));
		stateHandler.registerUniversal(new StateSpectator(this));
	}
	// registerPlayer() will add the player to the player list.  
	public ArenaPlayer registerPlayer(Player player)
	{
		String name = player.getName();
		ArenaPlayer gamePlayer = new ArenaPlayer(player, this);
		gamePlayers.put(name, gamePlayer);
		gamePlayer.setState(stateHandler.getUniversal("lobb"));
		ArenaHelper.emptyPlayer(gamePlayer);
		
		updateHidden();
		
		if(plugin.gameHandler.getMapHandler().isMapLoaded()) player.teleport(plugin.gameHandler.getMapHandler().getLoadedMap().getLobby());
		else player.teleport(plugin.gameHandler.getMapHandler().getMainLobby());
		
		return gamePlayer;
	}
	// Deregister player will remove the player and if a game is running will also remove the player.
	public void deregisterPlayer(ArenaPlayer player)
	{
		gamePlayers.remove(player);
		player.getPlayer().sendMessage("You have been deregistered");
		ArenaHelper.emptyPlayer(player);
		player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		updateHidden();
		
		if(plugin.gameHandler.getMapHandler().isMapLoaded()) player.getPlayer().teleport(plugin.gameHandler.getMapHandler().getLoadedMap().getLobby());
		else player.getPlayer().teleport(plugin.gameHandler.getMapHandler().getMainLobby());
		
	}
	public void deregisterPlayer(Player player)
	{
		ArenaPlayer gamePlayer = this.getPlayer(player);
		this.deregisterPlayer(gamePlayer);
	}
	public void registerGame(ArenaGame game)
	{
		gameTypes.put(game.getTag(), game);
	}
	public void registerAll()
	{
		Player[] players = plugin.getServer().getOnlinePlayers();
		for(Player player : players)
		{
			this.registerPlayer(player);
		}
		plugin.logger.info("The world has been registered");
	}
	public void deregisterAll()
	{
		for(int i = 0; i < this.getPlayers().size(); i++)
		{
			this.deregisterPlayer(this.getPlayers().get(i));
		}
		plugin.logger.info("The world has been deregistered");
	}
	
	// Return Lists: These will return a set of lists for players for games to manipulate.
	
	//   GetPlayers will get all the players that are registered (which should be all the players)
	public List<ArenaPlayer> getPlayers()
	{
		List<ArenaPlayer> list = new ArrayList<ArenaPlayer>();
		list.addAll(gamePlayers.values());
		return list;
	}
	//   GetSpectators will return all players who have opted to be a spectator.  Specators are defined by a special state.
	public List<ArenaPlayer> getSpectators()
	{
		List<ArenaPlayer> list = new ArrayList<ArenaPlayer>();
		for(int i = 0; i < this.getPlayers().size(); i++)
		{
			if(this.getPlayers().get(i).getState() instanceof StateSpectator){list.add(gamePlayers.get(i));}
		}
		return list;
	}
	//   GetActivePlayers will return all players who want to play the game (this discludes gamemasters and specators)
	public List<ArenaPlayer> getActivePlayers()
	{
		List<ArenaPlayer> list = new ArrayList<ArenaPlayer>();
		list.addAll(gamePlayers.values());
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).getState() instanceof StateSpectator){list.remove(list.get(i));}
			if(list.get(i).getState() instanceof StateGM){list.remove(list.get(i));}
		}
		return list;
	}
	//   GetSpectators will return all players who have opted to be a spectator.  Specators are defined by a special state.
	public List<ArenaPlayer> getGameMasters()
	{
		List<ArenaPlayer> list = new ArrayList<ArenaPlayer>();
		for(int i = 0; i < this.getPlayers().size(); i++)
		{
			if(this.getPlayers().get(i).getState() instanceof StateGM){list.add(gamePlayers.get(i));}
		}
		return list;
	}
	public void messagePlayers(String message, List<ArenaPlayer> players)
	{
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).getPlayer().sendMessage(message);
		}
	}
	public void messageAllPlayers(String message)
	{
		List<ArenaPlayer> list = new ArrayList<ArenaPlayer>(gamePlayers.values());
		for(int i = 0; i < list.size(); i ++)
		{
			list.get(i).getPlayer().sendMessage(message);
		}
	}
	
	
	// Load Game will load the game into the loadedGame slot and unload it when
	//   needed.  Loading a game will 
	public void loadGame(ArenaGame game)
	{
		loadedGame = game;
		loadedGame.gameInitiate();
		for(int i = 0; i < this.getPlayers().size(); i++)
		{
			this.getPlayers().get(i).getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		
	}
	public void unloadGame()
	{
		loadedGame.gameTerminate();
		loadedGame = null;
		for(int i = 0; i < this.getPlayers().size(); i++)
		{
			this.getPlayers().get(i).getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}
	
	// Game Commands, will start, stop, reset, etc games.
	public void startGame()
	{
		loadedGame.gameStart();
		taskHandler.startGameClock(loadedGame);
	}
	public void endGame()
	{
		loadedGame.gameEnd();
		taskHandler.stopGameClock();
	}
	public void resetGame()
	{
		loadedGame.gameReset();
	}
	
	// CONDITIONALS:  here is all the conditionals that can be taken from the game
	//   by code standard all "Is" statements will be bool returns.  Furthermore anything
	//   that will be in the form of a question will be a bool return.
	public boolean isGameLoaded()
	{
		if(loadedGame == null) return false;
		return true;
	}
	public boolean containsPlayer(Player player)
	{
		String name = player.getName();
		if(gamePlayers.containsKey(name)) return true;
		return false;
	}
	public boolean containsPlayer(String name)
	{
		if(gamePlayers.containsKey(name)) return true;
		return false;
	}
	public boolean gameExists(String key)
	{
		return gameTypes.containsKey(key);
	}
	
	// GETTERS: These return information and objects.
	public Scoreboard getScoreboard()
	{
		return board;
	}
	public List<String> getGameNames()
	{
		List<String> list = new ArrayList<String>();
		list.addAll(gameTypes.keySet());
		return list;
	}
	public ArenaGame getLoadedGame()
	{
		return loadedGame;
	}
	public ArenaGame getGame(String key)
	{
		return gameTypes.get(key);
	}
	public List<ArenaGame> getGameTypes()
	{
		List<ArenaGame> list = new ArrayList<ArenaGame>();
		list.addAll(gameTypes.values());
		return list;
	}
	public int getPlayerCount()
	{
		List<ArenaPlayer> players = this.getPlayers();
		return players.size();
	}
	
	// Player getters (For converting player>>arenaplayer
	public ArenaPlayer getPlayer(Player player)
	{
		String name = player.getName();
		return gamePlayers.get(name);
	}
	public ArenaPlayer getPlayer(String name)
	{
		return gamePlayers.get(name);
	}
	
	// Handler getters
	public TaskHandler getTaskHandler()
	{
		return taskHandler;
	}
	public MapHandler getMapHandler()
	{
		return mapHandler;
	}
	public SpawnHandler getSpawnHandler()
	{
		return spawnHandler;
	}
	public ScoreHandler getScoreHandler()
	{
		return scoreHandler;
	}
	public StateHandler getStateHandler()
	{
		return stateHandler;
	}
	
	// SETTERS: These will set information and objects to the gameHandler.
	//   These cannot do anything in terms of actions
	
	
	
}
