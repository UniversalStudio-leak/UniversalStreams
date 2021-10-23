package ru.universalstudio.streams.inventory.common;

import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import ru.universalstudio.streams.inventory.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class InventoryHolder implements org.bukkit.inventory.InventoryHolder {

    private final BInventory inventory;
    private static boolean listener;

    public InventoryHolder(BInventory bInventory, Plugin plugin) {
        this.inventory = bInventory;
    }

    public BInventory getBInventory() {
        return this.inventory;
    }

    public Inventory getInventory() {
        return this.inventory.getInventory();
    }

    static void clinitProxy() {
        listener = false;
    }
}