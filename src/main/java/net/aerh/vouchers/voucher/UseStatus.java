package net.aerh.vouchers.voucher;

import org.bukkit.ChatColor;

public enum UseStatus {
    VOUCHER_NOT_FOUND(ChatColor.RED + "This is an invalid voucher! Please contact a staff member."),
    NO_COMMAND(ChatColor.RED + "This voucher has no data! Please contact a staff member."),
    SUCCESS;

    private final String message;

    UseStatus() {
        this.message = "";
    }

    UseStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
