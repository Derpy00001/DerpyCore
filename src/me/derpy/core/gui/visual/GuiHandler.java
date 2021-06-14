package me.derpy.core.gui.visual;

import me.derpy.core.DerpyCore;
import me.derpy.core.gui.listeners.onClick;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

public class GuiHandler {

    public GuiHandler(){
        Bukkit.getServer().getPluginManager().registerEvents(new onClick(), DerpyCore.getPlugin(DerpyCore.class));
        core.blankSlotItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getDyeData());
        ItemMeta meta = core.blankSlotItem.getItemMeta();
        meta.setDisplayName(" ");
        core.blankSlotItem.setItemMeta(meta);
    }

    public GuiPage createGui(String inventoryName, int inventoryRows, @Nullable InventoryHolder inventoryHolder){
        return new GuiPage(inventoryName, inventoryRows, inventoryHolder);
    }

    public static class core{
        static ItemStack blankSlotItem;
    }
}
