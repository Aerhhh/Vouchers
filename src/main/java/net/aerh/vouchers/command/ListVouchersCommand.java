package net.aerh.vouchers.command;

import net.aerh.vouchers.voucher.VoucherManager;
import net.aerh.vouchers.voucher.data.VoucherData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListVouchersCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> voucherNames = new ArrayList<>();

        for (VoucherData data : VoucherManager.get().getVouchers()) {
            voucherNames.add(data.getName());
        }

        sender.sendMessage(ChatColor.GREEN + "Vouchers: " + ChatColor.AQUA + String.join(ChatColor.GREEN + ", " + ChatColor.AQUA, voucherNames));
        return false;
    }
}
