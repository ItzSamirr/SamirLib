package it.itzsamirr.samirlib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/
@UtilityClass
public class Color {
    public String translate(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public List<String> translate(List<String> list){
        list.replaceAll(Color::translate);
        return list;
    }
}
