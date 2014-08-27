package me.migsect.Arenas.Players;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class ArenaPlayerLoadout
{
	private String name;
	private String description = "";
	private String tag;
	private Material menuMat;
	
	private ItemStack head = null;
	private ItemStack chest = null;
	private ItemStack legs = null;
	private ItemStack boots = null;
	private List<ItemStack> inventory = new ArrayList<ItemStack>();
	private List<PotionEffect> effects = new ArrayList<PotionEffect>();
	private int levels = 0;
	private int experience = 0;
	
	public ArenaPlayerLoadout(String name, String tag, Material menuMat)
	{
		this.name = name;
		this.tag = tag;
		this.menuMat = menuMat;
	}
	
	// equipPlayer will mostly be called when the player spawns.
	public void equipPlayer(ArenaPlayer player)
	{
		player.getPlayer().setLevel(levels);
		player.getPlayer().setExp(experience);
		
		if(head != null) player.getPlayer().getInventory().setHelmet(head);
		if(chest != null) player.getPlayer().getInventory().setChestplate(chest);
		if(legs != null) player.getPlayer().getInventory().setLeggings(legs);
		if(boots != null) player.getPlayer().getInventory().setBoots(boots);
		
		for(int i = 0; i < inventory.size(); i++)
		{
			player.getPlayer().getInventory().setItem(44 - i, inventory.get(i));
		}
		for(int i = 0; i < effects.size(); i++)
		{
			player.getPlayer().addPotionEffect(effects.get(i));
		}
	}
	public void giveEffects(ArenaPlayer player)
	{
		for(int i = 0; i < effects.size(); i++)
		{
			player.getPlayer().addPotionEffect(effects.get(i));
		}
	}
	
	public void setHelm(ItemStack item){head = item;}
	public void setChest(ItemStack item){chest = item;}
	public void setLegs(ItemStack item){legs = item;}
	public void setBoots(ItemStack item){boots = item;}
	public void addItem(ItemStack item){inventory.add(item);}
	public void setLevel(int level){this.levels = level;}
	public void setExp(int exp){this.experience = exp;}
	public void addEffect(PotionEffect effect){effects.add(effect);}
	public void setDescription(String string){description = string;}
	
	public String getTag(){return tag;}
	public String getName(){return name;}
	public String getDescription(){return description;}
	public Material getDisplayMaterial(){return menuMat;}
}
