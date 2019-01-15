package com.example.bean;

import android.content.Intent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfo {
    @Id(autoincrement = true)
    private long id;
    private String name;
    private String password;
    private int status;

    @Generated(hash = 1002779832)
    public UserInfo(long id, String name, String password, int status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.status = status;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
