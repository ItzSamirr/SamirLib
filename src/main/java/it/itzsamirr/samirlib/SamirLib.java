package it.itzsamirr.samirlib;


import it.itzsamirr.samirlib.command.CommandManager;
import it.itzsamirr.samirlib.event.AsyncTickEvent;
import it.itzsamirr.samirlib.event.EventManager;
import it.itzsamirr.samirlib.event.TickEvent;
import it.itzsamirr.samirlib.menu.MenuManager;
import it.itzsamirr.samirlib.player.PlayerManager;
import it.itzsamirr.samirlib.utils.SamirLogger;
import org.bukkit.Bukkit;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 */
public final class SamirLib {
    private static SamirLib instance;
    private final SamirLibPlugin plugin;
    private CommandManager commandManager;
    private PlayerManager playerManager;
    private MenuManager menuManager;
    private EventManager eventManager;
    private AsyncTickEvent asyncTickEvent;
    private TickEvent tickEvent;
    private SamirLogger<SamirLibPlugin> logger;

    private SamirLib(SamirLibPlugin plugin)
    {
        this.plugin = plugin;
    }

    void onEnable(){
        this.logger = new SamirLogger<>(plugin, "&7[&bSamirLib&7]");
        this.commandManager = new CommandManager();
        this.playerManager = new PlayerManager();
        this.menuManager = new MenuManager();
        this.eventManager = new EventManager();
        this.asyncTickEvent = new AsyncTickEvent();
        this.tickEvent = new TickEvent();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            this.eventManager.call(tickEvent);
        }, 0, 1);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> this.eventManager.call(asyncTickEvent), 0, 1);
    }

    void onDisable(){
        instance = null;
        commandManager.unregisterAll();
        Bukkit.getScheduler().cancelTasks(plugin);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public SamirLibPlugin getPlugin() {
        return plugin;
    }

    public SamirLogger<SamirLibPlugin> getLogger() {
        return logger;
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

    public static SamirLib getInstance() {
        if(instance == null) instance = new SamirLib(SamirLibPlugin.getPlugin(SamirLibPlugin.class));
        return instance;
    }
}
