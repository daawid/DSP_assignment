package com.controllers;

import com.models.BookingsBean;
import com.models.BookingsDAO;
import com.models.Database;
import com.models.Email;
import com.models.MembersDAO;
import com.models.PaymentDAO;
import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
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
@WebServlet(name = "AdminDashServlet", urlPatterns = {"/admin-dashboard"})
public class AdminDashServlet extends HttpServlet {

    Database db = new Database();
    MembersDAO members = new MembersDAO();
    UserDAO users = new UserDAO();
    User user = new User();
    BookingsDAO bookings = new BookingsDAO();
    PaymentDAO payments = new PaymentDAO();
    Calendar cal;
    DecimalFormat df = new DecimalFormat("#.##");
    Email emailModel = new Email();
    String origin, destination, username, reference;
    Date dot;

    double membershipFee = 0;
    double bookingsDouble = 0;
    int usersInt = 0;

    /**
     * Dashboard messages *
     */
    String DASH_MSG = "DASH_MSG";
    String LOGIN_MSG = "LOGIN_MSG";
    String ERR_MSG = "ERR_MSG";
    String WARNING_MSG = "WARNING_MSG";
    String SUCCESS_MSG = "SUCCESS_MSG";

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

        request.getRequestDispatcher("admin-dashboard").forward(request, response);
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

        /**
         * Read input from admin-dashboard.jsp *
         */
        String button = request.getParameter("function"); // Reads the function group from the JSP and casts it to String button

        switch (button) {
            case "listMembers": //Show a list of all members
                request.setAttribute("DASH_MSG", "All members");
                request.setAttribute("listMembers", members.getAll());
                break;

            case "listOutstandingBalances": //List balances greater than zero
                request.setAttribute("DASH_MSG", "All outstanding payments");
                request.setAttribute("listOutstandingBalances", members.listBalances());
                break;

            case "listBookings": //List all bookings
                request.setAttribute("DASH_MSG", "All bookings");
                request.setAttribute("listBookings", bookings.getAll());
                break;

            case "listUnapprovedBookings": //List all unapproved bookings
                request.setAttribute("DASH_MSG", "All unapproved bookings");
                request.setAttribute("listBookings", bookings.getAllByStatus("Submitted"));
                break;

            case "listSuspendedMembers": //List all "Suspended" members
                request.setAttribute("DASH_MSG", "All suspended users");
                List<User> membersarr = members.getAllByStatus("Suspended");

                request.setAttribute("listSuspendedMembers", membersarr);
                break;

            case "approveBooking": //Approve a selected booking
                String approveBooking = request.getParameter("approveBooking");
                request.setAttribute("SUCCESS_MSG", "Booking " + approveBooking + " approved");

                if (approveBooking != null) {
                    BookingsBean tempBooking = new BookingsBean();
                    tempBooking.setID(Integer.parseInt(approveBooking));
                    tempBooking.setStatus(BookingsBean.APPROVED);
                    bookings.updateStatus(tempBooking);

                    username = request.getParameter("approveBookingMember");
                    origin = request.getParameter("approveBookingOrigin");
                    destination = request.getParameter("approveBookingDestination");
                    dot = Date.valueOf(request.getParameter("approveBookingDot"));
                    reference = request.getParameter("approveBookingReference");

                    String title = "Booking approved";
                    String content = "Your booking from " + origin + " to " + destination + " with reference " + reference + " has been approved. You are travelling on " + dot;
                    emailModel.details(username, title, content);
                }
                break;

            case "rejectBooking": //Reject a selected booking
                String rejectBooking = request.getParameter("rejectBooking");
                request.setAttribute("SUCCESS_MSG", "Booking " + rejectBooking + " rejected");

                if (rejectBooking != null) {
                    BookingsBean tempBooking = new BookingsBean();
                    tempBooking.setID(Integer.parseInt(rejectBooking));
                    System.out.println("Booking " + tempBooking);
                    tempBooking.setStatus(BookingsBean.REJECTED);
                    bookings.updateStatus(tempBooking);

                    username = request.getParameter("rejectBookingMember");
                    origin = request.getParameter("rejectBookingOrigin");
                    destination = request.getParameter("rejectBookingDestination");
                    reference = request.getParameter("rejectBookingReference");

                    String title = "Booking rejected";
                    String content = "Your booking from " + origin + " to " + destination + " with reference " + reference + " has been rejected";
                    emailModel.details(username, title, content);
                }

                break;

            case "resumeMembership": //Resume a user's membership
                String resumeMembership = request.getParameter("resumeMembership");
                request.setAttribute("SUCCESS_MSG", "User " + resumeMembership + " unsuspended");

                if (resumeMembership != null) {
                    User tempUser = new User();
                    tempUser.setUsername(resumeMembership);
                    tempUser.setStatus(User.APPROVED);
                    members.updateStatus(tempUser);
                    users.updateStatus(tempUser);

                    String title = "Account unsuspended";
                    String content = "Your account has been unsuspended, you may resume making bookings";
                    emailModel.details(resumeMembership, title, content);
                }

                break;

            case "suspendMembership": //Suspend a user's membership
                String suspendMembership = request.getParameter("suspendMembership");
                request.setAttribute("SUCCESS_MSG", "User " + suspendMembership + " suspended");
                System.out.println("uID: " + suspendMembership);

                if (suspendMembership != null) {
                    User tempUser = new User();
                    tempUser.setUsername(suspendMembership);
                    tempUser.setStatus(User.SUSPENDED);
                    members.updateStatus(tempUser);
                    users.updateStatus(tempUser);

                    String title = "Account suspended";
                    String content = "Your account has been suspended, you may no longer make bookings";
                    emailModel.details(suspendMembership, title, content);
                }

                break;

        }
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
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
