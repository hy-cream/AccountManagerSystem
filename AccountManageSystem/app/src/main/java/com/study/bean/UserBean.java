package com.study.bean;

/**
 * Created by 胡钰 on 2017/1/5.
 */

public class UserBean {

    private String name;
    private String passwd;

    public UserBean(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
