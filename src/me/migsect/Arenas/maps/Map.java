package me.migsect.Arenas.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

import me.migsect.Arenas.GameTypes.ArenaGame;

public class Map
{
	
	@SuppressWarnings("unused")
	private MapHandler mapHandler;
	
	private List<String> gameTypes = new ArrayList<String>();
	
	private World world = null;
	private Location lobby = null;
	private Location center = null;
	private int maxDistance = 0;
	private int mapBottom = 0;
	private List<Location> genSpawns = new ArrayList<Location>();
	private HashMap<String,List<Location>> teamSpawns = new HashMap<String,List<Location>>();
	private String displayName;
	private String tag;
	
	public Map(MapHandler mapHandler)
	{
		this.mapHandler = mapHandler;
	}
	
	public void addGenSpawn(Location loc){genSpawns.add(loc);}
	public void addTeamSpawns(String tag, List<Location> locs){teamSpawns.put(tag, locs);}
	
	public void addGameType(ArenaGame game){gameTypes.add(game.getTag());}
	public void addGameType(String gameTag){gameTypes.add(gameTag);}
	public boolean canSupportGame(String gameTag){return gameTypes.contains(gameTag);}
	public void setWorld(World world){this.world = world;}
	public World getWorld(){return world;}
	public void setLobby(Location location){lobby = location;}
	public Location getLobby(){return lobby;}
	public void setCenter(Location center){this.center = center;}
	public Location getCenter(){return center;}
	public void setMaxDistance(int maxDistance){this.maxDistance = maxDistance;}
	public int getMaxDistance(){return maxDistance;}
	public void setMapBottom(int mapBottom){this.mapBottom = mapBottom;}
	public int getMapBottom(){return mapBottom;}
	public void setDisplayName(String displayName){this.displayName = displayName;}
	public String getDisplayName(){return displayName;}
	public void setTag(String tag){this.tag = tag;}
	public String getTag(){return tag;}
	
	public boolean hasGameType(ArenaGame game){return gameTypes.contains(game);}
	public List<String> getGameTypes(){return gameTypes;}
	public List<Location> getGenSpawns(){return genSpawns;}
	public Location getRandGenSpawn()
	{
		Random rand = new Random();
		return genSpawns.get(rand.nextInt(genSpawns.size()));
	}
	public List<Location> getTeamSpawns(String teamTag){return teamSpawns.get(teamTag);}
	public Location getRandTeamSpawn(String teamTag)
	{
		Random rand = new Random();
		return teamSpawns.get(teamTag).get(rand.nextInt(teamSpawns.get(teamTag).size()));
	}
}
