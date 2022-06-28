package it.itzsamirr.samirlib;

import it.itzsamirr.samirlib.event.listener.Listener;
import it.itzsamirr.samirlib.plugin.PluginInfo;
import it.itzsamirr.samirlib.plugin.SamirPlugin;
import it.itzsamirr.samirlib.utils.Timer;
import it.itzsamirr.samirlib.utils.UpdateChecker;
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
        new UpdateChecker<>(102929, this).check(s -> {
            if(getConfig().getBoolean("check-for-updates")){
                if(s.equals(getDescription().getVersion())){
                    customLogger.info("&aYou are using the latest version of SamirLib!");
                }else{
                    customLogger.info("&aThere is a new version of SamirLib available! &7[&b" + s + "&7]&a at &bhttps://www.spigotmc.org/resources/samirlib.102929/");
                }
            }
        }, 600*1000);
        customLogger.info("&aEnabled in " + timer.getPassedTime() + " ms");
    }

    @Override
    public void disable() {
        SamirLib.getInstance().onDisable();
    }
}
