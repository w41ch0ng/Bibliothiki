package userControllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.UserDAO;
import models.User;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
        	// Get user ID from form data sent in request
	        int id = Integer.parseInt(request.getParameter("id"));

	        // Create UserDAO instance to interact with database
	        UserDAO userDAO = new UserDAO();
	        // Fetch user from the database using provided ID
	        User user = userDAO.getUserByID(id);

	        // Check if user exists
	        if (user != null) {
	        	
	            try {
	                // Delete user from database
	                userDAO.deleteUser(id);

	                // Get current session (if it exists)
	                HttpSession session = request.getSession(false);
	                if (session != null) {
	                    // Log out user by invalidating session
	                    session.invalidate();
	                }

	                // Redirect user to home page (index.jsp)
	                response.sendRedirect("index.jsp");
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } 
	    }
	}