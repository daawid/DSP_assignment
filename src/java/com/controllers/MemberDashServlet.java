package com.controllers;

import com.models.BookingsBean;
import com.models.BookingsDAO;
import com.models.Email;
import com.models.MembersDAO;
import com.models.PaymentBean;
import com.models.PaymentDAO;
import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dawid
 */
@WebServlet(name = "MemberDashServlet", urlPatterns = {"/MemberDashServlet"})
public class MemberDashServlet extends HttpServlet {

    MembersDAO members = new MembersDAO();
    UserDAO users = new UserDAO();
    BookingsDAO bookings = new BookingsDAO();
    PaymentDAO payments = new PaymentDAO();
    /**
     * Dashboard messages *
     */
    String DASH_MSG = "DASH_MSG";
    String LOGIN_MSG = "LOGIN_MSG";
    String ERR_MSG = "ERR_MSG";
    String WARNING_MSG = "WARNING_MSG";
    String SUCCESS_MSG = "SUCCESS_MSG";
    DecimalFormat df = new DecimalFormat("#.##");
    Email emailModel = new Email();
    double currentBalance = 0;
    double newBalance = 0;
    BookingsBean tempBooking = new BookingsBean();
    ArrayList<BookingsBean> bookingArr = new ArrayList<>();
    int passengers = 0;
    double price = 0;
    int miles = 0;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        HttpSession session = request.getSession(false);

