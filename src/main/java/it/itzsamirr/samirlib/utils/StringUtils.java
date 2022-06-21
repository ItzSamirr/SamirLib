package it.itzsamirr.samirlib.utils;

import lombok.experimental.UtilityClass;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/
@UtilityClass
public class StringUtils {
    public boolean isAlphabetical(String s){
        return s.matches("[a-zA-Z]+");
    }

    public String reverse(String s){
        StringBuilder sb = new StringBuilder();
        for(int i = s.toCharArray().length;i>0;i--){
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public String construct(String[] array, int from ,int to){
        StringBuilder buffer = new StringBuilder();
        if(to > array.length) to = array.length;
        for (int i = from; i < to; i++) {
            buffer.append(array[i]);
            if(i != to-1) buffer.append(" ");
        }
        return buffer.toString();
    }
}
