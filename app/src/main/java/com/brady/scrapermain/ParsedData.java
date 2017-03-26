package com.brady.scrapermain;

/**
 * Created by Sspencer on 3/22/17.
 */

public class ParsedData {

    private String Address;
    private String Date;
    private String Data;

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
