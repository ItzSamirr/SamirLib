package it.itzsamirr.samirlib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 **/
@UtilityClass
public class ItemUtils {
    public Material getMaterial(String name){
        return Material.getMaterial(name.toUpperCase().replace(" ", "_"));
    }

    public Material getMaterial(String name, String or){
        Material mat = getMaterial(name);
        if(mat == null){
            Material other = getMaterial(or);
            if(other == null) return Material.REDSTONE_BLOCK;
            return other;
        }
        return mat;
    }
}
