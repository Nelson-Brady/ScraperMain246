package com.brady.scrapermain;

public class ParsedData {

    private String Address;
    private String Date;
    private String Data;
    private String hour;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMins() {
        return mins;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public String getSecs() {
        return secs;
    }

    public void setSecs(String secs) {
        this.secs = secs;
    }

    private String mins;
    private String secs;

    public ParsedData() {
    }


    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAddress() {
        return Address;
    }

    public String getDate() {
        return Date;
    }



}
