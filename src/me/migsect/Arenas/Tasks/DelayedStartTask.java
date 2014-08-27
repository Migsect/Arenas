package me.migsect.Arenas.Tasks;

import me.migsect.Arenas.GameTypes.ArenaGame;

import org.bukkit.scheduler.BukkitRunnable;

public class DelayedStartTask extends BukkitRunnable
{
	
	
	ArenaGame game;
	
	public DelayedStartTask(ArenaGame game)
	{
		this.game = game;
	}
	
	@Override
	public void run()
	{
		game.gameStart();
	}
	

}
