package ru.universalstudio.streams.inventory;

import java.util.*;
import java.util.stream.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import net.md_5.bungee.api.chat.*;
import ru.universalstudio.streams.*;
import org.bukkit.event.inventory.*;
import ru.universalstudio.streams.common.*;
import ru.universalstudio.streams.common.Stream;
import ru.universalstudio.streams.inventory.common.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class InventoryManager extends BInventory {

    private static List<Integer> ints = new ArrayList<>();

    public InventoryManager() {
        super(Manager.getInstance().msg("INVENTORY_TITLE"), 5, (Plugin)Manager.getInstance());
    }

    private static void accept(BInventory bInventory) {
        bInventory.clear();
        bInventory.addItem(44, new ItemBuilder(Manager.getInstance().msg("INFO_ITEM.ITEM_ID")).setDisplayName(Manager.getInstance().msg("INFO_ITEM.ITEM_NAME")).setLore(Manager.getInstance().getConfig().getStringList("INFO_ITEM.ITEM_LORE")).done());
        if (StreamManager.getStreams().values().stream().noneMatch(Stream::isActive)) {
            bInventory.addItem(22, new ItemBuilder(Manager.getInstance().msg("ITEM_LIST_EMPTY.ITEM_ID")).setDisplayName(Manager.getInstance().msg("ITEM_LIST_EMPTY.ITEM_NAME")).setLore(Manager.getInstance().getConfig().getStringList("ITEM_LIST_EMPTY.ITEM_LORE")).done());
        } else {
            List<Stream> list = StreamManager.getStreams().values().stream().filter(Stream::isActive).collect(Collectors.toList());
            for (int i = 0; i < list.size(); ++i) {
                Stream stream = list.get(i);
                bInventory.addItem(ints.get(i), new ItemBuilder().setSkull(stream.getPlayerName())
                                .setDisplayName(Manager.getInstance().msg("FORMAT_ITEM.ITEM_NAME")
                                        .replace("{group}", Manager.getInstance().getGroup(stream.getPlayerName()))
                                        .replace("{playerName}", stream.getPlayerName())
                                        .replace("{streamName}", stream.getTitle()))
                                .setLore(
                                        Manager.getInstance().getConfig().getStringList("FORMAT_ITEM.ITEM_LORE"),
                                        "{group}",
                                        Manager.getInstance().getGroup(stream.getPlayerName()),
                                        "{streamName}",
                                        stream.getTitle(),
                                        "{playerName}",
                                        stream.getPlayerName(),
                                        "{views}",
                                        Manager.getInstance().number(stream.getViews()),
                                        "{likes}",
                                        Manager.getInstance().number(stream.getLikes()),
                                        "{dislikes}",
                                        Manager.getInstance().number(stream.getDisLikes()),
                                        "{streamName}",
                                        stream.getTitle()).done(),
                        new Click() { // если енто не так, то меня не бить
                            @Override
                            public void onClick(InventoryClickEvent inventoryClickEvent) {
                                inventoryClickEvent.getWhoClicked().closeInventory();
                                inventoryClickEvent.getWhoClicked().spigot().sendMessage(InventoryManager.getTextWithUrl(Manager.getInstance().msg("MESSAGE_FORMAT"), Manager.getInstance().msg("HOVER_MESSAGE"), stream.getUrl()));
                            }
                        }
                );
            }
        }
    }

    public static TextComponent getTextWithCommand(String string, String string2, String string3) {
        TextComponent textComponent = new TextComponent(string);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(string2).create()));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, string3));
        return textComponent;
    }

    public static TextComponent getTextWithUrl(String string, String string2, String string3) {
        TextComponent textComponent = new TextComponent(string);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(string2).create()));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, string3));
        return textComponent;
    }

    public void openInv(Player player) {
        this.open(player, InventoryManager::accept);
    }

}
