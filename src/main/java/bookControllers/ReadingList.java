package bookControllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.BookDAO;
import models.Book;
import database.UserDAO;
import models.User;

@WebServlet("/ReadingList")
public class ReadingList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Get current session or create one if it doesn't exist
        HttpSession session = request.getSession();
        
        // Retrieve user ID from session
        Integer id = (Integer) session.getAttribute("id");
        
        // Create instance of BookDAO to interact with database
        BookDAO bookDAO = new BookDAO();
        
        // Retrieve reading list for user with given ID
        List<Book> readingList = bookDAO.getReadingListByUserID(id);

        // Set reading list as attribute in request
        request.setAttribute("readingList", readingList);
        
        // Forward request and response to "readingList.jsp" to display reading list
        request.getRequestDispatcher("readingList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get current session or create one if it doesn't exist
        HttpSession session = request.getSession();
        
        // Retrieve user ID from session
        Integer userID = (Integer) session.getAttribute("id");

        // Get "bookID" parameter from request (ID of book to add)
        String bookIdParam = request.getParameter("bookID");
        
        // If "bookID" parameter is missing or empty, send 400 Bad Request error
        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Convert "bookID" parameter to integer (book ID)
        int bookID = Integer.parseInt(bookIdParam);
        
        // Create instance of BookDAO to interact with database
        BookDAO bookDAO = new BookDAO();
        
        // Add book to user's reading list in database
        bookDAO.addBookToReadingList(userID, bookID);

        // Redirect user back to reading list page to see updated list
        response.sendRedirect(request.getContextPath() + "/ReadingList");
    }
}