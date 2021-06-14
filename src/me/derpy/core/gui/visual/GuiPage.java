package me.derpy.core.gui.visual;

import me.derpy.core.gui.DerpCoreHolder;
import me.derpy.core.gui.interfaces.IExecutableButton;
import me.derpy.core.gui.interfaces.IGuiButton;
import me.derpy.core.gui.interfaces.IPageButton;
import me.derpy.core.gui.types.InventoryExecutable;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GuiPage {
    private Inventory inventory;
    private Map<Integer, IGuiButton> guiButtons;

    GuiPage(String inventoryName, int inventoryRows, @Nullable InventoryHolder inventoryHolder){
        if(inventoryRows<1 || inventoryRows>6){
            throw new IndexOutOfBoundsException("Too many rows inputted!\nMust have at least 1 row or a maximum of 6");
        }
        this.inventory = Bukkit.createInventory(inventoryHolder==null?new DerpCoreHolder(this):inventoryHolder,
                9*inventoryRows,inventoryName);
        this.guiButtons = new HashMap<>();
    }

    public Inventory getInventory(){return this.inventory;}

    public Map<Integer, IGuiButton> getGuiButtons(){return Collections.unmodifiableMap(this.guiButtons);}

    public void removeButton(IGuiButton button){
        if(this.guiButtons.containsValue(button)){
            this.inventory.remove(button.getDisplayItem());
            this.guiButtons.remove(button.getInventorySlot());
        }
    }

    public void addPageButton(final ItemStack displayItem, final int inventorySlot, final GuiPage page){
        if(this.inventory.getSize()<inventorySlot){
            throw new IndexOutOfBoundsException("Inventory Slot "+inventorySlot+" does not exist!");
        }
        IPageButton button = new IPageButton() {
            @Override
            public GuiPage getPage() {
                return page;
            }

            @Override
            public ItemStack getDisplayItem() {
                return displayItem;
            }

            @Override
            public int getInventorySlot() {
                return inventorySlot;
            }
        };
        if(this.guiButtons.containsKey(inventorySlot)){
            this.removeButton(this.guiButtons.get(inventorySlot));
        }
        this.guiButtons.put(inventorySlot,button);
        this.inventory.setItem(inventorySlot, displayItem);
    }

    public void addExecutableButton(final ItemStack displayItem, final int inventorySlot, final InventoryExecutable executable){
        if(this.inventory.getSize()<inventorySlot){
            throw new IndexOutOfBoundsException("Inventory Slot "+inventorySlot+" does not exist!");
        }
        IExecutableButton button = new IExecutableButton() {
            @Override
            public void activateButton() {
                try {
                    executable.execute();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public ItemStack getDisplayItem() {
                return displayItem;
            }

            @Override
            public int getInventorySlot() {
                return inventorySlot;
            }
        };
        if(this.guiButtons.containsKey(inventorySlot)){
            this.removeButton(this.guiButtons.get(inventorySlot));
        }
        this.guiButtons.put(inventorySlot,button);
        this.inventory.setItem(inventorySlot, displayItem);
    }

    public void setFilled(boolean bool){
        int i = 0;
        for(ItemStack item : this.inventory.getContents()){
            if(bool && item == null){
                this.inventory.setItem(i, GuiHandler.core.blankSlotItem);
            }else if(!bool && item == GuiHandler.core.blankSlotItem){
                this.inventory.setItem(i, null);
            }
            i++;
        }
    }
}
