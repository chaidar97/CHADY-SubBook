package com.example.chady.subbook;



/**
 * Created by Chady on 2018-01-17.
 */


/**
 * Class that handles storage of variables for a specific subscription
 */
public class Subscriptions {
    public String name;
    public String comment=" ";
    public String date="INITIAL";
    public double price=0.0;
    public int ID;

    /**
     * Consrtucts subscription object with sub name as a parameter
     * @param subName
     */
    public Subscriptions(String subName){
        this.name = subName;
    }

    /**
     * Sets the ID of the sub
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Sets the price of the sub
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the name of the sub
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the comment of the sub
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the date of the sub
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the ID of the sub
     * @return returns the ID
     */
    public int getID() { return ID; }

    /**
     * Returns the price of the sub
     * @return returns the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the comment of the sub
     * @return returns the comment
     */
    public String getComment() { return comment;}

    /**
     * Returns the date of the sub
     * @return Returns the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the name of the sub
     * @return Returns the name
     */
    public String getName() {
        return name;
    }
}
