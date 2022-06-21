package it.itzsamirr.samirlib.player;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/
public class PlayerManager {
    private List<PlayerWrapper<?>> wrappers = new ArrayList<>();

    @SuppressWarnings({"unchecked cast", "OptionalGetWithoutIsPresent"})
    public <T extends JavaPlugin> PlayerWrapper<T> wrap(Player p, T plugin){
        PlayerWrapper<T> wrapper;
        if(wrappers.stream().noneMatch(wp -> wp.getUUID().equals(p.getUniqueId()) && wp.getPlugin().getClass().isAssignableFrom(plugin.getClass()))){
            wrapper = new PlayerWrapper<>(plugin, p);
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
}
