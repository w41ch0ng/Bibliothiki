package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.User;

import de.svws_nrw.ext.jbcrypt.BCrypt;

public class UserDAO {
	
	User oneUser = null;
	Connection conn = null;
    Statement stmt = null;
    
    // Database connection credentials
    String user = System.getenv("DB_USER");
    String password = System.getenv("DB_PASSWORD");
    String url = System.getenv("DB_URL");

    public UserDAO() {}

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

    // Method to extract a User object from the result set	
	private User getNextUser(ResultSet rs){
		
    	User thisUser=null;
		
    	try {
			// Create new User object using data from current row in result set
			thisUser = new User(
					rs.getInt("id"),
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("email"),
					rs.getString("phone"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return thisUser; // Return created User object	
	}

	// Method to retrieve all users from database
    public ArrayList<User> getAllUsers(){
	   
		ArrayList<User> allUsers = new ArrayList<User>(); // Create an empty list to store all users
		openConnection(); // Open database connection
		
		try{
			// Prepare and execute a SQL query to select all users
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
		    ResultSet rs1 = stmt.executeQuery(); // Execute query and get result set
		    while(rs1.next()){
		    	oneUser = getNextUser(rs1); // Get next User object from result set
		    	allUsers.add(oneUser); // Add user to list
		   }

		    stmt.close(); // Close statement
		    closeConnection();  // Close database connection
		} catch(SQLException se) { System.out.println(se); }

		return allUsers; // Return the list of all users
   }

    // Method to retrieve user by their ID
    public User getUserByID(int id){
	   
		openConnection(); // Open database connection
		oneUser=null;
		
		try{
		    // Prepare and execute SQL query to select user by their ID
	        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id=?");
	        pstmt.setInt(1, id); // Set ID in query
		    ResultSet rs1 = pstmt.executeQuery(); // Execute query and get result set
		    while(rs1.next()){
		    	oneUser = getNextUser(rs1); // Get User object from result set
		    }

		    pstmt.close(); // Close statement
		    closeConnection(); // Close database connection
		} catch(SQLException se) { System.out.println(se); }

		return oneUser;
   }

    // Method to insert new user into database
    public void insertUser(User user) {
    	
	    openConnection(); // Open database connection

	    try {
	        // Prepare and execute SQL query to insert new user
	        PreparedStatement stmt = conn.prepareStatement(
	            "INSERT INTO users (id, username, password, email, phone)" +
	            "VALUES (?, ?, ?, ?, ?)");
	        stmt.setInt(1, user.getID());
	        stmt.setString(2, user.getUsername());
	        stmt.setString(3, user.getPassword());
	        stmt.setString(4, user.getEmail());
	        stmt.setString(5, user.getPhone());
	        stmt.executeUpdate(); // Execute update query
	        stmt.close(); // Close statement
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(); // Close database connection
	    }
	}

    // Method to update existing user in database
    public Boolean updateUser(User user, boolean updatePassword) throws SQLException {
    	
	    openConnection(); // Open database connection

	    try {
			// Prepare and execute SQL query to update existing user
	        PreparedStatement stmt;
	        if (updatePassword) {
		        // If user updating password, hash new password and pass alongside other parameters to update
	            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
	            stmt = conn.prepareStatement("UPDATE users SET Username = ?, Password = ?, Email = ?, Phone = ? WHERE ID = ?;");
		        stmt.setString(1, user.getUsername());
	            stmt.setString(2, hashedPassword);
		        stmt.setString(3, user.getEmail());
		        stmt.setString(4, user.getPhone());
		        stmt.setInt(5, user.getID());
	        } else {
	        	// If not, update parameters without updating password to maintain existing password hash
	            stmt = conn.prepareStatement("UPDATE users SET Username = ?, Email = ?, Phone = ? WHERE ID = ?;");
		        stmt.setString(1, user.getUsername());
		        stmt.setString(2, user.getEmail());
		        stmt.setString(3, user.getPhone());
		        stmt.setInt(4, user.getID());
	        }
	        
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
    public Boolean deleteUser(int id) throws SQLException {
    	
	    openConnection(); // Open database connection
	    int result = 0;
	   
	    try {
			// Prepare and execute SQL query to delete user
	    	PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?;");
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

    // Helper method to search for books by search string (in title or author)
    public ArrayList<User> searchUser(String searchStr) {
    	
	    ArrayList<User> matchingUsers = new ArrayList<User>(); // Create empty list to store matching users
	    openConnection(); // Open database connection
	    
	    try {
	        // Prepare and execute SQL query to search for users by username or email
	        PreparedStatement stmt = conn.prepareStatement
	        ("SELECT * FROM users WHERE Username LIKE ? OR Email LIKE ?");
	        stmt.setString(1, "%" + searchStr + "%");
	        stmt.setString(2, "%" + searchStr + "%");
	        ResultSet rs = stmt.executeQuery(); // Execute search query

	        while (rs.next()) {
	            User user = getNextUser(rs); // Get next User object from result set
	            matchingUsers.add(user); // Add user to list of matching users
	        }

	        stmt.close(); // Close statement
	        closeConnection(); // Close database connection
	    } catch (SQLException se) {
	        System.out.println(se);
	    }

	    return matchingUsers; // Return the list of matching users
	    
	}

    // Method to get next available ID for new user
    public int getNextAvailableID() throws SQLException {
    	
	    int nextID = 0;
	    openConnection(); // Open database connection
	    
	    try (
			 // Prepare and execute SQL query to get maximum ID in users table
	         PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS max_id FROM users");
	         ResultSet resultSet = stmt.executeQuery()) {
	        if (resultSet.next()) {
	            nextID = resultSet.getInt("max_id") + 1; // Set the next ID to one more than the maximum ID
	        } closeConnection(); // Close database connection
	    }
	    return nextID; // Return next ID
	}
   
}