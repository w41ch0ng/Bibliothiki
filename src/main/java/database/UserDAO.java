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
    String user = System.getenv("DB_USER");
    String password = System.getenv("DB_PASSWORD");
    String url = System.getenv("DB_URL");

    public UserDAO() {}
	
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

		
	private User getNextUser(ResultSet rs){
    	User thisUser=null;
		try {
			
			thisUser = new User(
					rs.getInt("id"),
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("email"),
					rs.getString("phone"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return thisUser;		
	}
	
	
   public ArrayList<User> getAllUsers(){
	   
		ArrayList<User> allUsers = new ArrayList<User>();
		openConnection();
		
		try{
			PreparedStatement stmt = conn.prepareStatement("select * from users");
		    ResultSet rs1 = stmt.executeQuery();
		    while(rs1.next()){
		    	oneUser = getNextUser(rs1);
		    	allUsers.add(oneUser);
		   }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

		return allUsers;
   }
   

   public User getUserByID(int id){
	   
		openConnection();
		oneUser=null;
		try{
		    String selectSQL = "select * from users where id="+id;
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    while(rs1.next()){
		    	oneUser = getNextUser(rs1);
		    }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

		return oneUser;
   }
   
   
   public void insertUser(User user) {
	    openConnection();

	    try {
	        PreparedStatement stmt = conn.prepareStatement(
	            "INSERT INTO users (id, username, password, email, phone)" +
	            "VALUES (?, ?, ?, ?, ?)");
	        stmt.setInt(1, user.getID());
	        stmt.setString(2, user.getUsername());
	        stmt.setString(3, user.getPassword());
	        stmt.setString(4, user.getEmail());
	        stmt.setString(5, user.getPhone());
	        stmt.executeUpdate();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection();
	    }
	}
   
   
   
   public Boolean updateUser(User user, boolean updatePassword) throws SQLException {
	    openConnection();

	    try {
	        PreparedStatement stmt;
	        if (updatePassword) {
	            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
	            stmt = conn.prepareStatement("UPDATE users SET Username = ?, Password = ?, Email = ?, Phone = ? WHERE ID = ?;");
		        stmt.setString(1, user.getUsername());
	            stmt.setString(2, hashedPassword);
		        stmt.setString(3, user.getEmail());
		        stmt.setString(4, user.getPhone());
		        stmt.setInt(5, user.getID());
	        } else {
	            stmt = conn.prepareStatement("UPDATE users SET Username = ?, Email = ?, Phone = ? WHERE ID = ?;");
		        stmt.setString(1, user.getUsername());
		        stmt.setString(2, user.getEmail());
		        stmt.setString(3, user.getPhone());
		        stmt.setInt(4, user.getID());
	        }
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
   
   
   public Boolean deleteUser(int id) throws SQLException {
	    openConnection();
	    int result = 0;
	   
	    try {
	    	PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?;");
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
   
   
   public ArrayList<User> searchUser(String searchStr) {
	    ArrayList<User> matchingUsers = new ArrayList<User>();
	    openConnection();
	    
	    try {
	        PreparedStatement stmt = conn.prepareStatement
	        ("SELECT * FROM users WHERE Username LIKE ? OR Email LIKE ?");
	        stmt.setString(1, "%" + searchStr + "%");
	        stmt.setString(2, "%" + searchStr + "%");
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            User user = getNextUser(rs);
	            matchingUsers.add(user);
	        }

	        stmt.close();
	        closeConnection();
	    } catch (SQLException se) {
	        System.out.println(se);
	    }

	    return matchingUsers;
	    
	}
   
   
   public int getNextAvailableID() throws SQLException {
	    int nextID = 0;
	    openConnection();
	    try (
	         PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS max_id FROM users");
	         ResultSet resultSet = stmt.executeQuery()) {
	        if (resultSet.next()) {
	            nextID = resultSet.getInt("max_id") + 1;
	        } closeConnection();
	    }
	    return nextID;
	}
   
}