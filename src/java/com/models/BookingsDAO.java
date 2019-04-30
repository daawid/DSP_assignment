package com.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dawid
 */

public class BookingsDAO implements GenericDAO<BookingsBean, Integer> {

    private Database db;

    public BookingsDAO() {
        db = new Database();
    }

    //Create booking entry in db
    @Override
    public boolean create(BookingsBean newBooking) {
        boolean inserted = db.insertData(Database.TBL_BOOKINGS, newBooking);
        return inserted;
    }

    //Generate a list of all bookings
    @Override
    public List<BookingsBean> getAll() {
        ArrayList<BookingsBean> bookings = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_BOOKINGS)) {
            BookingsBean booking = (BookingsBean) object;
            bookings.add(booking);
        }

        return bookings;
    }

    //Get all matching status
    public List<BookingsBean> getAllByStatus(String status) {
        ArrayList<BookingsBean> bookings = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_BOOKINGS, Database.TBL_BOOKINGS_STATUS, status)) {
            BookingsBean booking = (BookingsBean) object;
            bookings.add(booking);
        }

        return bookings;
    }

    //Get all by member ID
    public List<BookingsBean> getAllByMemberID(String memberId) {
        ArrayList<BookingsBean> bookings = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_BOOKINGS, Database.TBL_BOOKINGS_MEMBER_ID, memberId)) {
            BookingsBean booking = (BookingsBean) object;
            bookings.add(booking);
        }

        return bookings;
    }

    //Get by booking ID
    public List<BookingsBean> getAllByID(int bookingId) {
        ArrayList<BookingsBean> bookings = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_BOOKINGS, Database.TBL_BOOKINGS_ID, bookingId)) {
            BookingsBean booking = (BookingsBean) object;
            bookings.add(booking);
        }

        return bookings;
    }

    //Get by matching ID
    @Override
    public BookingsBean getByID(Integer id) {
        BookingsBean booking = (BookingsBean) db.searchData(Database.TBL_BOOKINGS, Database.TBL_BOOKINGS_ID, id);
        if (booking == null) {
            System.out.println("BookingDAO error, not found");
        }
        return booking;
    }

    //Update booking status
    public boolean updateStatus(BookingsBean updatedBooking) {
        return db.updateData(Database.TBL_BOOKINGS, updatedBooking.getID(), Database.TBL_BOOKINGS_STATUS, updatedBooking.getStatus());
    }
    
    //Update booking date of travel
    public boolean updateBookingDot(BookingsBean updatedBooking) {
        return db.updateData(Database.TBL_BOOKINGS, updatedBooking.getID(), Database.TBL_BOOKINGS_DATEOFTRAVEL, updatedBooking.getDateOfTravel());
    }

    //Update booking number of passengers
    public boolean updateBookingPassengers(BookingsBean updatedBooking) {
        return db.updateData(Database.TBL_BOOKINGS, updatedBooking.getID(), Database.TBL_BOOKINGS_PASSENGERS, updatedBooking.getPassengers());
    }

    //Update booking price
    public boolean updateBookingPrice(BookingsBean updatedBooking) {
        return db.updateData(Database.TBL_BOOKINGS, updatedBooking.getID(), Database.TBL_BOOKINGS_PRICE, updatedBooking.getPrice());
    }
}
