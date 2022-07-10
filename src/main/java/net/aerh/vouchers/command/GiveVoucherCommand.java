package net.aerh.vouchers.command;

import net.aerh.vouchers.voucher.data.VoucherData;
import net.aerh.vouchers.voucher.VoucherManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveVoucherCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /givevoucher <player> <voucher> [amount]");
            return false;
        }

        Player targetName = Bukkit.getPlayer(args[0]);
        if (targetName == null) {
            sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found!");
            return false;
        }

        String voucherName = args[1];
        VoucherData data = VoucherManager.get().getVoucher(voucherName);
        if (data == null) {
            sender.sendMessage(ChatColor.RED + "Voucher " + voucherName + " not found!");
            return false;
        }

        int amount = 1;
        if (args.length >= 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "The amount must be a number!");
                return false;
            }
        }

        String amountString = "voucher ";
        if (amount > 1) amountString = amount + " of voucher ";
        sender.sendMessage(ChatColor.GREEN + "Giving " + amountString + ChatColor.AQUA + voucherName + ChatColor.GREEN +
                " to " + ChatColor.YELLOW + targetName.getName() + ChatColor.GREEN + "!");
        ItemStack voucher = VoucherManager.get().getVoucher(voucherName).getItemStack();
        voucher.setAmount(amount);
        targetName.getInventory().addItem(voucher);
        return false;
    }
}
