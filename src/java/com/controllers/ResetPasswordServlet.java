package com.controllers;

import com.models.Email;
import com.models.MembersDAO;
import com.models.Password;
import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
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
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/resetPassword"})
public class ResetPasswordServlet extends HttpServlet {

    MembersDAO members = new MembersDAO();
    Email emailModel = new Email();
    UserDAO users = new UserDAO();
    Password pw = new Password();
    public static String SUCCESS_MSG = "SUCCESS_MSG";
    public static String ERR_MSG = "ERR_MSG";

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

        request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
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
        } else {
            session.invalidate();
            session = null;
        }

        String username = request.getParameter("username");
        String newPassword = null;
        String password;

        ArrayList<User> usersArr = new ArrayList<>();
        User temp = members.getByID(username);
        if (!usersArr.contains(temp)) {
            usersArr.add(temp);
        }

        try {
            //Carry out the password reset, generate new random password and notify user via email
            if (temp.getUsername().equals(username)) {
                newPassword = UUID.randomUUID().toString().substring(0, 6);

                password = pw.hashPassword(newPassword);

                User tempUser = new User();
                tempUser.setPassword(password);
                tempUser.setUsername(username);
                tempUser.setReset(true);

                users.updatePassword(tempUser);
                users.updateReset(tempUser);

                String title = "Password reset";
                String content = "Your new password is " + newPassword + " please log in and change it as soon as possible";

                emailModel.details(username, title, content);

                request.setAttribute(SUCCESS_MSG, "Your password has been reset, please check your email");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (NullPointerException e) {
            request.setAttribute(ERR_MSG, "Your username doesn't exist in the database");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
