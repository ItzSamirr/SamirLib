package it.itzsamirr.samirlib.utils;

import it.itzsamirr.samirlib.configuration.json.IConfigurationObject;
import it.itzsamirr.samirlib.configuration.json.annotations.JsonPath;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author ItzSamirr
 * Created at 11.07.2022
 **/
public final class LazyLocation implements IConfigurationObject {
    @JsonPath(name = "world")
    private String world;
    @JsonPath(name = "x")
    private double x;
    @JsonPath(name = "y")
    private double y;
    @JsonPath(name = "z")
    private double z;
    @JsonPath(name = "yaw")
    private float yaw;
    @JsonPath(name = "pitch")
    private float pitch;

    public LazyLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public LazyLocation(String world, double x, double y, double z) {
        this(world, x, y, z, 0f, 0f);
    }

    public Location toBukkitLocation(){
        return new Location(getBukkitWorld(), x, y, z, yaw, pitch);
    }

    public boolean isValid(){
        return Bukkit.getWorld(world) != null;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof LazyLocation)) return false;
        if(obj == this) return true;
        LazyLocation other = (LazyLocation) obj;
        return isValid() && other.isValid() && other.world.equals(world) && other.x == x && other.y == y && other.z == z && other.yaw == yaw && other.pitch == pitch;
    }

    public boolean isSimilar(LazyLocation other){
        return isValid() && other.isValid() && toBukkitLocation().distanceSquared(other.toBukkitLocation()) != .0 && yaw == other.yaw && pitch == other.pitch && world.equals(other.world);
    }

    public World getBukkitWorld(){
        return Bukkit.getWorld(world);
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
