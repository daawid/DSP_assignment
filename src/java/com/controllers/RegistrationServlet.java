package com.controllers;

import com.models.AddressBean;
import com.models.Email;
import com.models.Password;
import com.models.RegistrationModel;
import com.models.User;
import com.models.UserDAO;
import com.services.AddressAPI;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
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
@WebServlet(name = "Registration", urlPatterns = {"/Registration"})
public class RegistrationServlet extends HttpServlet {

    public static String LOGIN_MSG = "LOGIN_MSG";
    public static String ERR_MSG = "ERR_MSG";
    public static String SUCCESS_MSG = "SUCCESS_MSG";
    Email emailModel = new Email();
    Password pw = new Password();
    UserDAO users = new UserDAO();

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

        request.getRequestDispatcher("index.jsp").forward(request, response);

        HttpSession session = request.getSession(false);

        //If session doesn't exist then create it
        if (session == null) {
            session = request.getSession();
        }
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

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");

        String button = request.getParameter("submit");
        ArrayList<AddressBean> addresses = null;

        switch (button) {
            case "lookupPostcode": //Uses 'AddressAPI' to lookup the provided postcode
                String postcode = request.getParameter("postcode");
                AddressAPI addressAPI = new AddressAPI(postcode);
                addresses = addressAPI.lookupAddress();

                //If API found no addresses then allow manual entering
                if (addresses.isEmpty()) {
                    request.setAttribute("manualAddress", true);
                    System.out.println(request.getAttribute("manualAddress"));
                }

                //Save data after lookup is complete
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("addresses", addresses);
                request.setAttribute("dob", dob);
                request.setAttribute("email", email);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                break;

            case "createAccount": //Create user account based on input
                HttpSession session = request.getSession(false);
                String address = request.getParameter("address");

                //Checks if user email already exists in the database
                if (users.getByID(email) != null) {
                    request.setAttribute("ERR_MSG", "The email " + email + " is already registered");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else {
                    //If email doesn't exist and passwords match then register account
                    if (password.equals(repeatPassword)) {
                        password = pw.hashPassword(password);

                        RegistrationModel registrationModel = new RegistrationModel(firstName, lastName, dob, address, email, password);
                        User user = registrationModel.createAccount();

                        String login_message = "Thanks for registering " + user.getFirstName() + " " + user.getLastName()
                                + ", you are now able to make bookings and payments."
                                + " A confirmation email has been sent.";
                        request.setAttribute(LOGIN_MSG, login_message);
                        request.setAttribute(SUCCESS_MSG, "Account " + user.getEmail() + " registered");

                        String title = "Account registered";
                        String content = "Thank you for registering with ABC travel " + firstName + " " + lastName + ". Your account has been created. "
                                + "Address: " + address + ". Date of birth: " + dob + ". Date of registration: " + new Date(System.currentTimeMillis());

                        emailModel.details(email, title, content);

                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        request.setAttribute(ERR_MSG, "Passwords do not match");
                        request.getRequestDispatcher("register.jsp").forward(request, response);
                    }
                }
                break;
        }
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
