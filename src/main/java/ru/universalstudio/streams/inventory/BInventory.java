package ru.universalstudio.streams.inventory;

import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import java.util.function.*;
import java.util.concurrent.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import ru.universalstudio.streams.inventory.common.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class BInventory {

    private final Inventory inv;
    private final ConcurrentHashMap<Integer, Click> itemsClick;
    private final ConcurrentHashMap<Integer, ItemStack> items;
    public static ConcurrentHashMap<Player, Consumer<BInventory>> invUpd = new ConcurrentHashMap<>();

    public BInventory(String string, int n, Plugin plugin) {
        this.inv = Bukkit.createInventory(new InventoryHolder(this, plugin), (9 * n), string);
        this.itemsClick = new ConcurrentHashMap<>();
        this.items = new ConcurrentHashMap<>();
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (Object invUpdPlayer : BInventory.invUpd.keySet()) {
                    Player player = (Player)invUpdPlayer;
                    ((Consumer)BInventory.invUpd.get(player)).accept(this);
                }
            }
        }, 0L, 20L);
    }

    public Inventory getInventory() {
        return this.inv;
    }

    public void addItem(Integer n, ItemStack itemStack) {
        if (this.items.contains(n)) {
            this.items.remove(n);
        }
        this.items.put(n, itemStack);
        this.inv.setItem(n, itemStack);
    }

    public void addItem(Integer n, ItemStack itemStack, Click click) {
        if (this.itemsClick.contains(n)) {
            this.itemsClick.remove(n);
        }
        this.itemsClick.put(n, click);
        this.addItem(n, itemStack);
    }

    public void open(Player player) {
        this.items.clear();
        this.itemsClick.clear();
        player.openInventory(this.inv);
    }

    public void open(Player player, Consumer<BInventory> consumer) {
        this.open(player);
        consumer.accept(this);
        invUpd.put(player, consumer);
    }

    public void clear() {
        this.inv.clear();
    }

    public void closeInventoryPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getInventory().getHolder() == null || !(player.getInventory().getHolder() instanceof InventoryHolder)) continue;
            player.closeInventory();
        }
    }

    public ConcurrentHashMap<Integer, ItemStack> getItems() {
        return this.items;
    }

    public ConcurrentHashMap<Integer, Click> getItemsClick() {
        return this.itemsClick;
    }
}
