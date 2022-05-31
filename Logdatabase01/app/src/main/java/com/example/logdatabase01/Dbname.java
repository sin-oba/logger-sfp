package com.example.logdatabase01;

import android.app.Application;

public class Dbname extends Application {
    private String name = "No Data";
    public String getDbname() {
        return name;
    }
    public void setDbname(String str) {
        this.name = str;
    }
}
