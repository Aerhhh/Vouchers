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

    private VoucherManager() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static VoucherManager get() {
        if (instance == null) {
            instance = new VoucherManager();
        }
        return instance;
    }

    public void addVoucher(VoucherData data) {
        this.data.add(data);
        VoucherPlugin.getInstance().getLogger().info("Added voucher " + data.getName());
    }

    public VoucherData getVoucher(String name) {
        for (VoucherData voucher : data) {
            if (voucher.getName().equals(name)) {
                return voucher;
            }
        }
        return null;
    }

    public VoucherRedeemState useVoucher(Player player, String voucherId) {
        VoucherData voucher = getVoucher(voucherId);
        if (voucher == null) {
            VoucherPlugin.getInstance().getLogger().warning(player.getName() + " tried to redeem voucher " + voucherId + " but it doesn't exist!");
            return VoucherRedeemState.VOUCHER_NOT_FOUND;
        }

        if (voucher.getCommands() == null) {
            VoucherPlugin.getInstance().getLogger().warning(player.getName() + " tried to redeem voucher " + voucherId + " but it has no commands!");
            return VoucherRedeemState.NO_COMMANDS;
        }
        
        voucher.getCommands().forEach(s -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", player.getName()));
        });

        return VoucherRedeemState.SUCCESS;
    }

    public List<VoucherData> getVouchers() {
        return data;
    }
}
