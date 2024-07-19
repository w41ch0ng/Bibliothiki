package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Book;

public class BookDAO {
	
	Book oneBook = null;
	Connection conn = null;
    Statement stmt = null;
    ResultSet result = null;
    String user = System.getProperty("DB_USER");
    String password = System.getProperty("DB_PASSWORD");
    String url = System.getProperty("DB_URL");

    public BookDAO() {}

    private void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		
	private Book getNextBook(ResultSet rs){
    	Book thisBook=null;
		try {
			
			thisBook = new Book(
					rs.getInt("id"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("date"),
					rs.getString("genres"),
					rs.getString("characters"),
					rs.getString("synopsis"),
					rs.getString("imageURL"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return thisBook;		
	}
	
	
   public ArrayList<Book> getAllBooks(){
	   
		ArrayList<Book> allBooks = new ArrayList<Book>();
		openConnection();
		
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from books");
		    ResultSet rs1 = stmt.executeQuery();
		    while(rs1.next()){
		    	oneBook = getNextBook(rs1);
		    	allBooks.add(oneBook);
		   }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }
		
		return allBooks;
   }
   

   public Book getBookByID(int id) {
	    openConnection();
	    oneBook = null;
	    try {
	        String selectSQL = "SELECT * FROM books WHERE id=?";
	        PreparedStatement pstmt = conn.prepareStatement(selectSQL);
	        pstmt.setInt(1, id);
	        ResultSet rs1 = pstmt.executeQuery();
	        while (rs1.next()) {
	            oneBook = getNextBook(rs1);
	        }
	        pstmt.close();
	        closeConnection();
	    } catch (SQLException se) {
	        System.out.println(se);
	    }
	    return oneBook;
	}

   
   public void insertBook(Book book) {
	    openConnection();

	    try {
	        PreparedStatement stmt = conn.prepareStatement(
	            "INSERT INTO books (id, title, author, date, genres, characters, synopsis, imageURL) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	        stmt.setInt(1, book.getID());
	        stmt.setString(2, book.getTitle());
	        stmt.setString(3, book.getAuthor());
	        stmt.setString(4, book.getDate());
	        stmt.setString(5, book.getGenres());
	        stmt.setString(6, book.getCharacters());
	        stmt.setString(7, book.getSynopsis());
	        stmt.setString(8, book.getImageURL());
	        stmt.executeUpdate();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection();
	    }
	}
   
   
   public Boolean updateBook(Book book) throws SQLException {
	   openConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE books SET ID = ?, Title = ?, Author = ?, Date = ?, Genres = ?, Characters = ?, Synopsis = ?, imageURL = ? WHERE ID = ?;");
     stmt.setInt(1, book.getID());
     stmt.setString(2, book.getTitle());
     stmt.setString(3, book.getAuthor());
     stmt.setString(4, book.getDate());
     stmt.setString(5, book.getGenres());
     stmt.setString(6,  book.getCharacters());
     stmt.setString(7, book.getSynopsis());
     stmt.setString(8, book.getImageURL());
     stmt.setInt(9, book.getID());
			stmt.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false;

		} finally {

			if (stmt != null) {
				stmt.close();
			}
			closeConnection();
		}
		return true;
	}
   
   
   public Boolean deleteBook(int id) throws SQLException {
	    openConnection();
	    int result = 0;
	   
	    try {
	    	PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?;");
	        stmt.setInt(1, id);
	        result = stmt.executeUpdate();
	    } finally {
	        if (stmt != null) {
	            stmt.close();
	        }
	        closeConnection();
	    }
	    if (result == 1) {
	        return true;
	    } else {
	        return false;
	    }
	}
   
   
   public ArrayList<Book> searchBook(String searchStr) {
	    ArrayList<Book> matchingBooks = new ArrayList<Book>();
	    openConnection();
	    
	    try {
	        PreparedStatement stmt = conn.prepareStatement
	        ("SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ?");
	        stmt.setString(1, "%" + searchStr + "%");
	        stmt.setString(2, "%" + searchStr + "%");
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Book book = getNextBook(rs);
	            matchingBooks.add(book);
	        }

	        stmt.close();
	        closeConnection();
	    } catch (SQLException se) {
	        System.out.println(se);
	    }

	    return matchingBooks;
	    
	}
   
   
   public int getNextAvailableID() throws SQLException {
	    int nextID = 0;
	    openConnection();
	    try (
	         PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS max_id FROM books");
	         ResultSet resultSet = stmt.executeQuery()) {
	        if (resultSet.next()) {
	            nextID = resultSet.getInt("max_id") + 1;
	        } closeConnection();
	    }
	    return nextID;
	}
   
   public List<Book> getReadingListByUserID(int userId) {
	    List<Book> readingList = new ArrayList<>();
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    openConnection();

	    try {
	    	
	        if (userExists(userId)) {
	            String query = "SELECT b.id, b.title, b.author, b.date, b.genres, b.characters, b.synopsis, b.imageURL " +
	                    "FROM books b " +
	                    "INNER JOIN user_reading_list ur ON b.id = ur.book_id " +
	                    "WHERE ur.user_id = ?";
	            preparedStatement = conn.prepareStatement(query);
	            preparedStatement.setInt(1, userId);
	            resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String title = resultSet.getString("title");
	                String author = resultSet.getString("author");
	                String date = resultSet.getString("date");
	                String genres = resultSet.getString("genres");
	                String characters = resultSet.getString("characters");
	                String synopsis = resultSet.getString("synopsis");
	                String imageURL = resultSet.getString("imageURL");

	                Book book = new Book(id, title, author, date, genres, characters, synopsis, imageURL);
	                readingList.add(book);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection();
	    }

	    return readingList;
	}
   
	private boolean userExists(int userId) throws SQLException {
	    String query = "SELECT 1 FROM users WHERE id = ?";
	    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
	        preparedStatement.setInt(1, userId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            return resultSet.next();
	        }
	    }
	}

   
	public void addBookToReadingList(int userId, int bookId) {
	    PreparedStatement preparedStatement = null;
	    openConnection();

	    try {
	        if (userExists(userId) && bookExists(bookId)) {
	            String query = "INSERT INTO user_reading_list (user_id, book_id) VALUES (?, ?)";
	            preparedStatement = conn.prepareStatement(query);
	            preparedStatement.setInt(1, userId);
	            preparedStatement.setInt(2, bookId);

	            preparedStatement.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection();
	    }
	}

	private boolean bookExists(int bookId) throws SQLException {
	    String query = "SELECT 1 FROM books WHERE id = ?";
	    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
	        preparedStatement.setInt(1, bookId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            return resultSet.next();
	        }
	    }
	}

   
	public void removeBookFromReadingList(int userId, int bookId) {
	    PreparedStatement preparedStatement = null;
	    openConnection();

	    try {
	    	
	        if (userExists(userId) && bookExists(bookId)) {
	            String query = "DELETE FROM user_reading_list WHERE user_id = ? AND book_id = ?";
	            preparedStatement = conn.prepareStatement(query);
	            preparedStatement.setInt(1, userId);
	            preparedStatement.setInt(2, bookId);

	            preparedStatement.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection();
	    }
	}

}
