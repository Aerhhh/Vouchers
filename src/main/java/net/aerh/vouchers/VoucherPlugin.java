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

import java.util.concurrent.atomic.AtomicInteger;

public final class VoucherPlugin extends JavaPlugin {

    private static VoucherPlugin instance;
    public static NamespacedKey VOUCHER_ID_KEY;

    @Override
    public void onEnable() {
        instance = this;
        VOUCHER_ID_KEY = new NamespacedKey(instance, "voucher_id");

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

        voucherSection.getKeys(false).forEach(key -> {
            ItemData itemData = ItemData.create(voucherSection.getString(key + ".item.display-name"), voucherSection.getStringList(key + ".item.lore"));
            VoucherData data = VoucherData.create(key, voucherSection.getString(key + ".description"), voucherSection.getString(key + ".command"), itemData);
            VoucherManager.get().addVoucher(data);
        });

        getLogger().info("Loaded " + voucherSection.getKeys(false).size() + " voucher" + (voucherSection.getKeys(false).size() == 1 ? "" : "s") + ".");
    }

    public static VoucherPlugin get() {
        return instance;
    }
}
