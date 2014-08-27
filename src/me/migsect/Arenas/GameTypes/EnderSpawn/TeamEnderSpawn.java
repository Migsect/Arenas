package me.migsect.Arenas.GameTypes.EnderSpawn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.GameTypes.ArenaTeam;
import me.migsect.Arenas.Players.ArenaPlayer;

public class TeamEnderSpawn extends ArenaTeam
{
	
	public TeamEnderSpawn(Arenas plugin, ArenaGame game)
	{
		super(plugin, game, "ends");
		teamLimit = 1;
		
		PotionEffect effect1 = new PotionEffect(PotionEffectType.HUNGER, 3600, 1, false);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 0, false);
		PotionEffect effect3 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 3600, 1, false);
		PotionEffect effect4 = new PotionEffect(PotionEffectType.SPEED, 3600, 2, false);
		PotionEffect effect5 = new PotionEffect(PotionEffectType.JUMP, 3600, 2, false);
		effects.add(effect1);
		effects.add(effect2);
		effects.add(effect3);
		effects.add(effect4);
		effects.add(effect5);
		
		team.setPrefix("" + ChatColor.BLUE);
		team.setCanSeeFriendlyInvisibles(true);
		team.setAllowFriendlyFire(false);
	}

	@Override
	public void onListenPlayerDeath(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerKillPlayer(ArenaPlayer player, ArenaPlayer killed)
	{
	}

	@Override
	public void onListenEntityKillPlayer(Entity entity, ArenaPlayer killed)
	{
	}

	@Override
	public void onListenPlayerKillEntity(ArenaPlayer player, Entity killed)
	{
	}

	@Override
	public void onListenEnviromentKillPlayer(ArenaPlayer player, DamageCause cause)
	{
	}

	@Override
	public void onListenPlayerAttackPlayer(ArenaPlayer player, ArenaPlayer victim)
	{
	}

	@Override
	public void onListenPlayerAttackEntity(ArenaPlayer player, Entity victim)
	{
	}

	@Override
	public void onListenEntityAttackPlayer(Entity entity, ArenaPlayer victum)
	{
	}

	@Override
	public void onListenPlayerArrowHit(ArenaPlayer player, Projectile projectile)
	{
	}

	@Override
	public void onListenPlayerDamaged(ArenaPlayer player, DamageCause cause)
	{
	}

	@Override
	public void onListenPlayerHitByArrow(ArenaPlayer player, Projectile projectile)
	{
	}

	@Override
	public void onListenPlayerShootBow(ArenaPlayer player, Entity projectile)
	{
	}

	@Override
	public void onListenPlayerDrop(ArenaPlayer player, Item item)
	{
	}

	@Override
	public void onListenPlayerPickup(ArenaPlayer player, Item item)
	{
	}

	@Override
	public void onListenPlayerConsume(ArenaPlayer player, ItemStack item)
	{
	}

	@Override
	public void onListenPlayerItemHeld(ArenaPlayer player, ItemStack item)
	{
	}

	@Override
	public void onListenPlayerExpChange(ArenaPlayer player, int amount)
	{
	}

	@Override
	public void onListenPlayerMove(ArenaPlayer player, Location moveTo,
			Location moveFrom)
	{
	}

	@Override
	public void onListenPlayerSneak(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerUnSneak(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerSprint(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerUnSprint(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerFlight(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerUnFlight(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerJump(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerPortal(ArenaPlayer player)
	{
	}

	@Override
	public void onListenPlayerInteract(ArenaPlayer player, Block block)
	{
	}

	@Override
	public void onListenPlayerInteractEntity(ArenaPlayer player, Entity entity)
	{
	}

	@Override
	public void onListenPlayerBlockBreak(ArenaPlayer player, Block block)
	{
	}

	@Override
	public void onListenPlayerPlaceBlock(ArenaPlayer player, Block block)
	{
	}
}
