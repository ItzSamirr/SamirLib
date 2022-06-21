package it.itzsamirr.samirlib.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 **/
@UtilityClass
public class MathUtils {
    public double avg(double... doubles){
        double sum = 0;
        for (int i = 0; i < doubles.length; i++) {
            sum += doubles[i];
        }
        return sum/doubles.length;
    }

    public int avg(int... ints){
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        return Math.toIntExact(Math.round(sum / (double) ints.length));
    }

    public float avg(float... floats){
        float sum = 0;
        for (int i = 0; i < floats.length; i++) {
            sum += floats[i];
        }
        return sum/floats.length;
    }

    public float clamp(float value, float min, float max){
        return Math.max(min, Math.min(max, value));
    }

    public int clamp(int value, int min, int max){
        return Math.max(min, Math.min(max, value));
    }

    public double clamp(double value, double min, double max){
        return Math.max(min, Math.min(max, value));
    }

    public float lerp(float a, float b, float f){
        return a + (b - a) * f;
    }

    public double lerp(double a, double b, double f){
        return a + (b - a) * f;
    }

    public float smoothstep(float a, float b, float f){
        f = clamp((f - a) / (b - a), 0, 1);
        return f * f * (3 - 2 * f);
    }

    public float rsqrt(float f){
        return 1 / (float) Math.sqrt(f);
    }

    public double rsqrt(double f){
        return 1 / Math.sqrt(f);
    }

    public float rsqrtF(float f){
        rsqrtF(f, 2);
        return f;
    }

    public float rsqrtF(float f, int iterations){
        float xhalf = 0.5f * f;
        int i = Float.floatToIntBits(f);
        i = 0x5f3759df - (i >> 1);
        f = Float.intBitsToFloat(i);
        for (int j = 0; j < iterations; j++) {
            f = f * (1.5f - xhalf * f * f);
        }
        return f;
    }

    public float[] lerp(float[] a, float[] b, float f){
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = lerp(a[i], b[i], f);
        }
        return result;
    }

    public double[] lerp(double[] a, double[] b, double f){
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = lerp(a[i], b[i], f);
        }
        return result;
    }
}
