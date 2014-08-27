package me.migsect.Arenas.Tasks;

import me.migsect.Arenas.GameTypes.ArenaGame;

import org.bukkit.scheduler.BukkitRunnable;

public class ClockTask extends BukkitRunnable
{
	private ArenaGame game;
	private int gameTick = 0;
	public ClockTask(ArenaGame game)
	{
		this.game = game;
	}
	
	public int getGameTick()
	{
		return gameTick;
	}
	
	@Override
	public void run()
	{
		game.onTick(gameTick);
		gameTick++;
	}
}
