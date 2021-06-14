package me.derpy.core.gui;

import me.derpy.core.gui.visual.GuiPage;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class DerpCoreHolder implements InventoryHolder {
    private GuiPage page;

    public DerpCoreHolder(GuiPage guiPage){
        this.page = guiPage;
    }

    @Override
    public Inventory getInventory() {
        return this.page.getInventory();
    }

    public GuiPage getPage(){
        return this.page;
    }
}
