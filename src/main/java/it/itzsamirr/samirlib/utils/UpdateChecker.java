package it.itzsamirr.samirlib.utils;

import it.itzsamirr.samirlib.plugin.SamirPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * @author ItzSamirr
 * Created at 26.06.2022
 **/

public class UpdateChecker<T extends SamirPlugin> {
    private final int id;
    private final T plugin;

    public UpdateChecker(int id, T plugin) {
        this.id = id;
        this.plugin = plugin;
    }

    public void check(Consumer<String> consumer){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try(InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id).openStream(); Scanner sc = new Scanner(is)){
                if(sc.hasNext()){
                    Bukkit.getScheduler().runTask(plugin, () -> consumer.accept(sc.next()));
                }
            } catch (Exception e) {
                plugin.getCustomLogger().error("&cError while checking for updates: " + e.getMessage());
            }
        });
    }

    public void check(Consumer<String> consumer, long period){
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> check(consumer), 0L, period);
    }

    public int getId() {
        return id;
    }

    public T getPlugin() {
        return plugin;
    }
}
