package it.itzsamirr.samirlib.command;

import it.itzsamirr.samirlib.SamirLibPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/
public class CommandManager {
    private HashMap<Class<? extends AbstractCommand<?>>, AbstractCommand<?>> commands = new HashMap<>();
    protected final SamirLibPlugin plugin;

    public CommandManager(SamirLibPlugin plugin) {
        this.plugin = plugin;
    }

    public <T extends JavaPlugin> void register(T plugin, Class<? extends AbstractCommand<T>>... commands){
        for (int i = 0; i < commands.length; i++) {
            Class<? extends AbstractCommand<T>> clazz = commands[i];
            try {
                AbstractCommand<T> cmd = clazz.getDeclaredConstructor(plugin.getClass()).newInstance(plugin);
                plugin.getCommand(cmd.info.name().toLowerCase()).setExecutor(cmd);
                this.commands.put(clazz, cmd);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRegistered(Class<? extends AbstractCommand<?>> clazz){
        return commands.containsKey(clazz);
    }

    public void unregister(Class<? extends AbstractCommand<?>> clazz){
        if(commands.containsKey(clazz)){
            AbstractCommand<?> cmd = commands.get(clazz);
            cmd.plugin.getCommand(cmd.getInfo().name().toLowerCase()).setExecutor(null);
            commands.remove(clazz);
        }
    }

    public void unregisterAll(){
        commands.keySet().forEach(this::unregister);
    }
}
