package me.migsect.Arenas.GameTypes.Golemnaut;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
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
import me.migsect.Arenas.Players.ArenaPlayerLoadout;

public class TeamVillagers extends ArenaTeam
{
	private ArenaPlayerLoadout loadout = new ArenaPlayerLoadout("Villager Loadout", "vill", Material.STICK);
	public TeamVillagers(Arenas plugin, ArenaGame game, String tag)
	{
		super(plugin, game, tag);
		teamLimit = 20;
		
		PotionEffect effect1 = new PotionEffect(PotionEffectType.SPEED, 3600, 0, false);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.JUMP, 3600, 2, false);
		PotionEffect effect3 = new PotionEffect(PotionEffectType.SATURATION, 3600, 0, false);
		effects.add(effect1);
		effects.add(effect2);
		effects.add(effect3);
		
		team.setPrefix("" + ChatColor.GREEN);
		team.setCanSeeFriendlyInvisibles(true);
		team.setAllowFriendlyFire(false);
		
		genLoadouts();
	}

	public void setLoadout(ArenaPlayer player)
	{
		player.setLoadout(loadout);
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
	private void genLoadouts()
	{
		loadout = new ArenaPlayerLoadout("Golem", "gole", Material.IRON_BLOCK);
		
		ItemStack helm = new ItemStack(Material.IRON_HELMET);
		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		loadout.setHelm(helm);
		loadout.setChest(chest);
		loadout.setLegs(legs);
		loadout.setBoots(boots);
		
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		loadout.addItem(sword);
		loadout.addItem(bow);
		loadout.addItem(arrow);

	}
}
