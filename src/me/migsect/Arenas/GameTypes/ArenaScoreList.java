package me.migsect.Arenas.GameTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import me.migsect.Arenas.Players.ArenaPlayer;

public class ArenaScoreList
{
	
	// ALL PLAYERS will be added, doesn't mean they will have any points.
	//   As such the main scorehandler will need to update this list with all new players.
	//   When a score is first made it will pull all info from the scorehandler in order to compile
	//   the list of players.  Whenever a new player joins the scorehandler will tell the scorelist to
	//   add them.
	// Scorelists do not care who is on their list.  They are just a means of tracking and retrieving information
	//   on the score they are.  It is up to the scorehandler to decide who is actually displayed on the
	//   client scoreboard.
	// Is displayed will determine if a player will be displayed or not. 
	
	// This ScoreList will ONLY be under the mainscoreboard as it will be a general cross-server report.  
	// Team specific Scoreboards will be part of a different setup and will most likely have their own speciailized hander within
	//   a hashmap in the scorehandler.  however we will not be worrying about team scoreboards until later.
	
	// isDisplayer and canSee will determine who is displayed on the scoreboard and who can see the scoreboard.
	ScoreHandler handler;
	
	HashMap<ArenaPlayer, Integer> scores = new HashMap<ArenaPlayer, Integer>();
	List<ArenaPlayer> isDisplayed = new ArrayList<ArenaPlayer>();
	Objective objective;
	String tag = "";
	String heading = "";
	String description = "";
	
	public ArenaScoreList(ScoreHandler handler, String tag)
	{
		this.handler = handler;
		this.tag = tag;
		objective = handler.getMainScoreboard().registerNewObjective(tag, "dummy");
		this.addAllPlayers(true);
	}
	public void setLocation(DisplaySlot slot)
	{
		// This will push any other scores out, so we must be careful here.
		objective.setDisplaySlot(slot);
	}
	public void setHeading(String heading)
	{
		this.heading = heading;
		objective.setDisplayName(heading);
	}
	
	public boolean isTracked(ArenaPlayer player)
	{
		return scores.containsKey(player);
	}
	public boolean isDisplayed(ArenaPlayer player)
	{
		return isDisplayed.contains(player);
	}
	
	private void addAllPlayers(boolean displayed)
	{
		List<ArenaPlayer> players = handler.getGameHandler().getPlayers();
		for(int i = 0; i < players.size(); i++)
		{
			this.addNewPlayer(players.get(i), displayed);
		}
	}
	
	public void addNewPlayer(ArenaPlayer player, boolean displayed)
	{
	scores.put(player,0);
	if(displayed) this.isDisplayed.add(player);
	}
	public void setDisplayed(ArenaPlayer player, boolean displayed)
	{
		if(displayed) isDisplayed.add(player);
		if(!displayed && isDisplayed.contains(player)) isDisplayed.remove(player);
	}
	public void setDisplayed(List<ArenaPlayer> players, boolean displayed)
	{
		for(int i = 0; i < players.size(); i++)
		{
			this.setDisplayed(players.get(i), displayed);
		}
	}
	
	public void addScore(ArenaPlayer player, int amount)
	{
		scores.put(player, scores.get(player) + amount);
	}
	public void setScore(ArenaPlayer player, int amount)
	{
		scores.put(player, amount);
	}
	public int getPlayerScore(ArenaPlayer player)
	{
		if(!scores.containsKey(player)) return 0;
		return scores.get(player);
	}
	
	@SuppressWarnings("deprecation")
	public void updateScoresPlayer(ArenaPlayer player)
	{
		if(isDisplayed.contains(player))
		{
			Score score = objective.getScore(player.getPlayer());
			score.setScore(scores.get(player));
		}
		if(!isDisplayed.contains(player))
		{
			handler.getMainScoreboard().resetScores((OfflinePlayer) player);
		}
	}
	public void updateScores()
	{
		List<ArenaPlayer> players = new ArrayList<ArenaPlayer>(scores.keySet());
		for(int i = 0; i < players.size(); i++)
		{
			if(isDisplayed.contains(players.get(i)))
			{
				@SuppressWarnings("deprecation")
				Score score = objective.getScore(players.get(i).getPlayer());
				score.setScore(scores.get(players.get(i)));
			}
		}
	}
}
