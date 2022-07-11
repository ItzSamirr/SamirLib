package it.itzsamirr.samirlib;


import it.itzsamirr.samirlib.command.CommandManager;
import it.itzsamirr.samirlib.configuration.json.JsonConfigManager;
import it.itzsamirr.samirlib.event.AsyncTickEvent;
import it.itzsamirr.samirlib.event.EventManager;
import it.itzsamirr.samirlib.event.TickEvent;
import it.itzsamirr.samirlib.menu.MenuManager;
import it.itzsamirr.samirlib.player.PlayerWrapperManager;
import it.itzsamirr.samirlib.utils.SamirLogger;
import it.itzsamirr.samirlib.utils.UpdateChecker;
import org.bukkit.Bukkit;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 */
public final class SamirLib {
    private static SamirLib instance;
    private final SamirLibPlugin plugin;
    private CommandManager commandManager;
    private PlayerWrapperManager playerManager;
    private MenuManager menuManager;
    private EventManager eventManager;
    private AsyncTickEvent asyncTickEvent;
    private JsonConfigManager jsonConfigManager;
    private TickEvent tickEvent;

    private SamirLib(SamirLibPlugin plugin)
    {
        this.plugin = plugin;
    }

    void onEnable(){
        this.commandManager = new CommandManager();
        this.playerManager = new PlayerWrapperManager();
        this.menuManager = new MenuManager();
        this.eventManager = new EventManager();
        this.jsonConfigManager = new JsonConfigManager();
        this.asyncTickEvent = new AsyncTickEvent();
        this.tickEvent = new TickEvent();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(!tickEvent.isCancelled())this.eventManager.call(tickEvent);
        }, 0, 1);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
          if(!asyncTickEvent.isCancelled())  this.eventManager.call(asyncTickEvent);
        }, 0, 1);
    }

    void onDisable(){
        instance = null;
        jsonConfigManager.saveAll();
        commandManager.unregisterAll();
        eventManager.unregisterAll();
        playerManager.unregisterAll();
        jsonConfigManager.unregisterAll();
        this.tickEvent = null;
        this.playerManager = null;
        this.menuManager = null;
        this.eventManager = null;
        this.asyncTickEvent = null;
        this.commandManager = null;
        this.jsonConfigManager = null;
    }

    public PlayerWrapperManager getPlayerManager() {
        return playerManager;
    }

    public SamirLibPlugin getPlugin() {
        return plugin;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public JsonConfigManager getJsonConfigManager() {
        return jsonConfigManager;
    }

    public static SamirLib getInstance() {
        if(instance == null) instance = new SamirLib(SamirLibPlugin.getPlugin(SamirLibPlugin.class));
        return instance;
    }
}
