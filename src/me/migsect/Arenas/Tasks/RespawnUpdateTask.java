package me.migsect.Arenas.Tasks;

import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.scheduler.BukkitRunnable;

public class RespawnUpdateTask extends BukkitRunnable
{
	ArenaPlayer player;
	
	public RespawnUpdateTask(ArenaPlayer player)
	{
		this.player = player;
	}
	
	@Override
	public void run()
	{
		if(player.hasTeam()) player.getTeam().grantEffects(player);
		if(player.hasState()) player.getState().applyEffects(player);
	}
	
	

}
