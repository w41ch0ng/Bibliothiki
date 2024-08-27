package bookControllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.BookDAO;

@WebServlet("/Remove")
public class Remove extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session or create one if it doesn't exist
        HttpSession session = request.getSession();
        
        // Retrieve user ID from session
        Integer userID = (Integer) session.getAttribute("id");

        // Get "bookID" parameter from request  ID of book to remove)
        String bookIdParam = request.getParameter("bookID");
        
        // If "bookID" parameter is missing or empty, send a 400 Bad Request error
        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Convert "bookID" parameter to an integer (book ID)
        int bookID = Integer.parseInt(bookIdParam);

        // Create instance of BookDAO to interact with database
        BookDAO bookDAO = new BookDAO();
        
        // Remove book from user's reading list in database
        bookDAO.removeBookFromReadingList(userID, bookID);

        // Redirect user back to reading list page to see updated list
        response.sendRedirect(request.getContextPath() + "/ReadingList");
    }
}