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

    private final String name, description;
    private final List<String> commands;
    private final ItemData itemData;
    private ItemStack itemStack;

    public VoucherData(String name, String description, List<String> commands, ItemData itemData) {
        this.name = name;
        this.description = description;
        this.commands = commands;
        this.itemData = itemData;
    }

    public static VoucherData create(String name, String description, List<String> commands, ItemData itemData) {
        return new VoucherData(name, description, commands, itemData);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCommands() {
        return commands;
    }

    public ItemStack getItemStack(int amount) {
        if (itemStack == null) {
            itemStack = new ItemStack(Material.PAPER, amount);
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta == null) {
                return null;
            }

            PersistentDataContainer container = itemMeta.getPersistentDataContainer();

            container.set(VoucherPlugin.getInstance().getVoucherIdKey(), PersistentDataType.STRING, name);
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