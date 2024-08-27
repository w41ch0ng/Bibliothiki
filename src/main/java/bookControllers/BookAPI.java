package bookControllers;

import database.BookDAO;
import models.Book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/BookAPI")
public class BookAPI extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
		// Get PrintWriter object to send text responses to client
	    PrintWriter out = response.getWriter();
	    
	    // New instance of BookDAO to interact with database
	    BookDAO dao = new BookDAO();
	    
        // Retrieve all books from the database
	    ArrayList<Book> allBooks = dao.getAllBooks();

        // Get 'search' parameter from request (URL query parameter)
	    String search = request.getParameter("search");
	    
        // If search parameter is provided
	    if (search != null) {

            // Check if search parameter is a number (ID)
	        if (search.matches("\\d+")) {

                // Convert search string to an integer (book ID)
	            int id = Integer.parseInt(search);
	            
                // Retrieve book with this ID from database
	            Book book = dao.getBookByID(id);
	            
                // If book exists, return it as single-item list
                // Otherwise, return empty list
	            allBooks = (book != null) ? new ArrayList<Book>(Arrays.asList(book)) : new ArrayList<Book>();
	        } else {
                // If search parameter is not number, search by other fields (like title or author)
	            allBooks = dao.searchBook(search);
	        }
	    } else {
            // If no search parameter is provided, return all books
	        allBooks = dao.getAllBooks();
	    }
        	// Convert list of books to JSON format
	        Gson gson = new Gson();
	        response.setContentType("application/json"); // Set response type to JSON
	        String json = gson.toJson(allBooks);
	        
	        // Write JSON string to response
	        out.write(json);

	     // Close PrintWriter
	     out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

        // Create instance of BookDAO to interact with database
        BookDAO bookDAO = new BookDAO();

        // Get PrintWriter object to send text responses to client
        PrintWriter out = response.getWriter();

        // Create Gson object to convert JSON to Java objects and vice versa
        Gson gson = new Gson();

        // Convert JSON body of request to Book object
        Book book = gson.fromJson(request.getReader(), Book.class);

        // Initialise next available ID for new book
        int nextID = 0;
        
        try {
            // Get next available ID from database
            nextID = bookDAO.getNextAvailableID();
        } catch (SQLException e) {
            // Print stack trace if SQL exception occurs
            e.printStackTrace();
        }

        // Set new book's ID
        book.setID(nextID);

        // Convert book object to JSON
        response.setContentType("application/json"); // Set response type to JSON
        String json = gson.toJson(book);

        // Write JSON string to response
        out.write(json);

        // Insert new book into database
        bookDAO.insertBook(book);

        // Write confirmation message to response
        out.write("\nBook uploaded: " + book.toString());

        // Close PrintWriter
        out.close();
    }
	
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create instance of BookDAO to interact with database
        BookDAO bookDAO = new BookDAO();

        // Get PrintWriter object to send text responses to client
        PrintWriter out = response.getWriter();

        // Create Gson object to convert JSON to Java objects and vice versa
        Gson gson = new Gson();

        // Convert JSON body of request to Book object
        Book book = gson.fromJson(request.getReader(), Book.class);

        // Convert book object to JSON
        response.setContentType("application/json"); // Set response type to JSON
        String json = gson.toJson(book);

        // Write JSON string to the response
        out.write(json);

        try {
            // Update book in database
            bookDAO.updateBook(book);
        } catch (SQLException e) {
            // Print stack trace if an SQL exception occurs
            e.printStackTrace();
        }

        // Write a confirmation message to the response
        out.write("\nBook updated: " + book.toString());

        // Close the PrintWriter
        out.close();
    }
	
	
	@Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Create instance of BookDAO to interact with database
        BookDAO bookDAO = new BookDAO();

        // Get PrintWriter object to send text responses to client
        PrintWriter out = response.getWriter();

        // Create Gson object to convert JSON to Java objects and vice versa
        Gson gson = new Gson();

        // Convert JSON body of request to Book object
        Book book = gson.fromJson(request.getReader(), Book.class);

        // Set response type to JSON
        response.setContentType("application/json");

        try {
            // Delete book from database using its ID
            bookDAO.deleteBook(book.getID());
        } catch (SQLException e) {
            // Print stack trace if an SQL exception occurs
            e.printStackTrace();
        }

        // Write a confirmation message to the response
        out.write("Book deleted: Book " + book.getID());

        // Close the PrintWriter
        out.close();
    }
}