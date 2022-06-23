package it.itzsamirr.samirlib.player;

import it.itzsamirr.samirlib.utils.Color;
import it.itzsamirr.samirlib.utils.ReflectionUtils;
import it.itzsamirr.samirlib.utils.ServerVersion;
import lombok.Data;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/

public class PlayerWrapper<T extends JavaPlugin> {
    protected Player player;
    protected T plugin;

    protected PlayerWrapper(T plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public Player getPlayer() {
        return player;//a
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public T getPlugin() {
        return plugin;
    }

    public void setPlugin(T plugin) {
        this.plugin = plugin;
    }

    public int getPing(){
        if(ServerVersion.getServerVersion().isBelow(ServerVersion.v_16)) {
            Field f = ReflectionUtils.getField("{nms}entity.EntityPlayer", "ping");
            boolean b = f.isAccessible();
            int ping;
            f.setAccessible(true);
            try {
                ping = f.getInt(ReflectionUtils.getMethod("CraftPlayer", "getHandle").invoke(player));
            } catch (IllegalAccessException | InvocationTargetException e) {
                ping = -1;
            }
            f.setAccessible(b);
            return ping;
        }else{
            try {
                return (int) ReflectionUtils.getMethod("Player", "getPing").invoke(player);
            } catch (IllegalAccessException | InvocationTargetException e) {
                return -1;
            }
        }
    }

    public String getDisplayName(){
        return player.getDisplayName();
    }

    public void sendMessage(Object... msg){
        for (int i = 0; i < msg.length; i++) {
            player.sendMessage(Color.translate(String.valueOf(msg[i])));
        }
    }

    public UUID getUUID(){
        return player.getUniqueId();
    }

    public String getName(){
        return player.getName();
    }

    public void sendMessage(BaseComponent... comp){
        player.spigot().sendMessage(comp);
    }
}
