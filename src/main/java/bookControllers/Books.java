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
    	String bookIdParam = request.getParameter("id");

        if (bookIdParam != null && bookIdParam.matches("\\d+")) {
            int id = Integer.parseInt(bookIdParam);
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookByID(id);
            if (book != null) {
                request.setAttribute("book", book);
                request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            BookDAO bookDAO = new BookDAO();
            ArrayList<Book> allBooks = bookDAO.getAllBooks();
            request.setAttribute("allBooks", allBooks);
            request.getRequestDispatcher("books.jsp").forward(request, response);
        }
    }
}