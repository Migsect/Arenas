package me.migsect.Arenas.Players;

import java.util.HashMap;

import me.migsect.Arenas.GameTypes.GameHandler;

public class StateHandler
{
	GameHandler gameHandler;
	HashMap<String, ArenaPlayerState> universal = new HashMap<String, ArenaPlayerState>();
	HashMap<String, ArenaPlayerState> local = new HashMap<String, ArenaPlayerState>();
	
	// The purpose of the state handler is to be able to have
	//   an easily accessed repository of states for games to use.
	//   they also can have custom states.  States are entirely controlled by the game. But the resting
	//   state will be the lobby state.  As such any new player will be given a lobby state and then
	//   the game can add the player, this will be handled by hardcode in the listener.
	public StateHandler(GameHandler gameHandler)
	{
		this.gameHandler = gameHandler;
	}
	
	
	
	public void registerUniversal(ArenaPlayerState state)
	{
		universal.put(state.getTag(), state);
	}
	public void registerLocal(ArenaPlayerState state)
	{
		local.put(state.getTag(), state);
	}
	public ArenaPlayerState getUniversal(String key)
	{
		return universal.get(key);
	}
	public ArenaPlayerState getLocal(String key)
	{
		return local.get(key);
	}
	public void clearLocal()
	{
		local.clear();
	}
}
