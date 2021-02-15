package com.komiut.conductor.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

public class MpesaHomer implements Serializable {

    String transactionId;
    String date,time,amount,phonenumber,firstname,secondname;
    Date dateParsed;

    public Date getDateParsed() {
        return dateParsed;
    }

    public void setDateParsed(Date dateParsed) {
        this.dateParsed = dateParsed;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }
}
