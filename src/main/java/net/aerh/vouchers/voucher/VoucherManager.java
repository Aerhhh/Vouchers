package net.aerh.vouchers.voucher;

import net.aerh.vouchers.VoucherPlugin;
import net.aerh.vouchers.voucher.data.VoucherData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoucherManager {

    private static VoucherManager instance;
    private final List<VoucherData> data = new ArrayList<>();


    public VoucherManager() {
    }

    public static VoucherManager get() {
        if (instance == null) {
            instance = new VoucherManager();
        }
        return instance;
    }

    public void addVoucher(VoucherData data) {
        this.data.add(data);
        VoucherPlugin.get().getLogger().info("Added voucher " + data.getName());
    }

    public VoucherData getVoucher(String name) {
        for (VoucherData voucher : data) {
            if (voucher.getName().equals(name)) {
                return voucher;
            }
        }
        return null;
    }

    public UseStatus useVoucher(Player player, String voucherId) {
        VoucherData voucher = getVoucher(voucherId);
        if (voucher == null) {
            VoucherPlugin.get().getLogger().warning(player.getName() + " tried to redeem voucher " + voucherId + " but it doesn't exist!");
            return UseStatus.VOUCHER_NOT_FOUND;
        }

        if (voucher.getCommand() == null) {
            VoucherPlugin.get().getLogger().warning(player.getName() + " tried to redeem voucher " + voucherId + " but it has no command!");
            return UseStatus.NO_COMMAND;
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), voucher.getCommand().replace("%player%", player.getName()));
        return UseStatus.SUCCESS;
    }

    public List<VoucherData> getVouchers() {
        return data;
    }
}
