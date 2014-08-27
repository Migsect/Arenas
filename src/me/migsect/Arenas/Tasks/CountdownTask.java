package me.migsect.Arenas.Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.Players.ArenaPlayer;

import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable
{
	// This task is purely cosmetic.  All it will do is display to players a countdown
	// and is in no other means a neccessary feature besides this. 
	Arenas plugin;
	
	private String type;
	private int remSecs;
	private List<ArenaPlayer> receivers = new ArrayList<ArenaPlayer>();
	private HashMap<Integer,String> messages = new HashMap<Integer,String>();
	// Each section MUST have a single string, else it won't work.
	//   If we need to have more than one string, we'll add support. Otherwise
	//   it's not worth our time.
	
	public CountdownTask(int secs, String type)
	{
		remSecs = secs;
		this.type = type;
	}
	
	public void addMessage(int atSec, String message)
	{
		messages.put(atSec, message);
	}
	
	public void addReciever(ArenaPlayer player)
	{
		receivers.add(player);
	}
	public void addAllRecivers(List<ArenaPlayer> players)
	{
		receivers.addAll(players);
	}
	// set Recievers will not use the new list and instead can use a dynamically changing list
	//   such as the gamehandlr's player list.
	public void setRecievers(List<ArenaPlayer> players)
	{
		receivers = players;
	}
	
	public int getRemSecs()
	{
		return remSecs;
	}
	public String getType()
	{
		return type;
	}
	
	@Override
	public void run()
	{
		if(messages.containsKey(remSecs)) messageReceivers(messages.get(remSecs));
		remSecs--;
		if(remSecs == 0) this.cancel();
	}
	
	private void messageReceivers(String message)
	{
		for(int i = 0; i < receivers.size(); i++)
		{
			receivers.get(i).getPlayer().sendMessage(message);
		}
	}
}
