package net.aerh.vouchers.voucher.data;

import org.bukkit.ChatColor;

import java.util.List;

public class ItemData {

    private final String displayName;
    private final List<String> lore;

    public ItemData(String displayName, List<String> lore) {
        this.displayName = displayName;
        this.lore = lore;
    }

    public static ItemData create(String displayName, List<String> lore) {
        return new ItemData(displayName, lore);
    }

    public String getDisplayName() {
        return displayName + " " + ChatColor.GRAY + "(Right Click)";
    }

    public List<String> getLore() {
        return lore;
    }
}
