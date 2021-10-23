package ru.universalstudio.streams.inventory.common;

import java.util.*;
import org.bukkit.*;
import com.mojang.authlib.*;
import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.meta.*;
import com.mojang.authlib.properties.*;
import ru.universalstudio.streams.reflection.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(ItemStack itemStack) {
        this.item = itemStack;
    }

    public ItemBuilder(String string, int n) {
        if (string.length() > 11) {
            this.item = new ItemStack(Material.SKULL_ITEM, n, (byte)3);
            this.setSkullTexture(string);
        } else {
            String[] args = string.split(":");
            this.item = (args.length == 2 ? new ItemStack(Material.getMaterial(args[0]), n, Short.parseShort(args[1])) : new ItemStack(Material.getMaterial(args[0]), n, Short.parseShort("0")));
        }
    }

    public ItemBuilder(String string) {
        this(string, 1);
    }

    public ItemBuilder() {
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
    }

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder(Material material, int n, byte by) {
        this.item = new ItemStack(material, n, by);
    }

    public ItemBuilder setDisplayName(String s) {
        ItemMeta itemMeta = this.item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> list) {
        ItemMeta itemMeta = this.item.getItemMeta();
        for(String s : list) {
            ChatColor.translateAlternateColorCodes('&', s);
        }
        itemMeta.setLore(list);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> list, String... lores) {
        ItemMeta itemMeta = this.item.getItemMeta();
        for (int i = 0; i < list.size(); ++i) {
            int n = 0;
            while (n < lores.length) {
                list.set(i, ChatColor.translateAlternateColorCodes('&', (list.get(i)).replace(lores[n++], lores[n++])));
            }
        }
        itemMeta.setLore(list);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int n, boolean bl) {
        ItemMeta itemMeta = this.item.getItemMeta();
        itemMeta.addEnchant(enchantment, n, bl);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag itemFlag) {
        ItemMeta itemMeta = this.item.getItemMeta();
        itemMeta.addItemFlags(itemFlag);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setSkullTexture(String string) {
        SkullMeta skullMeta = (SkullMeta)this.item.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString(("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/" + string + "\"}}}"))));
        try {
            ReflectionUtils.setField(skullMeta, "profile", gameProfile);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        this.item.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setSkull(String string) {
        SkullMeta skullMeta = (SkullMeta)this.item.getItemMeta();
        skullMeta.setOwner(string);
        this.item.setItemMeta(skullMeta);
        return this;
    }

    public ItemStack done() {
        return this.item;
    }

}