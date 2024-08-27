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

import de.svws_nrw.ext.jbcrypt.BCrypt;

@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve current session
        HttpSession session = request.getSession();
        
        // Get user ID from session
        Integer id = (Integer) session.getAttribute("id");
        
        // Create UserDAO instance to interact with database
	    UserDAO userDAO = new UserDAO();
	    
	    // Get user by ID
	    User user = userDAO.getUserByID(id);
	    
        // Set user object as a request attribute to be accessed in JSP page
	    request.setAttribute("user", user);

        // Forward request to accountPage.jsp to display user's information
	    request.getRequestDispatcher("accountPage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve parameters from the form submission
		int id = Integer.parseInt(request.getParameter("id"));
	    String username = request.getParameter("name");
	    String email = request.getParameter("email");
	    String phone = request.getParameter("phone");
	    String newPassword = request.getParameter("pass");

        // Create UserDAO instance to interact with database
	    UserDAO userDAO = new UserDAO();
	    
	    // Get user by ID
	    User user = userDAO.getUserByID(id);

        // Update the user's information
	    if (newPassword != null && !newPassword.isEmpty()) {
            // If new password is provided, update user object with new password
	        user.setPassword(newPassword);
	    }

        // Update the other user details
	    user.setUsername(username);
	    user.setEmail(email);
	    user.setPhone(phone);

	    try {
            // Call updateUser method of UserDAO to update user in database
	        userDAO.updateUser(user, newPassword != null && !newPassword.isEmpty());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

        // Redirect to home page after updating
	    response.sendRedirect("index.jsp");
	}

}