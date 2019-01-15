package com.example.bean;

import java.util.List;

public class UserWallet<T> {
    private double balance;
    private List<WalletBean> detailList;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<WalletBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<WalletBean> detailList) {
        this.detailList = detailList;
    }
}
