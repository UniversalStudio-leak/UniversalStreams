package ru.universalstudio.streams.inventory.common;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;
import ru.universalstudio.streams.inventory.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class EventListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getHolder() != null && inventoryClickEvent.getInventory().getHolder() instanceof InventoryHolder) {
            InventoryHolder inventoryHolder = (InventoryHolder)inventoryClickEvent.getInventory().getHolder();
            BInventory bInventory = inventoryHolder.getBInventory();
            inventoryClickEvent.setCancelled(true);
            Click click = bInventory.getItemsClick().get(inventoryClickEvent.getSlot());
            click.onClick(inventoryClickEvent);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player)inventoryCloseEvent.getPlayer();
        if (BInventory.invUpd.containsKey(player)) {
            BInventory.invUpd.remove(player);
        }
    }
}