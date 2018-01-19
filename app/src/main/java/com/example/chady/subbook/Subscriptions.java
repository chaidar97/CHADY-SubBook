package com.example.chady.subbook;


import java.io.Serializable;

/**
 * Created by Chady on 2018-01-17.
 */

//Class that handles storage of variables for a specific subscription
public class Subscriptions implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    String comment=" ";
    String date="INITIAL";
    double price=0.0;
    int ID;

    public Subscriptions(String subName){
        this.name = subName;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getID() { return ID; }

    public double getPrice() {
        return price;
    }

    public String getComment() { return comment;}

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
