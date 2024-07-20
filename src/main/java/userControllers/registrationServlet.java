package userControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.svws_nrw.ext.jbcrypt.BCrypt;

@WebServlet("/register")
public class registrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String user = System.getenv("DB_USER");
    String dbPassword = System.getenv("DB_PASSWORD");
    String url = System.getenv("DB_URL");


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String re_password = request.getParameter("re_pass");
		String phone = request.getParameter("phone");
		
		RequestDispatcher dispatcher = null;
		Connection conn = null;
		
		if (username == null || username.equals("")) {
			request.setAttribute("status", "invalidName");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
		}
		
		if (email == null || email.equals("")) {
			request.setAttribute("status", "invalidEmail");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
		}
		
		if (password == null || password.equals("")) {
			request.setAttribute("status", "invalidPassword");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
		}
		
		else if (!password.equals(re_password)) {
			request.setAttribute("status", "invalidRePassword");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
		}
		
		if (phone == null || phone.equals("")) {
			request.setAttribute("status", "invalidPhone");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
		} 
		
		else if (phone.length() > 10) {
			request.setAttribute("status", "invalidPhoneLength");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			conn = DriverManager.getConnection(url, user, dbPassword);

			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			PreparedStatement stmt = conn.prepareStatement("insert into users (username,password,email,phone) values(?,?,?,?)");
			stmt.setString(1, username);
			stmt.setString(2, hashedPassword);
			stmt.setString(3, email);
			stmt.setString(4, phone);

			int rowCount = stmt.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (rowCount > 0) {
				request.setAttribute("status", "success");

			} else {
				request.setAttribute("status", "failed");
			}

			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}