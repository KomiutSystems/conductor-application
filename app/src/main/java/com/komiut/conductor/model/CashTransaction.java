package com.komiut.conductor.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CashTransaction implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int user_id;

    String selectedDepart, selectedDest, regno, passname, passphone, nopass, amount, luggage, amtGiven, stringTotal, stringChange;
    String date;
    String offlineDataAccessDate;
    String uniqueID;
    String cashId;
    boolean status;


    public String getOfflineDataAccessDate() {
        return offlineDataAccessDate;
    }

    public void setOfflineDataAccessDate(String offlineDataAccessDate) {
        this.offlineDataAccessDate = offlineDataAccessDate;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSelectedDepart() {
        return selectedDepart;
    }

    public void setSelectedDepart(String selectedDepart) {
        this.selectedDepart = selectedDepart;
    }

    public String getSelectedDest() {
        return selectedDest;
    }

    public void setSelectedDest(String selectedDest) {
        this.selectedDest = selectedDest;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getPassname() {
        return passname;
    }

    public void setPassname(String passname) {
        this.passname = passname;
    }

    public String getPassphone() {
        return passphone;
    }

    public void setPassphone(String passphone) {
        this.passphone = passphone;
    }

    public String getNopass() {
        return nopass;
    }

    public void setNopass(String nopass) {
        this.nopass = nopass;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public String getAmtGiven() {
        return amtGiven;
    }

    public void setAmtGiven(String amtGiven) {
        this.amtGiven = amtGiven;
    }

    public String getStringTotal() {
        return stringTotal;
    }

    public void setStringTotal(String stringTotal) {
        this.stringTotal = stringTotal;
    }

    public String getStringChange() {
        return stringChange;
    }

    public void setStringChange(String stringChange) {
        this.stringChange = stringChange;
    }

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }
}
