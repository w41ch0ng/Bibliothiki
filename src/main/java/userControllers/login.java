package userControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.svws_nrw.ext.jbcrypt.BCrypt;

@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    // Database connection credentials from environment variables
	String user = System.getenv("DB_USER");
    String dbPassword = System.getenv("DB_PASSWORD");
    String url = System.getenv("DB_URL");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve email and password parameters from request
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
        // Initialise session and dispatcher
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		Connection conn = null;

        // Check if email is missing
		if (email == null || email.equals("")) {
			request.setAttribute("status", "invalidEmail"); // Set error status for invalid email
            dispatcher = request.getRequestDispatcher("login.jsp"); // Forward to login page
            dispatcher.forward(request, response);
		}

        // Check if password is missing
		if (password == null || password.equals("")) {
			request.setAttribute("status", "invalidPassword"); // Set error status for invalid password
            dispatcher = request.getRequestDispatcher("login.jsp"); // Forward to login page
            dispatcher.forward(request, response);
		}
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection(url, user, dbPassword); // Establish database connection
			
            // Prepare SQL query to fetch user details based on provided email
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
			stmt.setString(1,  email);

			ResultSet rs = stmt.executeQuery();

            // Check if user with provided email exists
			if (rs.next()) {
                // Retrieve hashed password from database
                String hashedPasswordFromDatabase = rs.getString("password");

                // Check if provided password matches hashed password
                if (BCrypt.checkpw(password, hashedPasswordFromDatabase)) {
                    // If password matches, store user information in session
                    session.setAttribute("name", rs.getString("username"));
                    session.setAttribute("id", rs.getInt("id"));
                    // Redirect to home page
                    response.sendRedirect("index.jsp");
                } else {
                    // If password does not match, set status and forward to login page
                    request.setAttribute("status", "failed");
                    dispatcher = request.getRequestDispatcher("login.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                // If user does not exist, set status and forward to login page
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
		}	catch (Exception e) {
			e.printStackTrace();
		} finally {
            // Ensure database connection is closed
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
