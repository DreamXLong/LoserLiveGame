package com.xlong.loserlivegame.model;

/**
 * Created by SLP on 2017/3/16.
 */

public class SaleModel {
    private String name;
    private int money;

    public SaleModel() {
    }

    public SaleModel(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "SaleModel{" +
                "name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
