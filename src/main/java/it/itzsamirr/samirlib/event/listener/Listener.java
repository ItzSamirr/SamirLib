package it.itzsamirr.samirlib.event.listener;

import it.itzsamirr.samirlib.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

/**
 * @author ItzSamirr
 * Created at 22.06.2022
 **/

public interface Listener<T extends JavaPlugin> {
    T getPlugin();
}
