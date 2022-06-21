package it.itzsamirr.samirlib.menu;

import it.itzsamirr.samirlib.SamirLib;
import it.itzsamirr.samirlib.player.PlayerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ItzSamirr
 * Created at 18.06.2022
 **/
public class MenuListener<T extends JavaPlugin> implements Listener {
    private SamirLib lib;
    private T plugin;

    public MenuListener(T plugin) {
        this.plugin = plugin;
        this.lib = SamirLib.getInstance();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)) return;
        PlayerWrapper<T> wrapper = lib.getPlayerManager().wrap((Player)e.getWhoClicked(), plugin);
        if(e.getView().getTopInventory().getHolder() == null) return;
        if(e.getView().getTopInventory().getHolder() instanceof Menu){
            Menu menu = (Menu)e.getClickedInventory().getHolder();
            if(!menu.getSlotClickFilter().test(e.getSlot())) return;
            if(menu.cancelAllInteractions()){
                e.setCancelled(true);
            }
            menu.onClick0(e);
        }
    }
}