        //If session doesn't exist then create it
        if (session == null) {
            session = request.getSession();
        }

        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        }

        String username = (String) session.getAttribute("username"); // Retrieve the user from session

        /**
         * Read input from member-dashboard.jsp *
         */
        String button = request.getParameter("function"); // Reads the function group from the JSP and casts it to String button

        User tempUser = members.getByID(username);

        switch (button) {
            case "checkOutstanding": //Check how much balance is left to pay
                request.setAttribute("checkOutstanding", tempUser.getBalance());
                double outstandingPayments = tempUser.getBalance();
                request.setAttribute("outstandingPayments", outstandingPayments);
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "makePayment": //Pay outstanding balance
                currentBalance = tempUser.getBalance();

                try {
                    double paymentAmount = Double.parseDouble(df.format(Double.parseDouble(request.getParameter("paymentAmount"))));

                    //Make sure payment is valid
                    if (paymentAmount > currentBalance) { 
                        request.setAttribute(WARNING_MSG, "Payment amount too high");
                        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                    } else if (paymentAmount <= 0) {
                        request.setAttribute(WARNING_MSG, "Payment amount too low");
                        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                    } else {
                        //Payment OK
                        newBalance = Double.parseDouble(df.format(currentBalance - paymentAmount));
                        tempUser.setBalance(newBalance);
                        members.updateBalance(tempUser);

                        PaymentBean payment = new PaymentBean();
                        payment.setAmount(paymentAmount);
                        payment.setDate(new Date(System.currentTimeMillis()));
                        payment.setTimestamp(new Time(System.currentTimeMillis()));
                        payment.setMemberID(username);
                        payment.setType("Travel costs");

                        payments.create(payment);

                        request.setAttribute(SUCCESS_MSG, "Payment successful");
                        request.setAttribute(DASH_MSG, "Payment of £" + paymentAmount + " made for user " + username);
                        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                    }

                } catch (NumberFormatException e) {
                    request.setAttribute(ERR_MSG, "Payment amount must be a number");
                    e.printStackTrace();
                }

                //request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "makeBooking": //Forward to booking
                request.setAttribute("makeBooking", "asd");
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "makeABooking": //Allows the user to make a booking
                String status = tempUser.getStatus().trim();
                Double balance = tempUser.getBalance();
                Date date = Date.valueOf(request.getParameter("date"));

                bookingArr.clear();
                String temp = request.getParameter("distance").replace(",", "");
                String tempPassengers = request.getParameter("passengers");
                miles = Integer.parseInt(temp);
                passengers = Integer.parseInt(tempPassengers);

                //Work out price based on mileage
                try {
                    if (miles <= 100) {
                        price = 20;
                    } else if (miles >= 101) {
                        price = 30;
                    }
                } finally {
                    try {
                        //Adjust the price to reflect additional passengers
                        if (passengers > 1) {
                            price = price + (((price / 2) * passengers) - (price / 2));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();

                    }
                }

                String origin = request.getParameter("origin");
                String destination = request.getParameter("destination");

                //Checks if there are already bookings for the user with "Submitted" status
                if (bookings.getAllByMemberID((String) session.getAttribute("username")).size() > 0) {
                    for (BookingsBean bookingsBean : bookings.getAllByMemberID(username)) {
                        if (bookingsBean.getStatus().equals("Submitted")) {
                            bookingArr.add(bookingsBean);
                        }
                    }
                }

                //If there are no "Submitted" bookings then OK
                if (bookingArr.isEmpty()) {
                    if (status.equals(User.APPROVED)) {
                        if (balance == 0) {
                            String reference = UUID.randomUUID().toString().substring(0, 10);
                            BookingsBean booking = new BookingsBean();
                            booking.setPrice(Double.parseDouble(df.format(price)));
                            booking.setDate(new Date(System.currentTimeMillis()));
                            booking.setMemberID(username);
                            booking.setStatus(BookingsBean.SUBMITTED);
                            booking.setOrigin(origin);
                            booking.setDestination(destination);
                            booking.setDistance(miles);
                            booking.setDateOfTravel(date);
                            booking.setPassengers(passengers);
                            booking.setReference(reference);
                            bookings.create(booking);

                            currentBalance = tempUser.getBalance();
                            newBalance = Double.parseDouble(df.format(currentBalance + Double.parseDouble(df.format(price))));
                            tempUser.setBalance(newBalance);
                            members.updateBalance(tempUser);

                            String title = "Booking made";
                            String content = "Your booking from " + origin + " to " + destination + "  has been made. Total cost £" + price + "0 for " + miles + " miles. You are travelling on " + date 
                                    + ". Use reference " + reference;

                            emailModel.details(username, title, content);

                            request.setAttribute(SUCCESS_MSG, "Booking has been made");
                            request.setAttribute(DASH_MSG, "Your booking from " + origin + " to " + destination + " has been made. Total cost £" + price + "0 for " + miles + " miles");
                        } else { //If outstanding balance is greater than zero
                            request.setAttribute(ERR_MSG, "Booking could not be made");
                            request.setAttribute(DASH_MSG, "You have outstanding balance of £" + balance + "0, please pay it before making new bookings");
                        }
                    } else {
                        //If member not "Approved"
                        request.setAttribute(ERR_MSG, "Only Approved members can make bookings");
                    }
                } else if (!bookingArr.isEmpty()) {
                    //If there are "Submitted" bookings
                    request.setAttribute(WARNING_MSG, "You have unapproved bookings");
                }
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "editBooking": //Forward to booking editing
                request.setAttribute("editBooking", "asd");
                int tempID = Integer.parseInt(request.getParameter("editBookingID"));
                String tempMember = request.getParameter("editBookingMemberID");
                Date tempDot = Date.valueOf(request.getParameter("editBookingDot"));
                int tempPass = Integer.parseInt(request.getParameter("editBookingPassengers"));
                String tempReference = request.getParameter("editBookingReference");

                //Set fields in front-end
                request.setAttribute("bookingID", tempID);
                request.setAttribute("memberID", tempMember);
                request.setAttribute("dot", tempDot);
                request.setAttribute("passengers", tempPass);
                request.setAttribute("reference", tempReference);

                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "editABooking": //Edit a selected booking
                Date updatedDot = Date.valueOf(request.getParameter("dot"));
                double updatedPrice = 0;

                /** There can only be one "Submitted" booking 
                 * therefore that's the booking we are looking for
                 * so add it to the ArrayList
                 */
                if (bookings.getAllByMemberID((String) session.getAttribute("username")).size() > 0) {
                    for (BookingsBean bookingsBean : bookings.getAllByMemberID(username)) {
                        if (bookingsBean.getStatus().equals("Submitted")) {

                            bookingArr.add(bookingsBean);
                            int count = 0;
                            if (bookingArr.isEmpty()) {
                                System.out.println("ITS EMPTY");
                            } else {
                                System.out.println("ITS NOT EMPTY");
                                System.out.println(bookingArr);

                                for (BookingsBean bookingsBean1 : bookingArr) {
                                    count++;
                                }
                            }

                            System.out.println("COUNT: " + count);

                            //Trims the toString to retrieve the booking ID
                            try {
                                String test = String.valueOf(bookingArr.get(0)).substring(1);
                                String test1 = test.substring(1);

                                System.out.println("TEST STRING: " + test1);

                                if (Integer.parseInt(request.getParameter("passengers")) != tempBooking.getPassengers()) {
                                    System.out.println("NO");
                                } else {
                                    System.out.println("YES");
                                }

                                passengers = Integer.parseInt(request.getParameter("passengers"));
                                price = bookingsBean.getPrice();
                                System.out.println("PRICE: " + price);
                                miles = bookingsBean.getDistance();
                                System.out.println("MILES: " + miles);
                                System.out.println("PASSENGERS: " + passengers);

                                //Adjust price based on new mileage
                                if (miles <= 100) {
                                    price = 20;
                                } else if (miles >= 101) {
                                    price = 30;
                                }

                                //Adjust cost for passengers
                                if (passengers > 1) {
                                    updatedPrice = price + (((price / 2) * passengers) - (price / 2));
                                }
                                
                                //Update booking
                                System.out.println("UPDATED PRICE: " + updatedPrice);
                                tempBooking.setID(Integer.parseInt(test1));
                                tempBooking.setPassengers(Integer.parseInt(request.getParameter("passengers")));
                                tempBooking.setDateOfTravel(updatedDot);
                                tempBooking.setPrice(Double.parseDouble(df.format(updatedPrice)));

                                //Update user's balance to reflect the new booking price
                                currentBalance = tempUser.getBalance();
                                newBalance = tempBooking.getPrice();
                                tempUser.setBalance(newBalance);
                                members.updateBalance(tempUser);

                                bookings.updateBookingDot(tempBooking);
                                bookings.updateBookingPassengers(tempBooking);
                                bookings.updateBookingPrice(tempBooking);

                                String title = "Booking updated";
                                String content = "Your booking with ID " + test1 + " has been edited. You are travelling on " + updatedDot + ". Passengers: " + request.getParameter("passengers") + " and the total cost is £" + updatedPrice + " for " + miles + " miles";
                                emailModel.details(username, title, content);

                                System.out.println("TEST PASSENGERS: " + Integer.parseInt(request.getParameter("passengers")));
                                System.out.println("TEST DOT: " + updatedDot);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }
                
                request.setAttribute(SUCCESS_MSG, "Booking edited");
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "listPayments": //List payments for the user
                if (payments.getAllByMemberID((String) session.getAttribute("username")).size() > 0) {
                    request.setAttribute("listPayments", payments.getAllByMemberID(username));
                    request.setAttribute("DASH_MSG", "All payments for " + username);
                } else {
                    request.setAttribute("WARNING_MSG", "No payments for " + username);
                }

                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "listMadeBookings": //List bookings made by the user
                if (bookings.getAllByMemberID((String) session.getAttribute("username")).size() > 0) {
                    request.setAttribute("listMadeBookings", bookings.getAllByMemberID(username));
                    System.out.println("TEST: " + bookings.getAllByMemberID(username));
                    request.setAttribute("DASH_MSG", "All bookings for " + username);
                } else {
                    request.setAttribute("WARNING_MSG", "No bookings for " + username);
                }

                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "changePassword": //Password change process
                request.setAttribute("DASH_MSG", "Change password for user " + username);
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                break;
        }

//        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
