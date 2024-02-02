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

        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
	    UserDAO userDAO = new UserDAO();
	    User user = userDAO.getUserByID(id);
	    request.setAttribute("user", user);
	    request.getRequestDispatcher("accountPage.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int id = Integer.parseInt(request.getParameter("id"));
	    String username = request.getParameter("name");
	    String email = request.getParameter("email");
	    String phone = request.getParameter("phone");
	    String newPassword = request.getParameter("pass");

	    UserDAO userDAO = new UserDAO();
	    User user = userDAO.getUserByID(id);

	    if (newPassword != null && !newPassword.isEmpty()) {
	        user.setPassword(newPassword);
	    }

	    user.setUsername(username);
	    user.setEmail(email);
	    user.setPhone(phone);

	    try {
	        userDAO.updateUser(user, newPassword != null && !newPassword.isEmpty());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    response.sendRedirect("index.jsp");
	}

}