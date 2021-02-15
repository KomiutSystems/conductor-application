package com.komiut.conductor.retrofit;

import java.io.Serializable;

public class MpesaServerData implements Serializable {

    String firstname,lastname,trans_id,car_plate,date_time;
    String amount;
    String phone;




    public MpesaServerData(String firstname, String lastname, String trans_id, String car_plate, String amount, String phone, String date_time) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.trans_id = trans_id;
        this.car_plate = car_plate;
        this.amount = amount;
        this.phone = phone;
        this.date_time = date_time;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getCar_plate() {
        return car_plate;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
