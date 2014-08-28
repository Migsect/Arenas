package me.migsect.Arenas.Players;

import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.migsect.Arenas.GameTypes.GameHandler;

public class StateGhost extends ArenaPlayerState
{
	public StateGhost(GameHandler handler)
	{
		super(handler);
		canBreak = false;
		canAttack = false;
		canPickup = false;
		canDrop = false;
		canConsume = false;
		canCollide = false;
		canEffectSpawn = false;
		canBeDamaged = false;
		
		PotionEffect effect1 = new PotionEffect(PotionEffectType.REGENERATION, 3600, 4, false);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SATURATION, 3600, 4, false);
		PotionEffect effect3 = new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 0, false);
		PotionEffect effect4 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3600, 4, false);
		effects.add(effect1);
		effects.add(effect2);
		effects.add(effect3);
		effects.add(effect4);
		
		mode = GameMode.SURVIVAL;
	}
	@Override
	public void enterState(ArenaPlayer player)
	{
		player.getPlayer().setAllowFlight(true);
		handler.addHidden(player);
		player.getPlayer().setGameMode(mode);
	}

	@Override
	public void leaveState(ArenaPlayer player)
	{
		player.getPlayer().setAllowFlight(false);
		handler.removeHidden(player);
		player.getPlayer().setGameMode(GameMode.SURVIVAL);
		removeAllEffects(player);
	}

	@Override
	public void updateState(ArenaPlayer player)
	{
	}

}
