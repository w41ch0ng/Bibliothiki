package models;

public class User {

    // Package-private instance variables to hold user data
	int id;
	String username;
	String password;
	String email;
	String phone;

    // Constructor to initialise User object with all attributes
	public User(int id, String username, String password, String email, String phone) {

		// Sets attributes to variables
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		
	}

	public User() {}
	
	// Getters and setters for attributes
		public int getID() {
			return id;
		}
	
		public void setID(int id) {
			this.id = id;
		}
	
		public String getUsername() {
			return username;
		}
	
		public void setUsername(String username) {
			this.username = username;
		}
	
		public String getPassword() {
			return password;
		}
	
		public void setPassword(String password) {
			this.password = password;
		}
	
		public String getEmail() {
			return email;
		}
	
		public void setEmail(String email) {
			this.email = email;
		}
	
		public String getPhone() {
			return phone;
		}
	
		public void setPhone(String phone) {
			this.phone = phone;
		}

	// Override toString() method to provide string representation of User object
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", phone=" + phone
				+ "]";
	}

}
