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
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        
        BookDAO bookDAO = new BookDAO();
        List<Book> readingList = bookDAO.getReadingListByUserID(id);

        request.setAttribute("readingList", readingList);
        request.getRequestDispatcher("readingList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("id");

        String bookIdParam = request.getParameter("bookID");
        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int bookID = Integer.parseInt(bookIdParam);
        
        BookDAO bookDAO = new BookDAO();
        bookDAO.addBookToReadingList(userID, bookID);

        response.sendRedirect(request.getContextPath() + "/ReadingList");
    }
    
}
