package it.itzsamirr.samirlib.player;

import it.itzsamirr.samirlib.SamirLib;
import it.itzsamirr.samirlib.event.player.PlayerWrapEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/
public class PlayerWrapperManager {
    private List<PlayerWrapper<?>> wrappers = new ArrayList<>();

    @SuppressWarnings({"unchecked cast", "OptionalGetWithoutIsPresent"})
    public <T extends JavaPlugin> PlayerWrapper<T> wrap(Player p, T plugin){
        PlayerWrapper<T> wrapper;
        if(wrappers.stream().noneMatch(wp -> wp.getUUID().equals(p.getUniqueId()) && wp.getPlugin().getClass().isAssignableFrom(plugin.getClass()))){
            wrapper = new PlayerWrapper<>(plugin, p);
            PlayerWrapEvent<T> wrapEvent = new PlayerWrapEvent<>(wrapper);
            SamirLib.getInstance().getEventManager().call(wrapEvent);
            if(wrapEvent.isCancelled()) return null;
            wrappers.add(wrapper);
        }else{
            wrapper = wrappers.stream()
                    .filter(wp -> wp.getUUID().equals(p.getUniqueId()))
                    .filter(wp -> wp.getPlugin().getClass().isAssignableFrom(plugin.getClass()))
                    .map(wp -> (PlayerWrapper<T>)wp)
                    .findFirst()
                    .get();
        }
        return wrapper;
    }

    public void unregisterAll(){
        wrappers.clear();
    }
}
