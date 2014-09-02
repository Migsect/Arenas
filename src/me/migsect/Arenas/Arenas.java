package me.migsect.Arenas;

import java.util.logging.Logger;

import me.migsect.Arenas.Commands.CommandGM;
import me.migsect.Arenas.Commands.CommandHandler;
import me.migsect.Arenas.Commands.CommandCommands;
import me.migsect.Arenas.Commands.CommandHelp;
import me.migsect.Arenas.Commands.CommandInfo;
import me.migsect.Arenas.Commands.CommandList;
import me.migsect.Arenas.Commands.CommandLoadGame;
import me.migsect.Arenas.Commands.CommandLoadMap;
import me.migsect.Arenas.Commands.CommandMapTeleport;
import me.migsect.Arenas.Commands.CommandShowState;
import me.migsect.Arenas.Commands.CommandStart;
import me.migsect.Arenas.Commands.CommandStop;
import me.migsect.Arenas.Commands.CommandUnloadGame;
import me.migsect.Arenas.Commands.CommandUnloadMap;
import me.migsect.Arenas.Events.BlockListener;
import me.migsect.Arenas.Events.CombatListener;
import me.migsect.Arenas.Events.ItemListener;
import me.migsect.Arenas.Events.LoginListener;
import me.migsect.Arenas.Events.MovementListener;
import me.migsect.Arenas.GameTypes.GameHandler;
import me.migsect.Arenas.GameTypes.EnderSpawn.GameEnderSpawn;
import me.migsect.Arenas.GameTypes.Golemnaut.GameGolemnaut;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Arenas extends JavaPlugin
{
	public final Logger logger = Logger.getLogger("Minecraft");
	public GameHandler gameHandler;
	public CommandHandler commandHandler;
	public ConfigAccessor mapConfig;
	
	
	// Enabling
	@Override
	public void onEnable()
	{
		// Server Log Message
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " Version " + pdf.getVersion() + " has been enabled.");
	
		// Listener Handling
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new CombatListener(this), this);
		pm.registerEvents(new MovementListener(this), this);
		pm.registerEvents(new LoginListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new ItemListener(this), this);
		
		// Config Handling
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		mapConfig  = new ConfigAccessor(this,"maps.yml");
		mapConfig.getConfig().options().copyDefaults(true);
		mapConfig.saveConfig();
		
		// Command handling
		this.registerCommands();
		
		// Setting up the game Handler
		gameHandler = new GameHandler(this);
		gameHandler.registerGame(new GameEnderSpawn(this));
		gameHandler.registerGame(new GameGolemnaut(this));
		
		// Registering all maps.
		gameHandler.getMapHandler().registerMaps();
		
		// Add all players in case of a reload
		gameHandler.registerAll();
	}
	
	// Disabling
	@Override
	public void onDisable()
	{
		// Server Log Message
		PluginDescriptionFile pdf = this.getDescription();
		this.logger.info(pdf.getName() + " has been disabled");
		
		// Remove all players in case of a reload
		gameHandler.deregisterAll();
	}
	
	private void registerCommands()
	{
		commandHandler = new CommandHandler(this);
		getCommand("a").setExecutor(commandHandler);
		
		commandHandler.register(new CommandList(this));
		commandHandler.register(new CommandCommands(this));
		commandHandler.register(new CommandLoadGame(this));
		commandHandler.register(new CommandInfo(this));
		commandHandler.register(new CommandUnloadGame(this));
		commandHandler.register(new CommandLoadMap(this));
		commandHandler.register(new CommandUnloadMap(this));
		commandHandler.register(new CommandGM(this));
		commandHandler.register(new CommandStart(this));
		commandHandler.register(new CommandStop(this));
		commandHandler.register(new CommandShowState(this));
		commandHandler.register(new CommandMapTeleport(this));
		commandHandler.register(new CommandHelp(this));
	}


}


