package me.migsect.Arenas.Tasks;

import java.util.ArrayList;
import java.util.List;

import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.scheduler.BukkitRunnable;

public class TagTask extends BukkitRunnable
{
	
	String tag = "";
	List<ArenaPlayer> players = new ArrayList<ArenaPlayer>();
	ArenaGame game;
	
	public TagTask(ArenaGame game, String tag)
	{
		this.game = game;
		this.tag = tag;
	}
	public TagTask(ArenaGame game, String tag, List<ArenaPlayer> players)
	{
		this.game = game;
		this.tag = tag;
		this.players.addAll(players);
	}

	
	public boolean isTag(String tag)
	{
		return this.tag.equalsIgnoreCase(tag);
	}
	
	@Override
	public void run()
	{
		if(!players.isEmpty())
		{
			game.onTagTask(tag, players);
		}
		else
		{
			game.onTagTask(tag);
		}
	}
}
