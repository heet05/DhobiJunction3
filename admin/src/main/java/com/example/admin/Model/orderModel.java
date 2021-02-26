package com.example.admin.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class orderModel implements Serializable {

    String Name;
    String Email;
    String number;
    String Address;
    String Total;
    List<checkOutModel>modelList;


    public List<checkOutModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<checkOutModel> modelList) {
        this.modelList = modelList;
    }

    @ServerTimestamp
    Date timestamp;
    String deliverTime;
    String PayMentMethod;
    String orderId;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getPayMentMethod() {
        return PayMentMethod;
    }

    public void setPayMentMethod(String payMentMethod) {
        PayMentMethod = payMentMethod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
