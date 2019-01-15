package com.example.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShoppingBean {
    @Id(autoincrement = true)
    private long id;
    private int commodityId;
    private String commodityName;
    private int count;
    private String pic;
    private double price;
    private boolean check;
    private int selected;

    @Generated(hash = 887668281)
    public ShoppingBean(long id, int commodityId, String commodityName, int count,
            String pic, double price, boolean check, int selected) {
        this.id = id;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.count = count;
        this.pic = pic;
        this.price = price;
        this.check = check;
        this.selected = selected;
    }

    @Generated(hash = 1245823369)
    public ShoppingBean() {
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getCheck() {
        return this.check;
    }
}
