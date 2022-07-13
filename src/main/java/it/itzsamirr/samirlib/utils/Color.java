package it.itzsamirr.samirlib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/
public final class Color {
    private Color(){
        throw new UnsupportedOperationException();
    }
    public static String translate(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> translate(List<String> list){
        list.replaceAll(Color::translate);
        return list;
    }
}
