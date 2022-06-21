package it.itzsamirr.samirlib.utils;

/**
 * @author ItzSamirr
 * Created at 19.06.2022
 **/
public final class Timer {
    private long startTime;

    public Timer(long startTime) {
        this.startTime = startTime;
    }

    public Timer(){
        this(System.currentTimeMillis());
    }

    public long getPassedTime(){
        return System.currentTimeMillis() - startTime;
    }

    public boolean hasPassed(long time){
        return getPassedTime() >= time;
    }

    public boolean hasPassed(long time, boolean reset){
        if(hasPassed(time)){
            if(reset) reset();
            return true;
        }
        return false;
    }

    public void reset(){
        startTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

}
