package me.migsect.Arenas.Players;

import java.util.HashMap;

import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.GameTypes.ArenaTeam;
import me.migsect.Arenas.GameTypes.GameHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaPlayer
{
	// Arena players are a better means of storing player data that allows also for stat tracking aside
	// from storing the data in each games data. This is also a means of grabbing information about the player.
	
	GameHandler handler;
	
	Player player;
	ArenaGame game = null;
	ArenaTeam team = null;
	ArenaPlayerState state;
	ArenaPlayerLoadout loadout;
	int kills;
	int deaths;
	
	HashMap<String, ArenaPlayerScore> scores = new HashMap<String, ArenaPlayerScore>();
	
	
	// combat allowances
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
	
	Location lastDeath;
	
	public ArenaPlayer(Player player, GameHandler handler)
	{
		this.handler = handler;
		this.player = player;
		lastDeath = player.getLocation();
		resetStats();
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public boolean inGame(){
		if (game == null) return false;
		return true;
	}
	public ArenaGame getGame(){
		return game;
	}
	public void setGame(ArenaGame game)
	{
		this.game = game;
	}
	
	public boolean inTeam(ArenaTeam team){
		if(team == null) return false;
		return true;
	}
	public boolean inTeam(ArenaPlayer player){
		if(player.getTeam() == this.getTeam()) return true;
		return false;
	}
	public boolean hasTeam()
	{
		if(team == null) return false;
		return true;
	}
	public ArenaTeam getTeam(){return team;}
	public void setTeam(ArenaTeam team){this.team = team;}
	public void setLastDeathLocation(Location loc){lastDeath = loc;}
	public boolean hasLastDeathLocation()
	{
		if(lastDeath != null) return true;
		return false;
	}
	public Location getLastDeathLocation(){return lastDeath;}
	
	public int getKills(){return kills;}
	public int getDeaths(){return deaths;}
	public void setKills(int num){kills = num;}
	public void setDeaths(int num){deaths = num;}
	public void resetStats()
	{
		kills = 0;
		deaths = 0;
	}
	
	public void addKill()
	{
		kills++;
	}
	public void addDeath()
	{
		deaths++;
	}
	
	public void playerDeath()
	{
		
	}
	
	public void setState(ArenaPlayerState state)
	{
		if(this.hasState()) this.state.leaveState(this);
		this.state = state;
		this.state.enterState(this);
	}
	public void setLoadout(ArenaPlayerLoadout loadout)
	{
		this.player.getInventory().clear();
		this.loadout = loadout;
	}
	public ArenaPlayerState getState(){return this.state;}
	public ArenaPlayerLoadout getLoadout(){return this.loadout;}
	public boolean hasState()
	{
		return !(state == null);
	}
	public boolean hasLoadout()
	{
		return !(loadout == null);
	}
	// combat allowances sets
	public void setCanDamage(boolean bool){canDamage = bool;}
	public void setCanBeDamaged(boolean bool){canBeDamaged = bool;}
	public void setCanEffectSpawn(boolean bool){canEffectSpawn = bool;}
	public void setCanCollude(boolean bool){canCollide = bool;}
	
	// item allowances sets
	public void setCanKeepItems(boolean bool){canKeepItems = bool;}
	public void setCanDeathDrop(boolean bool){canDeathDrop = bool;}
	public void setCanMoveArmor(boolean bool){canMoveArmor = bool;}
	public void setCanConsume(boolean bool){canConsume = bool;}
	public void setCanPickup(boolean bool){canPickup = bool;}
	public void setCanDrop(boolean bool){canDrop = bool;}
	
	// movement allowances sets
	public void setCanMove(boolean bool){canMove = bool;}
	public void setCanFly(boolean bool){canFly = bool;}
	public void setCanSprint(boolean bool){canSprint = bool;}
	public void setCanSneak(boolean bool){canSneak = bool;}
	
	// item allowances get
	public boolean canKeepItems()
	{
		if(this.hasTeam()) if(!this.getTeam().canKeepItems()) return false;
		if(this.hasState()) if(!this.getState().canKeepItems()) return false;
		if(this.game != null) if(!this.game.canKeepItems()) return false;
		return canKeepItems;
	}
	public boolean canDeathDrop()
	{
		if(this.hasTeam()) if(!this.getTeam().canDeathDrop()) return false;
		if(this.hasState()) if(!this.getState().canDeathDrop()) return false;
		if(this.game != null) if(!this.game.canDeathDrop()) return false;
		return canDeathDrop;
		}
	public boolean canMoveArmor()
	{
		if(this.hasTeam()) if(!this.getTeam().canMoveArmor()) return false;
		if(this.hasState()) if(!this.getState().canMoveArmor()) return false;
		if(this.game != null) if(!this.game.canMoveArmor()) return false;
		return canMoveArmor;
	}
	public boolean canConsume()
	{
		if(this.hasTeam()) if(!this.getTeam().canConsume()) return false;
		if(this.hasState()) if(!this.getState().canConsume()) return false;
		if(this.game != null) if(!this.game.canConsume()) return false;
		return canConsume;
	}
	public boolean canPickup()
	{
		if(this.hasTeam()) if(!this.getTeam().canPickup()) return false;
		if(this.hasState()) if(!this.getState().canPickup()) return false;
		if(this.game != null) if(!this.game.canPickup()) return false;
		return canPickup;
	}
	public boolean canDrop()
	{
		if(this.hasTeam()) if(!this.getTeam().canDrop()) return false;
		if(this.hasState()) if(!this.getState().canDrop()) return false;
		if(this.game != null) if(!this.game.canDrop()) return false;
		return canDrop;
	}
	
	// combat allowances get
	public boolean canDamage()
	{
		if(this.hasTeam()) if(!this.getTeam().canDamage()) return false;
		if(this.hasState()) if(!this.getState().canDamage()) return false;
		if(this.game != null) if(!this.game.canDamage()) return false;
		return canDamage;
	}
	public boolean canBeDamaged()
	{
		if(this.hasTeam()) if(!this.getTeam().canBeDamaged()) return false;
		if(this.hasState()) if(!this.getState().canBeDamaged()) return false;
		if(this.game != null) if(!this.game.canBeDamaged()) return false;
		return canBeDamaged;
	}
	public boolean canCollide()
	{
		if(this.hasTeam()) if(!this.getTeam().canCollide()) return false;
		if(this.hasState()) if(!this.getState().canCollide()) return false;
		if(this.game != null) if(!this.game.canCollide()) return false;
		return canCollide;
	}
	public boolean canEffectSpawn()
	{
		if(this.hasTeam()) if(!this.getTeam().canEffectSpawn()) return false;
		if(this.hasState()) if(!this.getState().canEffectSpawn()) return false;
		if(this.game != null) if(!this.game.canEffectSpawn()) return false;
		return canEffectSpawn;
	}
	
	// movement allowances get
	public boolean canMove()
	{
		if(this.hasTeam()) if(!this.getTeam().canMove()) return false;
		if(this.hasState()) if(!this.getState().canMove()) return false;
		if(this.game != null) if(!this.game.canMove()) return false;
		return canMove;
	}
	public boolean canFly()
	{
		if(this.hasTeam()) if(!this.getTeam().canFly()) return false;
		if(this.hasState()) if(!this.getState().canFly()) return false;
		if(this.game != null) if(!this.game.canFly()) return false;
		return canFly;
	}
	public boolean canSprint()
	{
		if(this.hasTeam()) if(!this.getTeam().canSprint()) return false;
		if(this.hasState()) if(!this.getState().canSprint()) return false;
		if(this.game != null) if(!this.game.canSprint()) return false;
		return canSprint;
	}
	public boolean canSneak()
	{
		if(this.hasTeam()) if(!this.getTeam().canSneak()) return false;
		if(this.hasState()) if(!this.getState().canSneak()) return false;
		if(this.game != null) if(!this.game.canSneak()) return false;
		return canSneak;
	}
	
	
	
	
}
