package com.models;

import java.sql.Date;

/**
 *
 * @author dawid
 */
public class BookingsBean {

    // constant to reduce duplication and spelling/coding errors
    public static final String APPROVED = "Approved";
    public static final String SUBMITTED = "Submitted";
    public static final String REJECTED = "Rejected";

    // variable
    private int ID;
    private String memberID;
    private Date date, dateOfTravel;
    private String status;
    private double price;
    private boolean accepted;
    private String origin;
    private String destination;
    private int distance;
    private int passengers;
    private String reference;

    // constructor
    public BookingsBean() {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

//    @Override
//    public String toString() {
//        return "BookingsBean{" + "ID=" + ID + ", memberID=" + memberID + ", date=" + date + ", status=" + status + ", price=" + price + ", accepted=" + accepted + ", origin=" + origin + ", destination=" + destination + ", distance=" + distance + '}';
//    }
    @Override
    public String toString() {
        return "ID" + ID;
    }

}
