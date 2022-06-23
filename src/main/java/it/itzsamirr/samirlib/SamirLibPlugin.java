package it.itzsamirr.samirlib;

import it.itzsamirr.samirlib.event.listener.Listener;
import it.itzsamirr.samirlib.utils.Timer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 */
public final class SamirLibPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Timer timer = new Timer();
        SamirLib.getInstance().onEnable();
        SamirLib.getInstance().getLogger().info("&aEnabled in " + timer.getPassedTime() + " ms");
    }

    @Override
    public void onDisable() {
        SamirLib.getInstance().onDisable();
    }
}
