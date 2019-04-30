package com.models;

import java.sql.Date;
import java.sql.Time;


/**
 *
 * @author dawid
 */
public class PaymentBean {

    // variable
    private int ID;
    private String memberID;
    private double amount;
    private Date date;
    private Time timestamp;
    private String type;

    // constructor
    public PaymentBean() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Time timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Payment [ID = " + ID + ", Type = " + type + ", Amount = " + amount + ", Date = " + date + "]";
    }
}
