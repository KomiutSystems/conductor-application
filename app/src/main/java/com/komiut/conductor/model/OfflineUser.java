package com.komiut.conductor.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.komiut.conductor.R;

import java.io.Serializable;

@Entity
public class OfflineUser implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;
    String user_email;
    String user_name;
    String user_phone;
    String user_plate;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] fingerprint;


    public byte[] getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(byte[] fingerprint) {
        this.fingerprint = fingerprint;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_plate() {
        return user_plate;
    }

    public void setUser_plate(String user_plate) {
        this.user_plate = user_plate;
    }
}
