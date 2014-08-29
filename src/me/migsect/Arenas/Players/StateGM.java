package me.migsect.Arenas.Players;

import org.bukkit.GameMode;

import me.migsect.Arenas.GameTypes.GameHandler;


public class StateGM extends ArenaPlayerState
{
	
	// In general this state gives full freedom over the game and as such does nothing
	//   to otherwise affect how events are handled.
	public StateGM(GameHandler handler)
	{
		super(handler);
		
		name = "Gamemaster";
		tag = "gamm";
		mode = GameMode.CREATIVE;
		
		canOperate = true;
		canKeepItems = true;
		canDeathDrop = true;
		
	}
	@Override
	public void enterState(ArenaPlayer player)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaveState(ArenaPlayer player)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateState(ArenaPlayer player)
	{
		// TODO Auto-generated method stub
		
	}

}
