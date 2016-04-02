package com.binc.expensemanager.bean;

/**
 * Created by yashbuch on 12/03/16.
 */
public class DataBean {
    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_te() {
        return name_te;
    }

    public void setName_te(String name_te) {
        this.name_te = name_te;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReq_qty() {
        return req_qty;
    }

    public void setReq_qty(String req_qty) {
        this.req_qty = req_qty;
    }

    public String getAvb_qty() {
        return avb_qty;
    }

    public void setAvb_qty(String avb_qty) {
        this.avb_qty = avb_qty;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name_en = "";
    private String name_te = "";
    private String category = "";
    private String req_qty = "";
    private String avb_qty = "";
}
