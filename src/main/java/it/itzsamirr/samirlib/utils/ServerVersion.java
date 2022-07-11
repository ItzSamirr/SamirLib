package it.itzsamirr.samirlib.utils;

import it.itzsamirr.samirlib.SamirLib;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

/**
 * @author ItzSamirr
 * Created at 18.06.2022
 **/
public enum ServerVersion {
    v1_7, v1_8, v1_10, v1_11, v_12, v_13, v_14, v_15, v_16, v_17, v_18, v_19, UNDEFINED;
    private static ServerVersion serverVersion;

    static{
        Server server = Bukkit.getServer();
        String version = String.valueOf(ReflectionUtils.getReleaseVersion());
        serverVersion = getServerVersion(version);
        if(serverVersion == UNDEFINED){
            SamirLib.getInstance().getPlugin().getCustomLogger().error("Unknown server version: " + version);
            for(Plugin plugin : server.getPluginManager().getPlugins()){
                if(plugin.getDescription().getDepend().contains("SamirLib")){
                    server.getPluginManager().disablePlugin(plugin);
                }
            }
            server.getPluginManager().disablePlugin(SamirLib.getInstance().getPlugin());
        }
    }

    public static ServerVersion getServerVersion(){
        return serverVersion;
    }

    public static ServerVersion getServerVersion(String version) {
        for (int i = 0; i < values().length; i++) {
            ServerVersion v = values()[i];
            if (v.toString().toLowerCase().substring(3).startsWith(version.toLowerCase())) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public boolean isAbove(ServerVersion version){
        return this.ordinal() > version.ordinal();
    }

    public boolean isBelow(ServerVersion version){
        return this.ordinal() < version.ordinal();
    }

    public boolean isEqual(ServerVersion version){
        return this.ordinal() == version.ordinal();
    }

    public boolean isAboveOrEqual(ServerVersion version){
        return this.ordinal() >= version.ordinal();
    }

    public boolean isBelowOrEqual(ServerVersion version){
        return this.ordinal() <= version.ordinal();
    }
}
