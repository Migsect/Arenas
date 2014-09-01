package me.migsect.Arenas.GameTypes;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.migsect.Arenas.Players.ArenaPlayer;

public class ScoreHandler
{
	GameHandler gameHandler;
	
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard mainboard = manager.getNewScoreboard();
	
	// The purpose of the score handler is to store all the scores of the game.
	//   All scores will actually be stored within a scorelist.  This has all the players who will be 
	//   in a hashmap.  To pull a player's score you just have to put the ArenaPlayer in to receive the value.
	
	// Scorehandler will update the scoreboard whenever a score is changed.  As such it will act as the interface
	//   for the game and the server.
	
	// Players will actually have their own custom scoreboards, these will be able to show a wide variety of information.
	//   
	public ScoreHandler(GameHandler gameHandler)
	{
		this.gameHandler = gameHandler;
	}
	
	public GameHandler getGameHandler()
	{
		return gameHandler;
	}
	public Scoreboard getMainScoreboard()
	{
		return mainboard;
	}
	public ScoreboardManager getScoreboardManager()
	{
		return manager;
	}
	
	public void showMain(ArenaPlayer player)
	{
		player.getPlayer().setScoreboard(mainboard);
	}
	public void showMain(List<ArenaPlayer> players)
	{
		for(int i = 0; i < players.size(); i++)
		{
		}
	}
	public void hideScoreboard(ArenaPlayer player)
	{
		player.getPlayer().setScoreboard(manager.getNewScoreboard());
	}
	public void hideScoreboard(List<ArenaPlayer> players)
	{
		
	}
}
