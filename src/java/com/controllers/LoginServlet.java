package com.controllers;

import com.models.LoginModel;
import com.models.Password;
import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    //Message of user entered wrong details
    public static String ERR_MSG = "ERR_MSG";
    Password pw = new Password();
    UserDAO users = new UserDAO();

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

        request.getRequestDispatcher("index.jsp").forward(request, response);
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

        /** If first connection then create new session
         * if logging in again the invalidate and create session
         * this prevents instances of being able to bypass filters
         */
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        } else {
            session.invalidate();
            session = null;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginModel lm = new LoginModel(username, password);

        //Validates user and assigns to correct dashboard
        if (lm.validateUser(username, password) == true) {
            if (session == null) {
                session = request.getSession();
            }
            session.setAttribute("user", lm.getStatus().trim());
            session.setAttribute("username", lm.getUsername().trim());

            ArrayList<User> usersArr = new ArrayList<>();
            User temp = users.getByID(username);
            if (!usersArr.contains(temp)) {
                usersArr.add(temp);
            }

            /** If password is set for a password 'reset' then show change password page
             * otherwise, if user is an admin show admin dashboard
             * if not then check if account is suspended
             * if account is not admin or suspended then show member dashboard
             */
            if (temp.getReset() == true) {
                response.sendRedirect(request.getContextPath() + "/change-password.jsp");
            } else if (lm.getStatus().trim().equalsIgnoreCase("ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/admin-dashboard.jsp");
                System.out.println(request.getContextPath());
            } else if (lm.getStatus().trim().equalsIgnoreCase("Suspended")) {
                request.setAttribute(ERR_MSG, "Your account is suspended");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/member-dashboard.jsp");
            }
        } else {
            request.setAttribute(ERR_MSG, "Username or password was incorrect");
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
