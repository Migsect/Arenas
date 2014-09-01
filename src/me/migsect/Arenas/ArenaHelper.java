package me.migsect.Arenas;

import java.util.List;

import me.migsect.Arenas.GameTypes.ArenaTeam;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class ArenaHelper
{
	static public void messageWorld(World world, String string)
	{
		List<Player> players = world.getPlayers();
		for (int i = 0;i < players.size(); i++)
		{
			players.get(i).sendMessage(string);
		}
	}
	static public void messageTeam(ArenaTeam arenaTeam, String string)
	{
		
	}
	
	// This method will replace all the color codes in minecraft with
	// their respective codes.
	static public String colorEncoding(String string)
	{
		String newString = string;
		
		newString = newString.replace("&0", "" + ChatColor.BLACK);
		newString = newString.replace("&1", "" + ChatColor.DARK_BLUE);
		newString = newString.replace("&2", "" + ChatColor.DARK_GREEN);
		newString = newString.replace("&3", "" + ChatColor.DARK_AQUA);
		newString = newString.replace("&4", "" + ChatColor.DARK_RED);
		newString = newString.replace("&5", "" + ChatColor.DARK_PURPLE);
		newString = newString.replace("&6", "" + ChatColor.GOLD);
		newString = newString.replace("&7", "" + ChatColor.GRAY);
		newString = newString.replace("&8", "" + ChatColor.DARK_GRAY);
		newString = newString.replace("&9", "" + ChatColor.BLUE);
		newString = newString.replace("&a", "" + ChatColor.GREEN);
		newString = newString.replace("&b", "" + ChatColor.AQUA);
		newString = newString.replace("&c", "" + ChatColor.RED);
		newString = newString.replace("&d", "" + ChatColor.LIGHT_PURPLE);
		newString = newString.replace("&e", "" + ChatColor.YELLOW);
		newString = newString.replace("&f", "" + ChatColor.WHITE);
		
		newString = newString.replace("&k", "" + ChatColor.MAGIC);
		newString = newString.replace("&l", "" + ChatColor.BOLD);
		newString = newString.replace("&m", "" + ChatColor.STRIKETHROUGH);
		newString = newString.replace("&n", "" + ChatColor.UNDERLINE);
		newString = newString.replace("&o", "" + ChatColor.ITALIC);
		newString = newString.replace("&p", "" + ChatColor.RESET);
		
		return newString;
	}
	
	static public double reduceDamage(Player player, double damage)
	{
		PlayerInventory inv = player.getInventory();
	    ItemStack boots = inv.getBoots();
	    ItemStack helmet = inv.getHelmet();
	    ItemStack chest = inv.getChestplate();
	    ItemStack pants = inv.getLeggings();
		double red = 0;
		
		if(helmet.getType() == Material.LEATHER_HELMET) red = red + 0.04;
	    else if(helmet.getType() == Material.GOLD_HELMET) red = red + 0.08;
	    else if(helmet.getType() == Material.CHAINMAIL_HELMET) red = red + 0.08;
	    else if(helmet.getType() == Material.IRON_HELMET) red = red + 0.08;
	    else if(helmet.getType() == Material.DIAMOND_HELMET) red = red + 0.12;
	    //
	    if(boots.getType() == Material.LEATHER_BOOTS) red = red + 0.04;
	    else if(boots.getType() == Material.GOLD_BOOTS) red = red + 0.04;
	    else if(boots.getType() == Material.CHAINMAIL_BOOTS) red = red + 0.04;
	    else if(boots.getType() == Material.IRON_BOOTS) red = red + 0.08;
	    else if(boots.getType() == Material.DIAMOND_BOOTS) red = red + 0.12;
	    //
	    if(pants.getType() == Material.LEATHER_LEGGINGS) red = red + 0.08;
	    else if(pants.getType() == Material.GOLD_LEGGINGS) red = red + 0.12;
	    else if(pants.getType() == Material.CHAINMAIL_LEGGINGS) red = red + 0.16;
	    else if(pants.getType() == Material.IRON_LEGGINGS) red = red + 0.20;
	    else if(pants.getType() == Material.DIAMOND_LEGGINGS) red = red + 0.24;
	    //
	    if(chest.getType() == Material.LEATHER_CHESTPLATE) red = red + 0.12;
	    else if(chest.getType() == Material.GOLD_CHESTPLATE) red = red + 0.20;
	    else if(chest.getType() == Material.CHAINMAIL_CHESTPLATE) red = red + 0.20;
	    else if(chest.getType() == Material.IRON_CHESTPLATE) red = red + 0.24;
	    else if(chest.getType() == Material.DIAMOND_CHESTPLATE) red = red + 0.32;
		
		return damage * (1 - red);
	}
	static public void emptyPlayer(ArenaPlayer gamePlayer)
	{
		gamePlayer.getPlayer().getInventory().clear();
		gamePlayer.getPlayer().getInventory().setHelmet(null);
		gamePlayer.getPlayer().getInventory().setBoots(null);
		gamePlayer.getPlayer().getInventory().setChestplate(null);
		gamePlayer.getPlayer().getInventory().setLeggings(null);
		
		for(PotionEffect effect : gamePlayer.getPlayer().getActivePotionEffects())
		{
			gamePlayer.getPlayer().getPlayer().removePotionEffect(effect.getType());
		}
	}
}
