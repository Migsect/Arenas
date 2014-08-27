package me.migsect.Arenas.Tasks;

import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.scheduler.BukkitRunnable;

public class RespawnTask extends BukkitRunnable
{
	ArenaPlayer player;
	ArenaGame game;
	
	public RespawnTask(ArenaGame game, ArenaPlayer player)
	{
		this.player = player;
		this.game = game;
	}
	@Override
	public void run()
	{
		game.respawnPlayer(player);
	}

}
