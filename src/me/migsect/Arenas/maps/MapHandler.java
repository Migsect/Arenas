package me.migsect.Arenas.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaTeam;
import me.migsect.Arenas.GameTypes.GameHandler;

public class MapHandler
{
	// Map Handler will store all map information and handle the loading and unloading of maps.
	//   This is sort of like the gameHandler put for maps.
	//   One important note is that the gameHandler will CONTAIN the mapHandler.  This is how
	//   we can get the maps needed.
	
	private GameHandler gameHandler;
	private Arenas plugin;
	
	private Map loadedMap = null;
	private HashMap<String, Map> maps = new HashMap<String, Map>();
	
	public MapHandler(Arenas plugin, GameHandler gameHandler)
	{
		this.plugin = plugin;
		this.gameHandler = gameHandler;
	}
	
	public void registerMaps()
	{
		List<String> mapKeys = new ArrayList<String>(plugin.mapConfig.getConfig().getConfigurationSection("maps").getKeys(false));
		for(int i = 0; i < mapKeys.size(); i++)
		{
			Map newMap = new Map(this);
			constructMap(newMap, "maps." + mapKeys.get(i));
			maps.put(newMap.getTag(),newMap);
		}
	}
	
	public Location getMainLobby(){return generateLocation(Bukkit.getWorld(plugin.mapConfig.getConfig().getString("mainlobbyworld")), plugin.mapConfig.getConfig().getString("mainlobby"));}
	public List<Map> getMaps(){return new ArrayList<Map>(maps.values());}
	public boolean mapExists(String mapTag){return maps.containsKey(mapTag);}
	public Map getMap(String mapTag){return maps.get(mapTag);}
	public void loadMap(Map map)
	{
		loadedMap = map;
		
		// This will set up the spawnHandler to handler the new spawns for the map.
		SpawnHandler spawnHandler = gameHandler.getSpawnHandler();
		spawnHandler.addList(map.getGenSpawns());
		List<ArenaTeam> teams = gameHandler.getLoadedGame().getTeams();
		for(int i = 0; i < teams.size(); i++)
		{
			spawnHandler.addTeamList(teams.get(i).getTeamTag(), map.getTeamSpawns(teams.get(i).getTeamTag()));
		}
		if(gameHandler.isGameLoaded())gameHandler.getLoadedGame().teleportPlayers(map.getLobby());
	}
	public void loadMap(String mapTag){loadMap(getMap(mapTag));}
	public void unloadMap()
	{
		SpawnHandler spawnHandler = gameHandler.getSpawnHandler();
		spawnHandler.emptySpawns();
		loadedMap = null;
	}
	public Map getLoadedMap(){return loadedMap;}
	public boolean isMapLoaded(){return loadedMap != null;}
	
	private void constructMap(Map map, String mapkey)
	{
		World world = Bukkit.getWorld(plugin.mapConfig.getConfig().getString(mapkey + ".world"));
		map.setWorld(world);
		Location lobbyLoc = generateLocation(world,plugin.mapConfig.getConfig().getString(mapkey + ".lobby"));
		map.setLobby(lobbyLoc);
		Location centerLoc = generateLocation(world,plugin.mapConfig.getConfig().getString(mapkey + ".center"));
		map.setCenter(centerLoc);
		int maxDistance = plugin.mapConfig.getConfig().getInt(mapkey + ".center-maxDistance");
		map.setMaxDistance(maxDistance);
		int mapBottom = plugin.mapConfig.getConfig().getInt(mapkey + ".mapBottom");
		map.setMapBottom(mapBottom);
		String displayName = plugin.mapConfig.getConfig().getString(mapkey + ".displayName");
		map.setDisplayName(displayName);
		String mapTag = plugin.mapConfig.getConfig().getString(mapkey + ".tag");
		map.setTag(mapTag);
		List<String> gameKeys = plugin.mapConfig.getConfig().getStringList(mapkey + ".gametypes");
		for(int i = 0; i < gameKeys.size(); i++)
		{
			if(plugin.gameHandler.gameExists(gameKeys.get(i)))
			{
				map.addGameType(gameHandler.getGame(gameKeys.get(i)));
			}
		}
		List<String> spawnPoints = plugin.mapConfig.getConfig().getStringList(mapkey + ".spawnpoints");
		for(int i = 0; i < spawnPoints.size(); i++)
		{
			map.addGenSpawn(generateLocation(world,spawnPoints.get(i)));
		}
		List<String> teamSpawns = new ArrayList<String>();
		teamSpawns.addAll(plugin.mapConfig.getConfig().getConfigurationSection(mapkey + ".teamspawnpoints").getKeys(false));
		for(int i = 0; i < teamSpawns.size(); i++)
		{
			String teamTag = teamSpawns.get(i);
			// Now we need to get the list of strings that we will transform.
			List<String> readTeamSpawns = plugin.mapConfig.getConfig().getStringList(mapkey + ".teamspawnpoints." + teamSpawns.get(i));
			List<Location> teamSpawnLoc = new ArrayList<Location>();
			for(int j = 0; j < readTeamSpawns.size(); j++)
			{
				teamSpawnLoc.add(generateLocation(world,readTeamSpawns.get(i)));
			}
			map.addTeamSpawns(teamTag, teamSpawnLoc);
		}
	}
	private Location generateLocation(World world, String rawLoc)
	{
		String[] splited = rawLoc.split(" ");
		int x = 0;
		int y = 0;
		int z = 0;
		try
		{
			x = Integer.parseInt(splited[0]);
			y = Integer.parseInt(splited[1]);
			z = Integer.parseInt(splited[2]);
		}
		catch (NumberFormatException e)
		{
			plugin.logger.warning("The map.yml is not correctly formatted!");
		}
		Location location = new Location(world, x,y,z);
		return location;
	}
}
