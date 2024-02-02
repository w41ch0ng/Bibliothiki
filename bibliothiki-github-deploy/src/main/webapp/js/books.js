console.log("books.js is loaded!");
function createBookCard(book) {
  return `
    <div class="col-md-4 mt-4 mb-4">
      <div class="card book-card">
      <a href="/Books?id=${book.id}">
        <img src="${book.imageURL}" class="card-img-top" alt="${book.title}">
        <div class="card-body">
          <h5 class="card-title">${book.title}</h5>
        </div>
      </a>
      </div>
    </div>
  `;
}


function createBooksContainer(books) {
  return `
    <div class="container">
      <div class="row">
        ${books.map((book) => createBookCard(book)).join("")}
      </div>
    </div>
  `;
}

const testContainer = document.getElementById("booksContainer");
console.log(testContainer);

function handleSearch(event) {
    event.preventDefault();
    const searchQuery = event.target.query.value.trim();
    if (searchQuery.length > 0 && /^[a-zA-Z0-9\s]+$/.test(searchQuery)) {
      fetchSearchResults(searchQuery);
      return true;
    } else {
      alert("Please enter a valid search query.");
      return false;
      }
	}

function fetchSearchResults(query) {
  fetch(`/BookAPI?search=${encodeURIComponent(query)}`)
    .then((response) => response.json())
    .then((data) => {
      console.log("Fetched search results:", data);
      const booksData = data;
      const booksContainer = createBooksContainer(booksData);
      document.getElementById("booksContainer").innerHTML = booksContainer;
    })
    .catch((error) => console.error("Error fetching search results:", error));
}

function fetchAllBooks() {
  fetch("/BookAPI")
    .then((response) => response.json())
    .then((data) => {
      console.log("Fetched all data:", data);
      const booksData = data;
      const booksContainer = createBooksContainer(booksData);
      document.getElementById("booksContainer").innerHTML = booksContainer;
    })
    .catch((error) => console.error("Error fetching all book data:", error));
}

fetchAllBooks();