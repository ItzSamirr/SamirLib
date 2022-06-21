package it.itzsamirr.samirlib.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ItzSamirr
 * Created at 19.06.2022
 **/

@Deprecated
//TODO: fix
public abstract class PaginatedMenu<T extends JavaPlugin> extends Menu<T>{
    protected int page = 1;


    public PaginatedMenu(T plugin, PlayerMenuData data) {
        super(plugin, data);
    }

    public int getMaxPages(){
        List<ItemStack> filtered = new ArrayList<>(Stream.of(getInventory().getContents()).filter(Objects::isNull).collect(Collectors.toList()));
        filtered.addAll(Stream.of(getInventory().getStorageContents()).filter(Objects::nonNull).filter(item -> !item.isSimilar(Menu.FILLER_GLASS)).collect(Collectors.toList()));
        return filtered.size();
    }
    
    public abstract int getPreviousButtonSlot();
    public abstract int getNextButtonSlot();

    @Override
    void onClick0(InventoryClickEvent event) {
        if(event.getSlot() == getPreviousButtonSlot()) {
            if (page > 1) page--;
            return;
        }
        if(event.getSlot() == getNextButtonSlot()) {
            if (page < getMaxPages()) page++;
            return;
        }
        super.onClick0(event);
    }
}
