package me.migsect.Arenas.GameTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import me.migsect.Arenas.Players.ArenaPlayer;

public class ArenaScoreList
{
	private ArenaScoreboard board;
	private Objective objective;
	private String name = "default";
	private String displayName = "default";
	private HashMap<ArenaPlayer, Integer> playerScores = new HashMap<ArenaPlayer, Integer>(); // TODO: Make it so this isn't a memory leak.
	
	public ArenaScoreList(ArenaScoreboard board, String name){
		this.name = name;
		this.displayName = name;
		this.board = board;
		objective = board.getVanillaScoreboard().registerNewObjective(name, "dummy");
		objective.setDisplayName(displayName);
	}
	
	public void setDisplayName(String newName)
	{
		displayName = newName;
	}
	public void setDisplaySlot(DisplaySlot slot)
	{
		objective.setDisplaySlot(slot);
	}
	public String getName()
	{
		return name;
	}
	public String getDisplayName()
	{
		return displayName;
	}
	public Objective getVanillaObjective()
	{
		return objective;
	}
	public ArenaScoreboard getScoreboard()
	{
		return board;
	}
	
	public int getScore(ArenaPlayer player)
	{
		return playerScores.get(player);
	}
	public void setScore(ArenaPlayer player, int newScore)
	{
		playerScores.put(player, newScore);
		updateVanilla();
	}
	public void addScore(ArenaPlayer player, int addScore)
	{
		if(!playerScores.containsKey(player)) playerScores.put(player, 0);
		playerScores.put(player,playerScores.get(player) + addScore);
		updateVanilla();
	}
	@SuppressWarnings("deprecation")
	private void updateVanilla()
	{
		List<ArenaPlayer> players = new ArrayList<ArenaPlayer>(playerScores.keySet());
		for(int i = 0; i < players.size(); i++)
		{
			objective.getScore((OfflinePlayer) players.get(i).getPlayer()).setScore(playerScores.get(players.get(i)));
			if(!board.isViewable(players.get(i))) board.getVanillaScoreboard().resetScores((OfflinePlayer) players.get(i).getPlayer());
		}
	}
}
