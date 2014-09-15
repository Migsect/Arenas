package me.migsect.Arenas.Events;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class CombatListener implements Listener
{
	Arenas plugin;
	
	public CombatListener(Arenas plugin)
	{
		this.plugin = plugin;
	}
	
	/*
	@EventHandler(priority = EventPriority.LOWEST)
	public void undoDeath(EntityDamageByEntityEvent event)
	{
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		double damage = ArenaHelper.reduceDamage(player, event.getDamage());
		double life = player.getHealth();
		if(damage >= life)
		{
			event.setCancelled(true);
			player.setHealth(20);
			player.getInventory().clear();
			player.setExp(0);
			player.setLevel(0);
			List<ItemStack> drops = new ArrayList<ItemStack>();
			PlayerDeathEvent e = new PlayerDeathEvent(player,drops,0,0,0,0,"");
			plugin.getServer().getPluginManager().callEvent(e);
		}
		
	}
	*/
	@SuppressWarnings("deprecation")
	@EventHandler 
	// Handles:
	//  - entityDeathEvent
	//  - entityKillEvent
	//  - playerKillEvent
	//  - playerKillEntityEvent
	public void onEntityDeath(EntityDeathEvent event)
	{
		Entity entity = event.getEntity();
		if(entity instanceof Player) return; // We don't need a redundacy.
		// if(event.isCanceled) return  - - - Cannot be canceled.
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventEntityDeath(event);
		// if(event.isCanceled) return  - - - Cannot be canceled.
		
		// Start of onListen
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenEntityDeath(entity);
		if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
			Entity killer = cause.getDamager();
			
			if(killer instanceof Player)
			{
				if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerKillEntity(plugin.gameHandler.getPlayer((Player) killer),entity);
				plugin.gameHandler.getPlayer((Player) killer).getTeam().onListenPlayerKillEntity(plugin.gameHandler.getPlayer((Player) killer), entity);
			}
			else if(killer instanceof Arrow)
			{
				Arrow arrow = (Arrow) killer;
				Entity shooter = arrow.getShooter();
				if(shooter instanceof Player)
				{
					ArenaPlayer shooterPlayer = plugin.gameHandler.getPlayer((Player)shooter);
					if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerKillEntity(shooterPlayer,entity);
					if(shooterPlayer.hasTeam()) shooterPlayer.getTeam().onListenPlayerKillEntity(shooterPlayer, entity);
					return;
				}
				else // shooter is not a player and something else
				{
					// Do nothing for now.
					return;
				}
			}
			else // Killer is not a player.
			{
				if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenEntityKillEntity(killer, entity);
				
			}
		}
	}
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		// if(event.isCanceled) return  - - - Cannot be canceled.
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventPlayerDeath(event);
		// if(event.isCanceled) return  - - - Cannot be canceled.
		
		// Start of onListen:
		ArenaPlayer player = plugin.gameHandler.getPlayer(event.getEntity());
		plugin.gameHandler.getLoadedGame().onListenPlayerDeath(player);
		if(player.hasTeam())player.getTeam().onListenPlayerDeath(player);
		
		// We need to clear the items that are being dropped if the player cannot drop it.
		if(!player.canDeathDrop()) event.getDrops().clear();

		player.setLastDeathLocation(player.getPlayer().getLocation());
		if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) // if the death was caused by an entity.
		{
			EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
			if(cause.getDamager() instanceof Player)
			{
	    		ArenaPlayer killer = plugin.gameHandler.getPlayer((Player) cause.getDamager());
	    		plugin.gameHandler.getLoadedGame().onListenPlayerKillPlayer(killer, player);
	    		if(player.hasTeam()) player.getTeam().onListenPlayerKillPlayer(killer, player);
			}
	    	else if (cause.getDamager() instanceof Entity)
	    	{
	    		Entity killer = event.getEntity().getLastDamageCause().getEntity();
	    		plugin.gameHandler.getLoadedGame().onListenEntityKillPlayer(killer, player);
	    		if(player.hasTeam()) player.getTeam().onListenEntityKillPlayer(killer, player);
	    	}
		}
		else // The death is caused by nature.
      	{
      		DamageCause damageCause = event.getEntity().getLastDamageCause().getCause();
      		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenEnviromentKillPlayer(player, damageCause);
      		if(player.hasTeam()) player.getTeam().onListenEnviromentKillPlayer(player, damageCause);
      	}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventEntityDamage(event);
		if(event.isCancelled()) return;
		
		Entity entity = event.getEntity();
		if(entity instanceof Player)
		{
			ArenaPlayer player =plugin.gameHandler.getPlayer((Player) event.getEntity());
			if(!player.canBeDamaged())
			{
				event.setCancelled(true);
				return;
			}
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerDamaged(player, event.getCause());
			if(player.hasTeam()) player.getTeam().onListenPlayerDamaged(player, event.getCause());
			return;
		}
		else
		{
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenEntityDamaged(entity, event.getCause());
			return;
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamagedByEntity(EntityDamageByEntityEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventEntityDamagedByEntity(event);
		if(event.isCancelled()) return;
		
		if(event.getEntity() instanceof Player) // If the entity being attacked is a player.
		{
			ArenaPlayer player = plugin.gameHandler.getPlayer((Player)event.getEntity());
			Entity attacker = event.getDamager();
			if(!player.canBeDamaged()) 
			{
				event.setCancelled(true);
				return;
			}
			if(attacker instanceof Player)
			{
				ArenaPlayer attackerPlayer = plugin.gameHandler.getPlayer((Player) attacker);
				if(!attackerPlayer.canDamage())
				{
					event.setCancelled(true);
					return;
				}
				if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerAttackPlayer(attackerPlayer, player);
				if(player.hasTeam()) player.getTeam().onListenPlayerAttackPlayer(attackerPlayer, player);
				if(attackerPlayer.hasTeam()) attackerPlayer.getTeam().onListenPlayerAttackPlayer(attackerPlayer, player);
			}
			else if(attacker instanceof Arrow) // Arrow Hit/Shoot handling
			{
				Arrow projectile = (Arrow) attacker;
				if(!player.canCollide()) // Do note that before a more elegant solution can be achieved, this is a spectator only thing.
				{
					Entity shooter = (Entity) projectile.getShooter();
					
					Vector velocity = projectile.getVelocity();
					double xComp = velocity.getX();
					double yComp = velocity.getY();
					double zComp = velocity.getZ();
					double magnitude = velocity.length();
					
					double xUComp = xComp / magnitude;
					double yUComp = yComp / magnitude;
					double zUComp = zComp / magnitude;
					
					player.getPlayer().teleport(player.getPlayer().getLocation().add(xUComp, yUComp, zUComp));
					
					Arrow newArrow = ((ProjectileSource) shooter).launchProjectile(Arrow.class);
					newArrow.setShooter((ProjectileSource) shooter);
					newArrow.setVelocity(velocity);
					newArrow.setBounce(false);
					
					event.setCancelled(true);
					projectile.remove();
					return;
				}
				plugin.gameHandler.getLoadedGame().onListenPlayerHitByArrow(player, projectile);
				if(player.hasTeam()) player.getTeam().onListenPlayerHitByArrow(player, projectile);
				if(projectile.getShooter() instanceof Player)
				{
					ArenaPlayer attackerPlayer = plugin.gameHandler.getPlayer((Player) projectile.getShooter());
					if(!attackerPlayer.canDamage())
					{
						event.setCancelled(true);
						return;
					}
					if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerArrowHit(attackerPlayer, projectile);
					if(attackerPlayer.hasTeam()) attackerPlayer.getTeam().onListenPlayerArrowHit(attackerPlayer, projectile);
					
					if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerHitByArrow(player, projectile);
					if(attackerPlayer.hasTeam()) attackerPlayer.getTeam().onListenPlayerHitByArrow(player, projectile);
					
					if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerAttackPlayer(attackerPlayer, player);
					if(attackerPlayer.hasTeam()) attackerPlayer.getTeam().onListenPlayerAttackPlayer(attackerPlayer, player);
					
					
				}
				else if(projectile.getShooter() instanceof Entity)
				{
					Entity entity = (Entity) projectile.getShooter();
					if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenEntityArrowHit(entity, projectile);
				}
			}
			else // attacker instanceof just Entity
			{
				if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenEntityAttackPlayer(attacker, player);
				if(player.hasTeam()) player.getTeam().onListenEntityAttackPlayer(attacker, player);
			}
				
			return;
		}
		else
		{
			Entity entity = event.getEntity();
			Entity attacker = event.getDamager();
			if(attacker instanceof Player)
			{
				ArenaPlayer attackerPlayer = plugin.gameHandler.getPlayer((Player) attacker);
				if(!attackerPlayer.canDamage())
				{
					event.setCancelled(true);
					return;
				}
				plugin.gameHandler.getLoadedGame().onListenPlayerAttackEntity(attackerPlayer, entity);
				if(attackerPlayer.hasTeam()) attackerPlayer.getTeam().onListenPlayerAttackEntity(attackerPlayer, entity);
				
			}
			else if(attacker instanceof Projectile);
			{
				Projectile projectile = (Projectile) attacker;
				plugin.gameHandler.getLoadedGame().onListenEntityHitByArrow(entity, projectile);
			}
		}
	}
	@EventHandler
	public void onEntityShootBowEvent(EntityShootBowEvent event)
	{
		if(event.isCancelled()) return;
		if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onEventEntityShootBowEvent(event);
		if(event.isCancelled()) return;
		
		Entity shooter = event.getEntity();
		if(shooter instanceof Player)
		{
			ArenaPlayer player = plugin.gameHandler.getPlayer((Player) shooter);
			if(!player.canDamage())
			{
				event.setCancelled(true);
				return;
			}
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenPlayerShootBow(player, event.getProjectile());
			if(player.hasTeam()) player.getTeam().onListenPlayerShootBow(player, event.getProjectile());
		}
		else
		{
			if(plugin.gameHandler.isGameLoaded()) plugin.gameHandler.getLoadedGame().onListenEntityShootBow(event.getEntity(), event.getProjectile());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	