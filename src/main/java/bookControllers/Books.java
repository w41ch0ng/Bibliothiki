package bookControllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.BookDAO;
import models.Book;

@WebServlet("/Books")
public class Books extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get "id" parameter from the request (URL query parameter)
    	String bookIdParam = request.getParameter("id");

        // Check if "id" parameter is present and is a valid number
        if (bookIdParam != null && bookIdParam.matches("\\d+")) {
            // Convert the "id" parameter to an integer (book ID)
            int id = Integer.parseInt(bookIdParam);
            // Create an instance of BookDAO to interact with database
            BookDAO bookDAO = new BookDAO();
            // Retrieve the book with ID from database
            Book book = bookDAO.getBookByID(id);
            // Check if the book exists
            if (book != null) {
                // Set book as attribute in the request
                request.setAttribute("book", book);
                // Forward request and response to "bookDetails.jsp" to display book details
                request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
            } else {
                // If book is not found, send 404 Not Found error
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // If no valid "id" parameter is provided, retrieve all books
            BookDAO bookDAO = new BookDAO();
            ArrayList<Book> allBooks = bookDAO.getAllBooks();
            // Set the list of all books as an attribute in the request
            request.setAttribute("allBooks", allBooks);
            // Forward request and response to "books.jsp" to display list of books
            request.getRequestDispatcher("books.jsp").forward(request, response);
        }
    }
}