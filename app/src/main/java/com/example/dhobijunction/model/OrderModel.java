package com.example.dhobijunction.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    String Name;
    String Email;
    String number;
    String Address;
    List<CheckModel> modelList;

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

    public List<CheckModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<CheckModel> modelList) {
        this.modelList = modelList;
    }
}
