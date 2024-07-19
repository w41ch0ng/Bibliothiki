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
	    PrintWriter out = response.getWriter();
	    BookDAO dao = new BookDAO();
	    ArrayList<Book> allBooks = dao.getAllBooks();
	    
	    String search = request.getParameter("search");
	    if (search != null) {
	        
	        if (search.matches("\\d+")) {
	            
	            int id = Integer.parseInt(search);
	            Book book = dao.getBookByID(id);
	            allBooks = (book != null) ? new ArrayList<Book>(Arrays.asList(book)) : new ArrayList<Book>();
	        } else {
	            allBooks = dao.searchBook(search);
	        }
	    } else {
	        allBooks = dao.getAllBooks();
	    }
	        Gson gson = new Gson();
	        response.setContentType("application/json"); 
	        String json = gson.toJson(allBooks);
	        out.write(json);
	    
	    out.close();
	}
	
	@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
		
		BookDAO bookDAO = new BookDAO();

   PrintWriter out = response.getWriter();
	
       Gson gson = new Gson();
       Book book = gson.fromJson(request.getReader(), Book.class);
       int nextID = 0;
	try {
		nextID = bookDAO.getNextAvailableID();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       book.setID(nextID);
       response.setContentType("application/json"); 
       String json = gson.toJson(book);
       out.write(json);
       bookDAO.insertBook(book);
       out.write("\nBook uploaded: " + book.toString());
       out.close();
   }


@Override
protected void doPut(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
	
   BookDAO bookDAO = new BookDAO();
   PrintWriter out = response.getWriter();
       Gson gson = new Gson();
       Book book = gson.fromJson(request.getReader(), Book.class);
       response.setContentType("application/json"); 
       String json = gson.toJson(book);
       out.write(json);
       try {
		bookDAO.updateBook(book);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       out.write("\nBook updated: " + book.toString());
       out.close();
   }


@Override
protected void doDelete(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
		BookDAO bookDAO = new BookDAO();
		PrintWriter out = response.getWriter();
		        Gson gson = new Gson();
		        Book book = gson.fromJson(request.getReader(), Book.class);
		        response.setContentType("application/json");
		        try {
					bookDAO.deleteBook(book.getID());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        out.write("Book deleted: Book " + book.getID());
		        out.close();
		    }

}