package me.migsect.Arenas.GameTypes;

import java.util.HashMap;

import me.migsect.Arenas.Players.ArenaPlayer;

public class ArenaScorelist
{
	String name = "default";
	HashMap<ArenaPlayer, Integer> playerScores = new HashMap<ArenaPlayer, Integer>();
	
	public ArenaScorelist(String name){
		this.name = name;
	}
	
	public int getScore(ArenaPlayer player)
	{
		return playerScores.get(player);
	}
	public void setScore(ArenaPlayer player, int newScore)
	{
		playerScores.put(player, newScore);
	}
	public void addScore(ArenaPlayer player, int addScore)
	{
		if(!playerScores.containsKey(player)) playerScores.put(player, 0);
		playerScores.put(player,playerScores.get(player) + addScore);
	}
}
