package me.migsect.Arenas.Players;

import org.bukkit.GameMode;

import me.migsect.Arenas.GameTypes.GameHandler;

public class StateLobby extends ArenaPlayerState
{

	public StateLobby(GameHandler handler)
	{
		super(handler);
		
		this.canBreak = false;
		this.canAttack = false;
		this.canBeDamaged = false;
		this.canPlace = false;
		this.canEffectSpawn = false;
		this.canDrop = false;
		this.canPickup = false;
		this.canDeathDrop = false;
		
		tag = "lobb";
		name = "Lobby";
		mode = GameMode.ADVENTURE;
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
