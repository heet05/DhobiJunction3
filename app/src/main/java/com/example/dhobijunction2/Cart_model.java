package com.example.dhobijunction2;

public class Cart_model {
    private String Pid;
    private String Title;
    private String Price;
    private String KgGm;
    private String Qty;
    private String Image;
    String total;
    String cartItemId;

    public Cart_model() {

    }

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

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getKgGm() {
        return KgGm;
    }

    public void setKgGm(String kgGm) {
        KgGm = kgGm;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Cart_model(String pid, String title, String price, String kgGm, String qty, String image, String total, String cartItemId) {
        Pid = pid;
        Title = title;
        Price = price;
        KgGm = kgGm;
        Qty = qty;
        Image = image;
        this.total = total;
        this.cartItemId = cartItemId;
    }

}
