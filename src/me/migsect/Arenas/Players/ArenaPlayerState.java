package me.migsect.Arenas.Players;

import java.util.ArrayList;
import java.util.List;

import me.migsect.Arenas.GameTypes.GameHandler;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;

// The purpose of this interface is to create a variety of different states a player
//  can be in.  These states will be as the following and may include more:
//    - Custom
//    - Lobby
//    - Spectator
//    - Player
//    - Ghost
//  They can include more or less than what is stated above but the purpose of using this
//    Interface is to allow an more easily managable character above what the current game allows.
//    Each state will basically determine if a player can perform certain actions or not.  As such
//    it will be an assemblage of letting or not letting certain events to occur.  
//  Some game states may allow greater control for arenas allowing them to be constructed in 
//    a manner to prevent breaking of certain blocks or otherwise.  As such these are like
//    a form of teams but allowing greater strength and variety.
//  States WILL NOT be used to grab data and find other players with a state.  
//    They are merely an additional means to control players and what they can do and furthermore
//    make it easier to develope each game.  Each game handler will control the player's state.
//    this makes each game have to work through it's gamehandler to change a player's state.
//    As such this will seperate the handling of states from a game and give it overall to
//    the game handler making game clean up a bit easier.
public abstract class ArenaPlayerState
{
	protected String name = "Nil";
	protected String tag = "nilh";
	protected GameMode mode = GameMode.SURVIVAL;
	protected GameHandler handler;
	
	protected boolean canPlace = true;
	protected boolean canBreak = true;
	protected List<Material> breakWhitelist = new ArrayList<Material>();
	protected List<Material> breakBlacklist = new ArrayList<Material>();
	protected boolean canAttack = true;
	protected List<EntityType> attackWhitelist = new ArrayList<EntityType>();
	protected List<EntityType> attackBlacklist = new ArrayList<EntityType>();
	
	protected List<PotionEffect> effects = new ArrayList<PotionEffect>();
	
	protected boolean isHidden = false;
	
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
	boolean canOperate = false;
	
	public ArenaPlayerState(GameHandler handler)
	{
		this.handler = handler;
	}
	
	final public String getStateName(){return name;}
	
	// State Switching:  Occurs whenever a player enters or leaves this state
	abstract public void enterState(ArenaPlayer player);
	abstract public void leaveState(ArenaPlayer player);
	abstract public void updateState(ArenaPlayer player); // This is used for when the system updates a state for some reason (I don't know why it would)
	
	// Event Allowances
	final public boolean canPlace(){return canPlace;}
	// TODO make a canPlace(Block) including a whitelist an stuff
	final public boolean canBreak(){return canBreak;}
	final public boolean canBreak(Material material)
	{
		if(canBreak == false && breakWhitelist.contains(material)) return true;
		if(canBreak == true && breakBlacklist.contains(material)) return false;
		return canBreak;
	}
	final public boolean canBreak(Block block)
	{
		return this.canBreak(block.getType());
	}
	final public boolean canAttack(){return canAttack;}
	final public boolean canAttack(EntityType entityType)
	{
		if(canBreak == false && breakWhitelist.contains(entityType)) return true;
		if(canBreak == true && breakBlacklist.contains(entityType)) return false;
		return canBreak;
	}
	final public boolean canAttack(Entity entity)
	{
		return this.canAttack(entity.getType());
	}
	
	final public boolean isHidden(){return isHidden;}
	final public String getTag(){return tag;}
	
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
	
	// Information get
	final public GameMode getGameMode(){return mode;}  // Gamemode handling will be by PlayerState, this will be on enterStates
	final public List<Material> getBreakWhiteList(){return breakWhitelist;}
	final public List<Material> getBreakBlackList(){return breakBlacklist;}
	
	final public List<EntityType> getAttackWhitelist(){return attackWhitelist;}
	final public List<EntityType> getAttackBlacklist(){return attackBlacklist;}
	
	
	// Set/adjust allowances
	final public void setCanBreak(boolean bool){canBreak = bool;} // If this is false, then white or black lists don't matter.
	final public void setBreakWhiteList(List<Material> materials){breakWhitelist = materials;}
	final public void setBreakBlackList(List<Material> materials){breakBlacklist = materials;}
	
	final public void setGamemode(GameMode gameMode){mode = gameMode;}
	
//combat allowances sets
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
	
	final public void equipPlayer(ArenaPlayer player)
	{
		for(int i = 0; i < effects.size(); i++)
		{
			player.getPlayer().addPotionEffect(effects.get(i));
		}
	}
	final public void removeAllEffects(ArenaPlayer player)
	{
		for(PotionEffect effect : player.getPlayer().getActivePotionEffects())
		{
			player.getPlayer().removePotionEffect(effect.getType());
		}
	}

	public void enterState()
	{
	}
}
