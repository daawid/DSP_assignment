package com.controllers;

import com.models.MembersDAO;
import com.models.Password;
import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
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
@WebServlet(name = "ChangePassword", urlPatterns = {"/ChangePassword"})
public class ChangePassword extends HttpServlet {

    //Dashboard messages
    String DASH_MSG = "DASH_MSG";
    String LOGIN_MSG = "LOGIN_MSG";
    String SUCCESS_MSG = "SUCCESS_MSG";
    String ERR_MSG = "ERR_MSG";
    String error = "";
    UserDAO users = new UserDAO();
    MembersDAO members = new MembersDAO();
    Password pw = new Password();

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
        response.setContentType("text/html;charset=UTF-8");

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

        //If session doesn't exist then
        if (session == null) {
            session = request.getSession();
        }

        //Display the change password page
        request.getRequestDispatcher("change-password.jsp").forward(request, response);

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
        //If session doesn't exist then
        if (session == null) {
            session = request.getSession();
        }
        
        //Get information from password change page
        String username = (String) session.getAttribute("username");

        request.setAttribute("changePassword", "asd");
        request.setAttribute("DASH_MSG", "Change password for user " + username);

        String currentPassword = (String) request.getParameter("currentPassword");
        String newPassword = (String) request.getParameter("newPassword");
        String confirmNewPassword = (String) request.getParameter("confirmNewPassword");
        
        User tempUser = new User();
        String password;
        
        //If 'reset' field is true
        if (tempUser.getReset() == true) {
            tempUser.setUsername(username);
            if (newPassword.equals(confirmNewPassword)) {
                password = pw.hashPassword(newPassword);

                tempUser.setPassword(password);
                tempUser.setReset(false);
                users.updatePassword(tempUser);
                users.updateReset(tempUser);

                request.setAttribute(SUCCESS_MSG, "Password for user " + username + " changed");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                request.setAttribute(ERR_MSG, error);
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
            }
            //If 'reset' field is false and password passed validation
        } else if (tempUser.getReset() == false && validatePassword(currentPassword, newPassword, confirmNewPassword, username)) {
            password = pw.hashPassword(newPassword);
            tempUser.setPassword(password);
            tempUser.setUsername(username);
            users.updatePassword(tempUser);
            tempUser.setReset(false);
            users.updateReset(tempUser);

            request.setAttribute(SUCCESS_MSG, "Password for user " + username + " changed");
            request.getRequestDispatcher("index.jsp").forward(request, response);

            if (tempUser.getReset() == true) {
                tempUser.setReset(false);
                users.updateReset(tempUser);
            }
        } else {
            request.setAttribute(ERR_MSG, error);
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
        }
    }

    //Password validation conditions
    private boolean validatePassword(String currentPassword, String newPassword, String confirmPassword, String username) {
        User usr = users.getByID(username);
        
        //Is username valid?
        if (usr == null) {
            error += "Invalid username";
            return false;
        }

        //Sets current password
        if (pw.checkPassword(currentPassword, usr.getPassword())) {
            currentPassword = usr.getPassword();
        }

        //Does password match database version?
        if (!usr.getPassword().equals(currentPassword)) {
            error += "Current password does not match";
            return false;
        }

        //Does new password match current password?
        if (!newPassword.equals(confirmPassword)) {
            error += "Passwords do not match";
            return false;
        }

        //Is new password the same as the old password?
        if (newPassword.equals(usr.getPassword())) {
            error += "New password cannot be the same as old password";
            return false;
        }
        return true;
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
