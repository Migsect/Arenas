package me.migsect.Arenas.GameTypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;
import me.migsect.Arenas.Players.ArenaPlayerLoadout;

public abstract class ArenaTeam
{
	protected Arenas plugin;
	protected ArenaGame game;
	protected String teamName;
	protected String teamTag;
	protected Team team;
	protected int teamLimit = 1;
	protected List<ArenaPlayer> members;
	protected List<PotionEffect> effects;
	protected List<ArenaPlayerLoadout> loadouts = new ArrayList<ArenaPlayerLoadout>();

  //combat allowances
	boolean canDamage = true;
	boolean canBeDamaged = true;
	boolean canCollide = true;
	boolean canEffectSpawn = true;
	
	// item allowances
	boolean canConsume = true;
	boolean canPickup = true;
	boolean canDrop = true;
	boolean canDeathDrop = true;
	boolean canKeepItems = false;
	boolean canMoveArmor = true;
	
	// movement allowances
	
	boolean canMove = true;
	boolean canFly = true;
	boolean canSprint = true;
	boolean canJump = true;
	boolean canSneak = true;
	
	public ArenaTeam(Arenas plugin, ArenaGame game, String tag)
	{
		this.plugin = plugin;
		this.game = game;
		this.teamTag = tag;
		this.teamName = game.getTag() + tag;
		Scoreboard board = plugin.gameHandler.getScoreboard();
		team = board.registerNewTeam(this.teamName);
		
		members = new ArrayList<ArenaPlayer>();
		effects = new ArrayList<PotionEffect>();
	}
	
	final public String getTeamName(){return teamName;}
	final public String getTeamTag(){return teamTag;}
	final public List<ArenaPlayer> getTeamPlayers(){return members;}
	final public boolean containsPlayer(ArenaPlayer player){return members.contains(player);}
	final public int getTeamLimit(){return teamLimit;}
	final public boolean canAddPlayer(){return (members.size() < teamLimit);}
	final public Team getTeam(){return team;}
	
	//item allowances get
	final public boolean canKeepItems(){return canKeepItems;}
	final public boolean canConsume(){return canConsume;}
	final public boolean canPickup(){return canPickup;}
	final public boolean canDrop(){return canDrop;}
	final public boolean canDeathDrop(){return canDeathDrop;}
	final public boolean canMoveArmor(){return canMoveArmor;}
	
	// combat allowances get
	final public boolean canDamage(){return canDamage;}
	final public boolean canBeDamaged(){return canBeDamaged;}
	final public boolean canCollide(){return canCollide;}
	final public boolean canEffectSpawn(){return canEffectSpawn;}
	
	// movement allowances get
	final public boolean canMove(){return canMove;}
	final public boolean canFly(){return canFly;}
	final public boolean canSprint(){return canSprint;}
	final public boolean canSneak(){return canSneak;}
	
	final public List<ArenaPlayer> getPlayers()
	{
		return members;
	}
	final public void addPlayer(ArenaPlayer player)
	{
		members.add(player);
		team.addPlayer(player.getPlayer());
		player.setTeam(this);
		this.grantEffects(player);
	}
	final public void addPlayers(List<ArenaPlayer> players)
	{
		for(int i = 0; i < players.size(); i++)
		{
			this.addPlayer(players.get(i));
		}
	}
	final public void removePlayer(ArenaPlayer player)
	{
		members.remove(player);
		team.removePlayer(player.getPlayer());
		player.setTeam(null);
		List<PotionEffect> playerEffects = new ArrayList<PotionEffect>();
		playerEffects.addAll(player.getPlayer().getActivePotionEffects());
		for(int i = 0; i < playerEffects.size(); i++) player.getPlayer().removePotionEffect(playerEffects.get(i).getType());
	}
	final public void removeAllPlayers()
	{
		for(int i = 0; i < members.size(); i++)
		{
			this.removePlayer(members.get(i));
		}
	}
	
	final public void addEffect(PotionEffect effect){effects.add(effect);}
	final public List<PotionEffect> getEffects(){return effects;}
	// We are seperating the handling of the effect granting per team because it's annoying.
	final public void grantEffects(ArenaPlayer player)
	{
		for(int i = 0; i < effects.size(); i++)
		{
			player.getPlayer().addPotionEffect(effects.get(i));
		}
	}
	// A listens by a team will ONLY be for its own team members.
	//   As such it should be implied that only events caused by its team members will be sent.
	///  As such anything outside the player realm will not be covered by the teams.
	
	abstract public void onListenPlayerDeath(ArenaPlayer player); //
	
	abstract public void onListenPlayerKillPlayer(ArenaPlayer player, ArenaPlayer killed); //
	abstract public void onListenEntityKillPlayer(Entity entity, ArenaPlayer killed); //
	abstract public void onListenPlayerKillEntity(ArenaPlayer player, Entity killed); //
	abstract public void onListenEnviromentKillPlayer(ArenaPlayer player, DamageCause cause); //
	
	abstract public void onListenPlayerAttackPlayer(ArenaPlayer player, ArenaPlayer victim); //
	abstract public void onListenPlayerAttackEntity(ArenaPlayer player, Entity victim); //
	abstract public void onListenEntityAttackPlayer(Entity entity, ArenaPlayer victum); //
	abstract public void onListenPlayerArrowHit(ArenaPlayer player, Projectile projectile); //
	
	abstract public void onListenPlayerDamaged(ArenaPlayer player, DamageCause cause); //
	abstract public void onListenPlayerHitByArrow(ArenaPlayer player, Projectile projectile); //
	
	abstract public void onListenPlayerShootBow(ArenaPlayer player, Entity projectile);
	
	abstract public void onListenPlayerDrop(ArenaPlayer player, Item item); //
	abstract public void onListenPlayerPickup(ArenaPlayer player, Item item); // 
	abstract public void onListenPlayerConsume(ArenaPlayer player, ItemStack item); //
	abstract public void onListenPlayerItemHeld(ArenaPlayer player, ItemStack item); //
	abstract public void onListenPlayerExpChange(ArenaPlayer player, int amount); //
	
	abstract public void onListenPlayerMove(ArenaPlayer player, Location moveTo, Location moveFrom);
	abstract public void onListenPlayerSneak(ArenaPlayer player); //
	abstract public void onListenPlayerUnSneak(ArenaPlayer player); //
	abstract public void onListenPlayerSprint(ArenaPlayer player); //
	abstract public void onListenPlayerUnSprint(ArenaPlayer player); //
	abstract public void onListenPlayerFlight(ArenaPlayer player); //
	abstract public void onListenPlayerUnFlight(ArenaPlayer player); //
	abstract public void onListenPlayerJump(ArenaPlayer player); // TODO: Figure this shit out
	abstract public void onListenPlayerPortal(ArenaPlayer player);
	
	abstract public void onListenPlayerInteract(ArenaPlayer player, Block block); //
	abstract public void onListenPlayerInteractEntity(ArenaPlayer player, Entity entity); //
	abstract public void onListenPlayerBlockBreak(ArenaPlayer player, Block block); //
	abstract public void onListenPlayerPlaceBlock(ArenaPlayer player, Block block); //
}
