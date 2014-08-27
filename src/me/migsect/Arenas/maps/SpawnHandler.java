package me.migsect.Arenas.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

import me.migsect.Arenas.GameTypes.ArenaTeam;
import me.migsect.Arenas.GameTypes.GameHandler;
import me.migsect.Arenas.Players.ArenaPlayer;

public class SpawnHandler
{
	GameHandler gameHandler;
	List<Spawn> spawns = new ArrayList<Spawn>(); 
	HashMap<String, List<Spawn>> teamSpawns = new HashMap<String, List<Spawn>>();
	
	public SpawnHandler(GameHandler gameHandler)
	{
		this.gameHandler = gameHandler;
	}
	
	public void addList(List<Location> locations)
	{
		spawns.addAll(this.convertList(locations));
	}
	public void addTeamList(String tag, List<Location> locations)
	{
		if(teamSpawns.containsKey(tag)) teamSpawns.get(tag).addAll(this.convertList(locations));
		else teamSpawns.put(tag, this.convertList(locations));
	}
	public void addTeamList(ArenaTeam team, List<Location> locations)
	{
		addTeamList(team.getTeamTag(), locations);
	}
	
	public void emptySpawns()
	{
		spawns.clear();
		teamSpawns.clear();
	}
	
	private Location getRandSpawn(List<Spawn> listSpawns)
	{
		Random rand = new Random();
		return listSpawns.get(rand.nextInt(listSpawns.size())).getLocation();
	}
	public Location getRandSpawn()
	{
		return this.getRandSpawn(spawns);
	}
	public Location getRandTeamSpawn(String tag)
	{
		return this.getRandSpawn(teamSpawns.get(tag));
	}
	
	public Location smartSpawn(double dist)
	{
		List<Spawn> spawnList = this.getDistanceSpawn(dist);
		while(spawnList.size() == 0)
		{
			spawnList = this.getDistanceSpawn(dist *= .9);
		}
		return getRandSpawn(spawnList);
	}
	public Location smartTeamSpawn(String tag, double dist)
	{
		List<Spawn> spawnList = this.getDistanceTeamSpawn(tag, dist);
		while(spawnList.size() == 0)
		{
			spawnList = this.getDistanceTeamSpawn(tag, dist *= .9);
		}
		return getRandSpawn(spawnList);
	}
	
	private List<Spawn> getDistanceSpawn(double distance)
	{
		List<ArenaPlayer> players = gameHandler.getPlayers();
		List<Spawn> spawnList = new ArrayList<Spawn>();
		for(int i = 0; i < spawns.size(); i++)
		{
			Spawn spawn = spawns.get(i);
			boolean canAdd = true;
			for(int j = 0; j < players.size(); j++)
			{
				if(spawn.getDistanceToPlayer(players.get(j)) < distance
						&& players.get(j).getState().canEffectSpawn()) canAdd = false;
			}
			if(canAdd == true) spawnList.add(spawn);
		}
		return spawnList;
	}
	private List<Spawn> getDistanceTeamSpawn(String tag, double distance)
	{
		List<ArenaPlayer> players = gameHandler.getPlayers();
		List<Spawn> spawnList = new ArrayList<Spawn>();
		List<Spawn> spawnTeams = teamSpawns.get(tag);
		for(int i = 0; i < spawnTeams.size(); i++)
		{
			Spawn spawn = spawnTeams.get(i);
			boolean canAdd = true;
			for(int j = 0; j < players.size(); j++)
			{
				if(spawn.getDistanceToPlayer(players.get(j)) < distance) canAdd = false;
			}
			if(canAdd == true) spawnList.add(spawn);
		}
		return spawnList;
	}
	@SuppressWarnings("unused")
	private List<Spawn> getTimeSpawn(double tics)
	{
		List<Spawn> spawnList = new ArrayList<Spawn>();
		int curTick = gameHandler.getTaskHandler().getGameTick();
		for(int i = 0; i < spawns.size(); i++)
		{
			if(curTick - spawns.get(i).getLastTic() > tics) spawnList.add(spawns.get(i));
		}
		return spawnList;
	}
	@SuppressWarnings("unused")
	private List<Spawn> getTimeTeamSpawn(String tag, double tics)
	{
		List<Spawn> spawnList = new ArrayList<Spawn>();
		List<Spawn> spawnTeams = teamSpawns.get(tag);
		int curTick = gameHandler.getTaskHandler().getGameTick();
		for(int i = 0; i < spawns.size(); i++)
		{
			if(curTick - spawnTeams.get(i).getLastTic() > tics) spawnList.add(spawnTeams.get(i));
		}
		return spawnList;
	}
	
	private List<Spawn> convertList(List<Location> locations)
	{
		List<Spawn> spawns = new ArrayList<Spawn>();
		if(locations == null || locations.isEmpty()) return spawns;
		for(int i = 0; i < locations.size(); i ++)
		{
			Spawn newSpawn = new Spawn(locations.get(i));
			spawns.add(newSpawn);
		}
		return spawns;
	}
}
