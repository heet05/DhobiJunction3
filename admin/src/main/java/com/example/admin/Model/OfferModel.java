package com.example.admin.Model;

public class OfferModel {
    String title,code,date,price,offerid;

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String id, String offerid) {
        this.offerid = offerid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String s, String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String s, String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String s, String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String s, String price) {
        this.price = price;
    }
}
