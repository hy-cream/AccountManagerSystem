package com.study.bean;

import java.io.Serializable;

/**
 * Created by 胡钰 on 2017/1/5.
 */

public class AccountBean implements Serializable {
    private String name;
    private double price;
    private String user_id;
    private String type;

    public AccountBean(String name, double price, String user_id,String type) {
        this.name = name;
        this.price = price;
        this.user_id = user_id;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
