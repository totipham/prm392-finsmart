package com.example.finsmart.Fragment;

import com.example.finsmart.Model.Wallet;

public class WalletWithCheck extends Wallet {
    public boolean isChecked;

    public WalletWithCheck(Wallet wallet, boolean isChecked) {
        super(wallet.getWalletId(), wallet.getName(), wallet.getBalance());
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Wallet getWallet() {
        return new Wallet(getWalletId(), getName(), getBalance());
    }
}
