// Function to create HTML card for single book
function createBookCard(book) {
  return `
    <div class="col-md-4 mt-4 mb-4">
      <div class="card book-card">
      <a href="/bibliothiki/Books?id=${book.id}">
        <img src="${book.imageURL}" class="card-img-top" alt="${book.title}">
        <div class="card-body">
          <h5 class="card-title">${book.title}</h5>
        </div>
      </a>
      </div>
    </div>
  `;
}

// Function to create container of book cards
function createBooksContainer(books) {
  return `
    <div class="container">
      <div class="row">
        ${books.map((book) => createBookCard(book)).join("")}
      </div>
    </div>
  `;
}

// Get HTML element with ID "booksContainer" to update its content
const testContainer = document.getElementById("booksContainer");
console.log(testContainer);

// Handle search form submission
function handleSearch(event) {
    event.preventDefault(); // Prevent form from submitting default
    const searchQuery = event.target.query.value.trim(); // Get search query from form input
  	// Check if search query is valid (non-empty & contains only letters, numbers, spaces)
    if (searchQuery.length > 0 && /^[a-zA-Z0-9\s]+$/.test(searchQuery)) {
      fetchSearchResults(searchQuery); // Fetch search results from the server
      return true;
    } else {
      alert("Please enter a valid search query."); // Show alert if query is invalid
      return false;
      }
	}

// Fetch search results from server based on query
function fetchSearchResults(query) {
  fetch(`/bibliothiki/BookAPI?search=${encodeURIComponent(query)}`)
    .then((response) => response.json()) // Convert response to JSON
    .then((data) => {
      console.log("Fetched search results:", data);
      const booksData = data;
      const booksContainer = createBooksContainer(booksData); // Create HTML for book cards
      document.getElementById("booksContainer").innerHTML = booksContainer; // Update the container with book cards
    })
    .catch((error) => console.error("Error fetching search results:", error));
}

// Fetch all books from the server and display them
function fetchAllBooks() {
  fetch("/bibliothiki/BookAPI")
    .then((response) => response.json()) // Convert the response to JSON
    .then((data) => {
      console.log("Fetched all data:", data);
      const booksData = data; // Store the fetched book data
      const booksContainer = createBooksContainer(booksData); // Create HTML for book cards
      document.getElementById("booksContainer").innerHTML = booksContainer; // Update the container with book cards
    })
    .catch((error) => console.error("Error fetching all book data:", error));
}

// Initially fetch all books when the page loads
fetchAllBooks();