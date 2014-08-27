package me.migsect.Arenas.Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.migsect.Arenas.Arenas;
import me.migsect.Arenas.GameTypes.ArenaGame;
import me.migsect.Arenas.Players.ArenaPlayer;

public class TaskHandler
{
	// The purpose of this class is to make easier handling of all tasks.
	//   For example, it allows grabbing of all currently running tasks.
	
	Arenas plugin;
	HashMap<ArenaPlayer, RespawnTask> respawnTasks = new HashMap<ArenaPlayer, RespawnTask>();
	List<TagTask> tagTasks = new ArrayList<TagTask>();
	List<CountdownTask> countdowns = new ArrayList<CountdownTask>();
	ClockTask clock;
	
	public TaskHandler(Arenas plugin)
	{
		this.plugin = plugin;
	}
	
	public void startGameClock(ArenaGame game)
	{
		ClockTask clockTask = new ClockTask(game);
		clockTask.runTaskTimer(plugin, 0, 1);
	}
	public void stopGameClock()
	{
		if(clock == null) return;
		clock.cancel();
		clock = null;
	}
	public int getGameTick()
	{
		return clock.getGameTick();
	}
	
	public void startCountdown(CountdownTask task)
	{
		task.runTaskTimer(plugin, 0, 20);
		countdowns.add(task);
	}
	public void stopCountdownType(String type)
	{
		for(int i = 0; i < countdowns.size(); i++)
		{
			if(countdowns.get(i).getType() == type)
			{
				countdowns.get(i).cancel();
				countdowns.remove(i);
			}
		}
	}
	public void stopCountdownAll()
	{
		for(int i = 0; i < countdowns.size(); i++)
		{
			countdowns.get(i).cancel();
			countdowns.remove(i);
		}
	}
	public List<CountdownTask> getCountdowns()
	{
		return countdowns;
	}
	
	public void startRespawn(ArenaGame game, ArenaPlayer player, int ticks)
	{
		RespawnTask task = new RespawnTask(game, player);
		respawnTasks.put(player, task);
		task.runTaskLater(plugin, ticks);
	}
	public void cancelRespawn(ArenaPlayer player)
	{
		RespawnTask task = respawnTasks.get(player);
		task.cancel();
		respawnTasks.remove(player);
	}
	public void cancelRespawnAll()
	{
		List<RespawnTask> tasks = new ArrayList<RespawnTask>(respawnTasks.values());
		for(int i = 0; i < tasks.size(); i++)
		{
			tasks.get(i).cancel();
			respawnTasks.clear();
		}
	}
	public void startTagTask(ArenaGame game, String tag, int ticks)
	{
		TagTask task = new TagTask(game, tag);
		tagTasks.add(task);
		task.runTaskLater(plugin, ticks);
	}
	public void startTagTask(ArenaGame game, String tag, List<ArenaPlayer> players, int ticks)
	{
		TagTask task = new TagTask(game, tag, players);
		tagTasks.add(task);
		task.runTaskLater(plugin, ticks);
	}
	public void cancelTagTasks(String tag)
	{
		for(int i = 0 ; i < tagTasks.size() ; i ++)
		{
			if(tagTasks.get(i).isTag(tag)) tagTasks.get(i).cancel();
			tagTasks.remove(i);
		}
	}
	public void cancelTagTasks()
	{
		for(int i = 0 ; i < tagTasks.size() ; i ++)
		{
			tagTasks.get(i).cancel();
			tagTasks.remove(i);
		}
	}
	// only to be used by the ArenaGame abstract class
	public void delayedStart(ArenaGame game, int seconds)
	{
		DelayedStartTask task = new DelayedStartTask(game);
		task.runTaskLater(plugin, seconds * 20);
	}
}
