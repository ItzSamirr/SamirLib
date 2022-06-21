package it.itzsamirr.samirlib.utils;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 18.06.2022
 **/
public final class SamirLogger<T extends JavaPlugin> {
    private final T plugin;
    private String prefix;

    public SamirLogger(T plugin, String prefix){
        this.plugin = plugin;
        this.prefix = prefix;
    }

    public void info(String msg){
        plugin.getServer().getConsoleSender().sendMessage(Color.translate(prefix + " " + msg));
    }

    public void warn(String msg){
        plugin.getServer().getConsoleSender().sendMessage(Color.translate("&e"  + prefix + " " + msg));
    }

    public void error(String msg){
        plugin.getServer().getConsoleSender().sendMessage(Color.translate("&c"  + prefix + " " + msg));
    }

    public void debug(String msg){
        plugin.getServer().getConsoleSender().sendMessage(Color.translate(prefix + " &7[Debug] " + msg));
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
