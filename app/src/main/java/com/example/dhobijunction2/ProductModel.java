package com.example.dhobijunction2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {

  public String getPid() {
    return Pid;
  }

  public void setPid(String pid) {
    Pid = pid;
  }

  public void setSid(String sid) {
    Sid = sid;
  }

  private String Pid;
  private String Title;
  private String Price;
  private String KgGm;
  private String Sid;

  public String getImage() {
    return Image;
  }

  public void setImage(String image) {
    Image = image;
  }
  private String Qty;

  public String getQty() {
    return Qty;
  }

  public void setQty(String qty) {
    Qty = qty;
  }

  private String Image;
  public String getSid() {
    return Sid;
  }


  public ProductModel(){
  }

  public ProductModel(String pid, String title, String price, String kgGm, String sid,String image,String Qty) {
    this.Pid = pid;
    this.Image=image;
    this.Title = title;
    this.Price = price;
    this.KgGm = kgGm;
    this.Sid = sid;
    this.Qty=Qty;

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
   this.Price = price;
  }

  public String getKgGm() {
    return KgGm;
  }

  public void setKgGm(String kgGm) {
    this.KgGm = kgGm;
  }
}