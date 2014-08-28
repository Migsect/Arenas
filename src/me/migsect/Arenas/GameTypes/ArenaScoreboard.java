package me.migsect.Arenas.GameTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

public class ArenaScoreboard
{
	private GameHandler handler;
	private Scoreboard board;
	private HashMap<String, ArenaScoreList> scores;
	private List<ArenaPlayer> hiddenPlayers = new ArrayList<ArenaPlayer>(); // TODO: Make it so this isn't a memory leak.
	
	public ArenaScoreboard(GameHandler handler)
	{
		this.handler = handler;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		scores = new HashMap<String, ArenaScoreList>();
	}
	
	public void newScoreList(String name, boolean include)
	{
		ArenaScoreList scorelist = new ArenaScoreList(this,name);
		if(include = true)
		{
			List<ArenaPlayer> players = handler.getActivePlayers();
			for(int i = 0; i < players.size(); i ++)
			{
				scorelist.setScore(players.get(i), 0);
			}
		}
	}
	
	public Scoreboard getVanillaScoreboard()
	{
		return board;
	}
	
	public void deleteScoreList(String string)
	{
		scores.remove(string);
	}
	public int getScoreList(String score, ArenaPlayer player)
	{
		return scores.get(score).getScore(player);
	}
	public boolean isViewable(ArenaPlayer player)
	{
		return !hiddenPlayers.contains(player);
	}
	public void includePlayer(ArenaPlayer player)
	{
		if(hiddenPlayers.contains(player)) hiddenPlayers.remove(player);
	}
	public void discludePlayer(ArenaPlayer player)
	{
		hiddenPlayers.add(player);
	}

	public List<ArenaPlayer> getHiddenPlayers()
	{
		return hiddenPlayers;
	}

	public void setHiddenPlayers(List<ArenaPlayer> hiddenPlayers)
	{
		this.hiddenPlayers = hiddenPlayers;
	}
}
