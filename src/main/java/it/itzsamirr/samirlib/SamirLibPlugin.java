package it.itzsamirr.samirlib;

import it.itzsamirr.samirlib.event.listener.Listener;
import it.itzsamirr.samirlib.plugin.PluginInfo;
import it.itzsamirr.samirlib.plugin.SamirPlugin;
import it.itzsamirr.samirlib.utils.Timer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 */
@PluginInfo(prefix = "&7[&bSamirLib&7] ")
public final class SamirLibPlugin extends SamirPlugin {

    @Override
    public void enable() {
        Timer timer = new Timer();
        SamirLib.getInstance().onEnable();
        customLogger.info("&aEnabled in " + timer.getPassedTime() + " ms");
    }

    @Override
    public void disable() {
        SamirLib.getInstance().onDisable();
    }
}
