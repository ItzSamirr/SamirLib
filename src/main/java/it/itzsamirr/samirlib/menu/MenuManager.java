package it.itzsamirr.samirlib.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 **/
public final class MenuManager {
    private HashMap<Class<? extends JavaPlugin>, MenuListener<? extends JavaPlugin>> registerMap = new HashMap<>();

    public <T extends JavaPlugin> void register(T plugin){
        if(!registerMap.containsKey(plugin.getClass())){
            MenuListener<T> menuListener = new MenuListener<>(plugin);
            registerMap.put(plugin.getClass(), menuListener);
            plugin.getServer().getPluginManager().registerEvents(registerMap.get(plugin.getClass()), plugin);
        }
    }

    public <T extends JavaPlugin> void unregister(T plugin){
        MenuListener<T> menuListener = (MenuListener<T>) registerMap.get(plugin.getClass());
        if(menuListener != null){
            HandlerList.unregisterAll(menuListener);
            registerMap.remove(plugin.getClass());
        }
    }

}
