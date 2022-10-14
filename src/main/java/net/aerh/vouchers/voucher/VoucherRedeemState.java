package net.aerh.vouchers.voucher;

import org.bukkit.ChatColor;

public enum VoucherRedeemState {
    VOUCHER_NOT_FOUND(ChatColor.RED + "This is an invalid voucher! Please contact a staff member."),
    NO_COMMAND(ChatColor.RED + "This voucher has no data! Please contact a staff member."),
    SUCCESS;

    private final String message;

    VoucherRedeemState() {
        this.message = "";
    }

    VoucherRedeemState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
