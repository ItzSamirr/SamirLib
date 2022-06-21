package it.itzsamirr.samirlib.menu;

import it.itzsamirr.samirlib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Predicate;

/**
 * @author ItzSamirr
 * Created at 07.06.2022
 **/
public abstract class Menu<T extends JavaPlugin> implements InventoryHolder {
    protected Inventory inventory;
    protected PlayerMenuData playerMenuData;
    protected Player player;
    protected T plugin;

    public Menu(T plugin, PlayerMenuData data) {
        this.plugin = plugin;
        this.playerMenuData = data;
        this.player = playerMenuData.getOwner();
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerMenuData getPlayerMenuData() {
        return playerMenuData;
    }

    public static final ItemStack FILLER_GLASS = new ItemBuilder(Material.THIN_GLASS).name("").flag(ItemFlag.HIDE_ATTRIBUTES).build();

    public abstract String getName();

    public abstract int getSize();

    public int getClampedSize(){
        int size = Math.abs(getSize());
        if(size%9 != 0) size -= size%9;
        if(size == 0) size = 9;
        return size;
    }

    public T getPlugin() {
        return plugin;
    }

    protected abstract void setup();
    protected abstract void onClick(InventoryClickEvent event);

    protected void fill(ItemStack item){
        int size = getClampedSize();
        for (int i = 0; i < size; i++) {
            if(inventory.getItem(i) == null)inventory.setItem(i, item);
        }
    }

    void onClick0(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getHolder() == null) return;
        this.onClick(event);
    }

    public void open()
    {
        this.setup();
        this.playerMenuData.push(this);
    }

    public void fill(){
        fill(FILLER_GLASS);
    }

    public boolean cancelAllInteractions(){
        return true;
    }

    public Predicate<Integer> getSlotClickFilter(){
        return i -> false;
    }



}
