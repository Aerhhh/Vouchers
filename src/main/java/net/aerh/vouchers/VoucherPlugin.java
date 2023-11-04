package net.aerh.vouchers;

import net.aerh.vouchers.command.GiveVoucherCommand;
import net.aerh.vouchers.command.ListVouchersCommand;
import net.aerh.vouchers.listener.VoucherClickListener;
import net.aerh.vouchers.voucher.VoucherManager;
import net.aerh.vouchers.voucher.data.ItemData;
import net.aerh.vouchers.voucher.data.VoucherData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class VoucherPlugin extends JavaPlugin {

    private static VoucherPlugin instance;
    private NamespacedKey voucherIdKey;

    @Override
    public void onEnable() {
        instance = this;
        voucherIdKey = new NamespacedKey(instance, "voucher_id");

        Bukkit.getPluginManager().registerEvents(new VoucherClickListener(), this);
        getCommand("listvouchers").setExecutor(new ListVouchersCommand());
        getCommand("givevoucher").setExecutor(new GiveVoucherCommand());
        saveDefaultConfig();
        loadVouchers();
    }

    /**
     * Load all the vouchers from the config file.
     */
    private void loadVouchers() {
        getLogger().info("Loading vouchers...");

        ConfigurationSection voucherSection = getConfig().getConfigurationSection("vouchers");
        if(voucherSection == null) {
            getLogger().warning("No vouchers found in config file!");
            return;
        }

        Set<String> vouchers = voucherSection.getKeys(false);
        vouchers.forEach(key -> {
            ItemData itemData = ItemData.create(voucherSection.getString(key + ".item.display-name"), voucherSection.getStringList(key + ".item.lore"));
            VoucherData data = VoucherData.create(key, voucherSection.getString(key + ".description"), voucherSection.getStringList(key + ".commands"), itemData);
            VoucherManager.get().addVoucher(data);
        });

        getLogger().info("Loaded " + vouchers.size() + " voucher" + (vouchers.size() == 1 ? "" : "s"));
    }

    public static VoucherPlugin getInstance() {
        return instance;
    }
    
    public NamespacedKey getVoucherIdKey() {
        return voucherIdKey;
    }
}
