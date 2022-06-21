package it.itzsamirr.samirlib.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 **/
@UtilityClass
public class ServerUtils {
    public double[] getTPS(){
        Object mcServer =  getMinecraftServer();
        Validate.notNull(mcServer, "Server is null");
        Field f = ReflectionUtils.getField(mcServer.getClass(), "recentTps");
        double[] tps;
        try {
            boolean b = f.isAccessible();
            f.setAccessible(true);
            tps = (double[]) f.get(mcServer);
            f.setAccessible(b);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            tps = new double[0];
        }
        return tps;
    }

    public double getAverageTPS(){
        return MathUtils.avg(getTPS());
    }

    public Object getMinecraftServer(){
        Method m = ReflectionUtils.getMethod("CraftServer", "getServer");
        try {
            return m.invoke(Bukkit.getServer());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
