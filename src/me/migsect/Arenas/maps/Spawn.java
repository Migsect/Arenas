package me.migsect.Arenas.maps;

import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.Location;

public class Spawn
{
	private Location loc;
	private int lastSpawnTic = 0;
	
	public Spawn(Location loc)
	{
		this.loc = loc;
	}
	
	public Location getLocation(){return loc;}
	public int getLastTic(){return lastSpawnTic;}
	public void setLastTic(int tic){lastSpawnTic = tic;}
	
	public double getDistanceToPlayer(ArenaPlayer player)
	{
		return loc.distance(player.getPlayer().getLocation());
	}
}
