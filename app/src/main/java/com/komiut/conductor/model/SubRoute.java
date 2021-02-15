package com.komiut.conductor.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SubRoute {

    @NonNull
    @PrimaryKey
            int id;
    String subRoutename;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubRoutename() {
        return subRoutename;
    }

    public void setSubRoutename(String subRoutename) {
        this.subRoutename = subRoutename;
    }
}
