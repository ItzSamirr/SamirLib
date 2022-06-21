package it.itzsamirr.samirlib.utils;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

/**
* @author ItzSamirr
* Created at 07.06.2022
**/
@UtilityClass
public class NumberUtils {
    public boolean isNumber(String s){
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException ignored){
            return false;
        }
        return true;
    }

    public double genRandomDouble(double min, double max){
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public double genRandomDouble(double min){
        return genRandomDouble(min, Double.MAX_VALUE);
    }

    public int genRandomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public int genRandomInt(int min){
        return genRandomInt(min, Integer.MAX_VALUE);
    }
}
