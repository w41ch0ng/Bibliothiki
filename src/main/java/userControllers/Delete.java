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
	        int id = Integer.parseInt(request.getParameter("id"));

	        UserDAO userDAO = new UserDAO();
	        User user = userDAO.getUserByID(id);

	        if (user != null) {
	            try {
	                userDAO.deleteUser(id);

	                HttpSession session = request.getSession(false);
	                if (session != null) {
	                    session.invalidate();
	                }

	                response.sendRedirect("index.jsp");
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        } 
	    }
	}