package net.aerh.vouchers.voucher.data;

import net.aerh.vouchers.VoucherPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class VoucherData {

    private final String name, description, command;
    private final ItemData itemData;
    private ItemStack itemStack;

    public VoucherData(String name, String description, String command, ItemData itemData) {
        this.name = name;
        this.description = description;
        this.command = command;
        this.itemData = itemData;
    }

    public static VoucherData create(String name, String description, String command, ItemData itemData) {
        return new VoucherData(name, description, command, itemData);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCommand() {
        return command;
    }

    public ItemStack getItemStack(int amount) {
        if (itemStack == null) {
            itemStack = new ItemStack(Material.PAPER, amount);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta == null) {
                return null;
            }

            PersistentDataContainer container = itemMeta.getPersistentDataContainer();

            container.set(VoucherPlugin.VOUCHER_ID_KEY, PersistentDataType.STRING, name);
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemData.getDisplayName()));

            List<String> translatedLore = new ArrayList<>();

            for (String line : itemData.getLore()) {
                translatedLore.add(ChatColor.translateAlternateColorCodes('&', line));
            }

            itemMeta.setLore(translatedLore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
}