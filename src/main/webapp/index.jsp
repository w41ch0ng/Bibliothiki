<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Book" %>
<%@ page import="database.BookDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
String name = (String) session.getAttribute("name");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bibliothiki</title>
<link rel="icon" type="image/x-icon" href="https://img.icons8.com/metro/26/6C63FF/book.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
<link rel="stylesheet" href="css/style.css">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>
<body>
<nav class="navbar navbar-expand-lg">
		<div class="container navbar-container">
		
		<a href="index.jsp" aria-label="Bibliothiki" class="navbar-brand">
		<img src="images/books-logo.svg" alt="logo" width="42" height="32">
		Bibliothiki</a>
		
			<button class="navbar-toggler" type ="button" data-bs-toggle="collapse" data-bs-target="#navmenu" aria-label="Toggle navigation">
				<span class = "navbar-toggler-icon"></span>
			
			</button>
		
			<div class="collapse navbar-collapse" id = "navmenu">
				<ul class="navbar-nav ms-auto">
				<li class="nav-item">
                        <a href="Books" class="nav-link">Books</a>
                    </li>
                    <li class="nav-item">
                        <a href="about.jsp" class="nav-link">About</a>
                    </li>
					<%-- Check if the user is logged in --%>
                <% if (name == null) { %>
                    <li class="nav-item">
                        <a href="login.jsp" class="nav-link">Login</a>
                    </li>
                    <li class="nav-item">
                        <a href="registration.jsp" class="nav-link">Sign Up</a>
                    </li>
                <% } else { %>
                    <li class="nav-item">
                        <a href="ReadingList" class="nav-link">Your Reading List</a>
                    </li>
                    <li class="nav-item dropdown" data-bs-theme="light">
                        <a class="nav-link dropdown-toggle" href="#" role="button" id="dropdownMenuButtonLight" data-bs-toggle="dropdown" aria-expanded="false">
                            <%= session.getAttribute("name") %>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButtonLight">
                            <li><a class="dropdown-item" href="Update">Your Account</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="logout" >Log out</a></li>
                        </ul>
                    </li>
                <% } %>
            </ul>
			</div>
		</div>
</nav>

<section class="index-1">

	<div class="container mt-3 d-sm-flex index-1-full-content">
		
		<div class="index-content index-content-1">
			<h2>Welcome to Bibliothiki.</h2>
				<p>The library has over 5 books to view. No one said it was the best library. Or even a good one.
					You also can't purchase any of these books as this isn't a real library, simply a project. But at least you can save
					them to a dedicated reading list. We have that.</p>
					<div class="form-group form-button">
					  <a href="Books" class="form-submit">Browse Books</a>
					</div>

		</div>
		<div class="index-image">
			<figure>
				<img src="images/book-lover.svg" alt="book-lover">
			</figure>
		</div>
	</div>
	
</section>

<section class="newsletter">
			<div class="container mt-3 d-md-flex newsletter">
				<div class="index-content">
						<h3 class="form">Sign up to our Newsletter</h3>
							<p>It's not supposed to work; there isn't a newsletter.</p>
				</div>
							<div class="newsletter-form">
				<form class="newsletter-form" id="newsletter-form">
								<label for="email"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="email" id="email"
									placeholder="E-mail" required="required"/>
				</form>
				<div class="form-group form-button">
								<input type="submit" name="newsletter-sign-up" id="newsletter-sign-up"
									class="newsletter-signup-button" value="Sign Up to the Newsletter" />
				</div>
							</div>
			</div>
</section>
		
<section class="index-3">

	<div class="container mt-3 d-sm-flex">
	
		<div class="index-3-image">
			<figure>
				<img src="images/index-to-about.svg" alt="book-lover">
			</figure>
		</div>
		
		<div class="index-content index-content-3">
			<h2>About the Bibliothiki Project</h2>
				<p>The Bibliothiki library project was made as a Cloud Development, Full-Stack demonstration,
				 utilising different technologies.</p>
					<div class="form-group form-button">
					  <a href="about.jsp" class="form-submit">Learn more about Bibliothiki</a>
					</div>

		</div>
		
	</div>
</section>

<footer class="p-4 bg-light text-black text-center position-relative">
	<p class="lead">Copyright &copy; 2024 Bibliothiki</p>
</footer>

	<!-- JS -->
<script src="https://unpkg.com/react@17.0.2/umd/react.development.js"></script>
<script src="https://unpkg.com/react-dom@17.0.2/umd/react-dom.development.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>