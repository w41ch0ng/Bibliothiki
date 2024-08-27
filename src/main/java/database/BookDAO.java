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
    
    // Database connection credentials from environment variables
    String user = System.getenv("DB_USER");
    String password = System.getenv("DB_PASSWORD");
    String url = System.getenv("DB_URL");

    public BookDAO() {}

    // Method to open connection to database
    private void openConnection() {
    	
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            
            // Establish connection using database URL, username, and password
            conn = DriverManager.getConnection(url, user, password);
            
            // Create a statement object for executing SQL queries
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Method to close the connection to the database
    private void closeConnection() {
    	
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to extract a Book object from the result set
	private Book getNextBook(ResultSet rs){
		
    	Book thisBook=null;
    	
		try {
			// Create new Book object using data from current row in result set
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
		
    	return thisBook; // Return created Book object
	}

	// Method to retrieve all books from database
    public ArrayList<Book> getAllBooks(){
	   
		ArrayList<Book> allBooks = new ArrayList<Book>(); // Create an empty list to store all books
		openConnection(); // Open database connection
		
		try {
			// Prepare and execute a SQL query to select all books
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books");
		    ResultSet rs1 = stmt.executeQuery(); // Execute query and get result set
		    while(rs1.next()){
		    	oneBook = getNextBook(rs1); // Get next Book object from result set
		    	allBooks.add(oneBook); // Add book to list
		   }

		    stmt.close();  // Close statement
		    closeConnection();  // Close database connection
		} catch(SQLException se) { System.out.println(se); }
		
		return allBooks; // Return the list of all books
   }

    // Method to retrieve book by its ID
    public Book getBookByID(int id) {
    	
	     openConnection(); // Open database connection
	     oneBook = null;
	     
	     try {
		     // Prepare and execute SQL query to select book by its ID
	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE id=?");
	         pstmt.setInt(1, id); // Set ID in query
	         ResultSet rs1 = pstmt.executeQuery(); // Execute query and get result set
	         while (rs1.next()) {
	            oneBook = getNextBook(rs1); // Get Book object from result set
	         }
	         
		     pstmt.close(); // Close statement
		     closeConnection(); // Close database connection
	     } catch (SQLException se) {
	         System.out.println(se);
	     }
	     return oneBook;
   }

   // Method to insert new book into database
   public void insertBook(Book book) {
	   
	    openConnection(); // Open database connection

	    try {
	        // Prepare and execute SQL query to insert new book
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
	        stmt.executeUpdate(); // Execute update query
	        stmt.close(); // Close statement
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(); // Close database connection
	    }
	}

   // Method to update existing book in database
   public Boolean updateBook(Book book) throws SQLException {
	   	
	   openConnection(); // Open database connection

		try {
			// Prepare and execute SQL query to update existing book
			PreparedStatement stmt = conn.prepareStatement(
			"UPDATE books SET ID = ?, Title = ?, Author = ?, Date = ?, Genres = ?, Characters = ?, Synopsis = ?, imageURL = ? WHERE ID = ?;");
			stmt.setInt(1, book.getID());
		    stmt.setString(2, book.getTitle());
		    stmt.setString(3, book.getAuthor());
		    stmt.setString(4, book.getDate());
		    stmt.setString(5, book.getGenres());
		    stmt.setString(6,  book.getCharacters());
		    stmt.setString(7, book.getSynopsis());
		    stmt.setString(8, book.getImageURL());
		    stmt.setInt(9, book.getID());
			stmt.executeUpdate(); // Execute the update query

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return false; // Return false if the update failed

		} finally {

			if (stmt != null) {
				stmt.close(); // Close statement
			}
			closeConnection(); // Close database connection
		}
		return true;
	}

   // Method to delete book from database
   public Boolean deleteBook(int id) throws SQLException {
	   
	    openConnection(); // Open database connection
	    int result = 0;
	   
	    try {
			// Prepare and execute SQL query to delete book
	    	PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?;");
	        stmt.setInt(1, id);
	        result = stmt.executeUpdate(); // Execute the delete query
	    } finally {
	        if (stmt != null) {
	            stmt.close(); // Close statement
	        }
	        closeConnection(); // Close database connection
	    }
	    if (result == 1) { // Check if one row was deleted
	        return true; // Return true if the deletion was successful
	    } else {
	        return false; // Return false if no rows were deleted
	    }
	}

   // Method to search for books by search string (in title or author)
   public ArrayList<Book> searchBook(String searchStr) {
	   
	    ArrayList<Book> matchingBooks = new ArrayList<Book>(); // Create empty list to store matching books
	    openConnection(); // Open database connection
	    
	    try {
	        // Prepare and execute SQL query to search for books by title or author
	        PreparedStatement stmt = conn.prepareStatement
	        ("SELECT * FROM books WHERE Title LIKE ? OR Author LIKE ?");
	        stmt.setString(1, "%" + searchStr + "%");
	        stmt.setString(2, "%" + searchStr + "%");
	        ResultSet rs = stmt.executeQuery(); // Execute search query

	        while (rs.next()) {
	            Book book = getNextBook(rs); // Get next Book object from result set
	            matchingBooks.add(book); // Add book to list of matching books
	        }

	        stmt.close(); // Close statement
	        closeConnection(); // Close database connection
	    } catch (SQLException se) {
	        System.out.println(se);
	    }

	    return matchingBooks; // Return the list of matching books
	    
	}

   // Method to get next available ID for new book
   public int getNextAvailableID() throws SQLException {
	   
	    int nextID = 0;
	    openConnection(); // Open database connection
	    
	    try (
		     // Prepare and execute SQL query to get maximum ID in books table
	         PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS max_id FROM books");
	         ResultSet resultSet = stmt.executeQuery()) {
	        if (resultSet.next()) {
	            nextID = resultSet.getInt("max_id") + 1; // Set the next ID to one more than the maximum ID
	        } closeConnection(); // Close database connection
	    }
	    return nextID; // Return next ID
	}

   // Method to get reading list of user by their ID
   public List<Book> getReadingListByUserID(int userId) {
	   
	    List<Book> readingList = new ArrayList<>(); // Create an empty list to store the reading list
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    openConnection(); // Open database connection

	    try {
	    	// Check if the user exists
	        if (userExists(userId)) {
	        	// Prepare and execute SQL query to get reading list of user
	            preparedStatement = conn.prepareStatement
	            		("SELECT b.id, b.title, b.author, b.date, b.genres, b.characters, b.synopsis, b.imageURL " +
	                    "FROM books b " +
	                    "INNER JOIN user_reading_list ur ON b.id = ur.book_id " +
	                    "WHERE ur.user_id = ?");
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

	                // Create new Book object and add it to reading list
	                Book book = new Book(id, title, author, date, genres, characters, synopsis, imageURL);
	                readingList.add(book);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(); // Close database connection
	    }

	    return readingList; // Return the reading list
	}
   
   // Method to check if user exists by ID
   private boolean userExists(int userId) throws SQLException {
		
	    try (
	    	// SQL query to check for user
	    	PreparedStatement preparedStatement = conn.prepareStatement("SELECT 1 FROM users WHERE id = ?")) {
	        preparedStatement.setInt(1, userId); // Set user ID in query
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            return resultSet.next(); // Return true if user exists, false otherwise
	        }
	    }
	}

   // Method to check if book exists by ID
   private boolean bookExists(int bookId) throws SQLException {
		
	    try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT 1 FROM books WHERE id = ?")) {
	        preparedStatement.setInt(1, bookId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            return resultSet.next();
	        }
	    }
	}

   // Method to add book to user's reading list
   public void addBookToReadingList(int userId, int bookId) {
		
	    PreparedStatement preparedStatement = null;
	    openConnection(); // Open database connection

	    try {
	        if (userExists(userId) && bookExists(bookId)) { // Check if both user and book exist
	            preparedStatement = conn.prepareStatement("INSERT INTO user_reading_list (user_id, book_id) VALUES (?, ?)");
	            preparedStatement.setInt(1, userId);
	            preparedStatement.setInt(2, bookId);

	            preparedStatement.executeUpdate(); // Execute insert query
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(); // Close database connection
	    }
	}

   // Method to remove a book from a user's reading list
   public void removeBookFromReadingList(int userId, int bookId) {
		
	    PreparedStatement preparedStatement = null;
	    openConnection(); // Open database connection

	    try {
	        if (userExists(userId) && bookExists(bookId)) { // Check if both the user and book exist
	            preparedStatement = conn.prepareStatement("DELETE FROM user_reading_list WHERE user_id = ? AND book_id = ?");
	            preparedStatement.setInt(1, userId);
	            preparedStatement.setInt(2, bookId);

	            preparedStatement.executeUpdate(); // Execute the delete query
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(); // Close database connection
	    }
	}

}
