package models;

public class User {

	int id;
	String username;
	String password;
	String email;
	String phone;
	
	
	public User(int id, String username, String password, String email, String phone) {

		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		
	}

	public User() {}

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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", phone=" + phone
				+ "]";
	}

}
