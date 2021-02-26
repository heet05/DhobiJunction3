package com.example.dhobijunction.model;

public class CartModel {
    String image;
    String price;
    String title;
    String sid;
    String qty;
    String pid;
    String cartItemId;
    String total;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getpId() {
        return pid;
    }

    public void setpId(String pId) {
        this.pid = pId;
    }

    public CartModel(String image, String price, String title, String sId, String qty, String pId,String cartItemId,String total) {
        this.image = image;
        this.price = price;
        this.title = title;
        this.sid = sId;
        this.qty = qty;
        this.pid = pId;
        this.cartItemId = cartItemId;
        this.total = total;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getsId() {
        return sid;
    }

    public void setsId(String sId) {
        this.sid = sId;
    }
    public CartModel(){}
}
