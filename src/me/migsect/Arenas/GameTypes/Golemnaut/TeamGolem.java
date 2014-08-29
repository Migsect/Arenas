package me.migsect.Arenas.GameTypes.Golemnaut;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.GameTypes.ArenaTeam;
import me.migsect.Arenas.Players.ArenaPlayer;
import me.migsect.Arenas.Players.ArenaPlayerLoadout;

public class TeamGolem extends ArenaTeam
{

	ArenaPlayerLoadout loadout;
	
	public TeamGolem(Arenas plugin, ArenaGame game, String tag)
	{
		super(plugin, game, tag);
		teamLimit = 1;
		
		PotionEffect effect1 = new PotionEffect(PotionEffectType.SATURATION, 72000, 5, false);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 72000, 0, false);
		PotionEffect effect3 = new PotionEffect(PotionEffectType.SLOW, 72000, 1, false);
		PotionEffect effect4 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 72000, 0, false);
		PotionEffect effect5 = new PotionEffect(PotionEffectType.HEALTH_BOOST, 72000, 4, false);
		PotionEffect effect6 = new PotionEffect(PotionEffectType.WATER_BREATHING, 72000, 0, true);
		PotionEffect effect7 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 4, true);
		PotionEffect effect8 = new PotionEffect(PotionEffectType.REGENERATION, 100, 4, true);
		effects.add(effect1);
		effects.add(effect2);
		effects.add(effect3);
		effects.add(effect4);
		effects.add(effect5);
		effects.add(effect6);
		effects.add(effect7);
		effects.add(effect8);
		
		team.setPrefix("" + ChatColor.DARK_RED);
		team.setCanSeeFriendlyInvisibles(true);
		team.setAllowFriendlyFire(false);
		
		this.genLoadouts();
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
		
		ItemStack helm = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) helm.getItemMeta();
		meta.setOwner("MHF_Golem");
		ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
		ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
		ItemStack boots = new ItemStack(Material.IRON_BOOTS);
		loadout.setHelm(helm);
		loadout.setChest(chest);
		loadout.setLegs(legs);
		loadout.setBoots(boots);
		
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addUnsafeEnchantment(Enchantment.KNOCKBACK, 4);
		sword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 4);
		ItemStack arrow = new ItemStack(Material.ARROW);
		
		loadout.addItem(sword);
		loadout.addItem(bow);
		loadout.addItem(arrow);

	}
}
