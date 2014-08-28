package me.migsect.Arenas.GameTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

public class ArenaScoreboard
{
	GameHandler handler;
	Scoreboard board;
	HashMap<String, ArenaScorelist> scores;
	List<ArenaPlayer> hiddenPlayers = new ArrayList<ArenaPlayer>();
	
	public ArenaScoreboard(GameHandler handler)
	{
		this.handler = handler;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		scores = new HashMap<String, ArenaScorelist>();
	}
	
	public void newScoreList(String name, boolean include)
	{
		ArenaScorelist scorelist = new ArenaScorelist(name);
		if(include = true)
		{
			List<ArenaPlayer> players = handler.getActivePlayers();
			for(int i = 0; i < players.size(); i ++)
			{
				scorelist.setScore(players.get(i), 0);
			}
		}
	}
	
	public void deleteScoreList(String string)
	{
		scores.remove(string);
	}
	
	public void setScore(String score, ArenaPlayer player, int newScore)
	{
		
	}
	public void addScore(String score, ArenaPlayer player, int addScore)
	{
		
	}
	public int getScore(String score, ArenaPlayer player)
	{
		return scores.get(score).getScore(player);
	}
	public void includePlayer(ArenaPlayer player)
	{
		
	}
	public void discludePlayer(ArenaPlayer player)
	{
		
	}
}
