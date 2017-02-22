package com.study.bean;

import java.io.Serializable;

/**
 * Created by 胡钰 on 2017/1/5.
 */

public class RecordBean implements Serializable{

    private String inOrOut;//支出或者收入
    private String content;//事情
    private double money;//多少钱
    private String type;//什么类型的账户
    private String date;//日期
    private String user_id;//用户id

    public RecordBean(String inOrOut, String content, double money, String type, String date, String user_id) {
        this.inOrOut = inOrOut;
        this.content = content;
        this.money = money;
        this.type = type;
        this.date = date;
        this.user_id = user_id;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
