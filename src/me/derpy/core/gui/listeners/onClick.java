package me.derpy.core.gui.listeners;

import me.derpy.core.gui.DerpCoreHolder;
import me.derpy.core.gui.interfaces.IExecutableButton;
import me.derpy.core.gui.interfaces.IGuiButton;
import me.derpy.core.gui.interfaces.IPageButton;
import me.derpy.core.gui.visual.GuiPage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class onClick implements Listener {
    @EventHandler
    public void onClickEvent(InventoryClickEvent e){
        if(e.getClickedInventory().getHolder() instanceof DerpCoreHolder && e.getCurrentItem()!=null){
            e.setCancelled(true);
            GuiPage page = ((DerpCoreHolder)e.getClickedInventory().getHolder()).getPage();
            if(page.getGuiButtons().containsKey(e.getSlot())){
                IGuiButton button = page.getGuiButtons().get(e.getSlot());
                if(button instanceof IExecutableButton){
                    ((IExecutableButton) button).activateButton();
                }else if(button instanceof IPageButton){
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().openInventory(((IPageButton) button).getPage().getInventory());
                }
            }
        }
    }
}
