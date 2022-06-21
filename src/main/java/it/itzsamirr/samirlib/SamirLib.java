package it.itzsamirr.samirlib;


import it.itzsamirr.samirlib.command.CommandManager;
import it.itzsamirr.samirlib.menu.MenuManager;
import it.itzsamirr.samirlib.player.PlayerManager;
import it.itzsamirr.samirlib.utils.SamirLogger;

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
    private SamirLogger<SamirLibPlugin> logger;

    private SamirLib(SamirLibPlugin plugin)
    {
        this.plugin = plugin;
    }

    void onEnable(){
        this.logger = new SamirLogger<>(plugin, "&7[&bSamirLib&7]");
        this.commandManager = new CommandManager(plugin);
        this.playerManager = new PlayerManager();
        this.menuManager = new MenuManager();
    }

    void onDisable(){
        instance = null;
        commandManager.unregisterAll();
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

    public static SamirLib getInstance() {
        if(instance == null) instance = new SamirLib(SamirLibPlugin.getPlugin(SamirLibPlugin.class));
        return instance;
    }
}
