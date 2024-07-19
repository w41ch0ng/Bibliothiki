package models;

import java.util.Objects;

public class Book {

	int id;
	String title;
	String author;
	String date;
	String genres;
	String characters;
	String synopsis;
	String imageURL;
	
	
	public Book(int id, String title, String author, String date, String genres, String characters, String synopsis, String imageURL) {

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
	    
	    
	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null || getClass() != obj.getClass()) {
	            return false;
	        }
	        Book otherBook = (Book) obj;
	        return id == otherBook.id;
	    }

		@Override
		public String toString() {
			return "Book [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date + ", genres=" + genres
					+ ", synopsis=" + synopsis + ", characters=" + characters + ", imageURL=" + imageURL + "]";
		}
}
