package models;

import java.util.Objects;

public class Book {

    // Package-private instance variables to hold book data
	int id;
	String title;
	String author;
	String date;
	String genres;
	String characters;
	String synopsis;
	String imageURL;

    // Constructor to initialise Book object with all attributes
	public Book(int id, String title, String author, String date, String genres, String characters, String synopsis, String imageURL) {

		// Sets attributes to variables
		this.id = id;
		this.title = title;
		this.author = author;
		this.date = date;
		this.genres = genres;
		this.characters = characters;
		this.synopsis = synopsis;
		this.imageURL = imageURL;
		
	}
 
	public Book() {}

	// Getters and setters for attributes
		public int getID() {
			return id;
		}
	
		public void setID(int id) {
			this.id = id;
		}
	
		public String getTitle() {
			return title;
		}
	
		public void setTitle(String title) {
			this.title = title;
		}
	
		public String getAuthor() {
			return author;
		}
	
		public void setAuthor(String author) {
			this.author = author;
		}
	
		public String getDate() {
			return date;
		}
	
		public void setDate(String date) {
			this.date = date;
		}
	
		public String getGenres() {
			return genres;
		}
	
		public void setGenres(String genres) {
			this.genres = genres;
		}
	
		public String getCharacters() {
			return characters;
		}
	
		public void setCharacters(String characters) {
			this.characters = characters;
		}
	
		public String getSynopsis() {
			return synopsis;
		}
	
		public void setSynopsis(String synopsis) {
			this.synopsis = synopsis;
		}
		
		public String getImageURL() {
	        return imageURL;
	    }

	    public void setImageURL(String imageURL) {
	        this.imageURL = imageURL;
	    }
	    

	    // Override hashCode() method for proper hashing
	    @Override
	    public int hashCode() {
	        return Objects.hash(id); // Generate hash code based on the book's ID
	    }

	    // Override equals() method for comparing two Book objects
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true; // If comparing same object, return true
	        }
	        if (obj == null || getClass() != obj.getClass()) {
	            return false; // If object is null or not of type Book, return false
	        }
	        Book otherBook = (Book) obj; // Cast object to Book
	        return id == otherBook.id; // Return true if IDs are equal
	    }

	    // Override toString() method to provide string representation of Book object
		@Override
		public String toString() {
			return "Book [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date + ", genres=" + genres
					+ ", synopsis=" + synopsis + ", characters=" + characters + ", imageURL=" + imageURL + "]";
		}
}
