package me.derpy.core.gui.interfaces;

import org.bukkit.inventory.ItemStack;

public interface IGuiButton {
    ItemStack getDisplayItem();
    int getInventorySlot();
}
