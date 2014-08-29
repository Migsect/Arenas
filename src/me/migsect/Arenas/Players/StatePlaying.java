package me.migsect.Arenas.Players;

import org.bukkit.GameMode;

import me.migsect.Arenas.GameTypes.GameHandler;

public class StatePlaying extends ArenaPlayerState
{
	// Theory:  StatePlaying does NOT include block breaking by standard. It is often a constructed
	//   state and can be adjusted as needed by a game.
	public StatePlaying(GameHandler handler)
	{
		super(handler);
		
		canBreak = false;
		canPickup = false;
		canDrop = false;
		canDeathDrop = false;
		canBreak = false;
		canPlace = false;
		
		tag = "play";
		name = "Playing";
		mode = GameMode.SURVIVAL;
	}

	@Override
	public void enterState(ArenaPlayer player)
	{
	}

	@Override
	public void leaveState(ArenaPlayer player)
	{
	}

	@Override
	public void updateState(ArenaPlayer player)
	{
	}

}
