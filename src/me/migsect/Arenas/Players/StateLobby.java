package me.migsect.Arenas.Players;

import me.migsect.Arenas.GameTypes.GameHandler;

public class StateLobby extends ArenaPlayerState
{

	public StateLobby(GameHandler handler)
	{
		super(handler);
		
		this.canBreak = false;
		this.canAttack = false;
		this.canPlace = false;
		this.canEffectSpawn = false;
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
