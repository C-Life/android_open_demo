package com.het.sdk.demo.model;

import java.io.Serializable;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-12-28<br>
 * 描述:
 **/
public class HetUserInfoBean implements Serializable {
    private String userId;
    private String userName;
    private int sex;
    private String phone;
    private Object email;
    private String birthday;
    private int weight;
    private int height;
    private String avatar;
    private String city;
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public HetUserInfoBean() {
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String var1) {
        this.userId = var1;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String var1) {
        this.userName = var1;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int var1) {
        this.sex = var1;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String var1) {
        this.phone = var1;
    }

    public Object getEmail() {
        return this.email;
    }

    public void setEmail(Object var1) {
        this.email = var1;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String var1) {
        this.birthday = var1;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int var1) {
        this.weight = var1;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int var1) {
        this.height = var1;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String var1) {
        this.avatar = var1;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String var1) {
        this.city = var1;
    }
}