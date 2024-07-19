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
<title>Bibliothiki Terms</title>
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

	<div class="container mt-3 d-sm-flex terms-container">
		<div class="terms-image">
			<figure>
				<img class="terms-image-size" src="images/terms.svg" alt="terms">
			</figure>
		</div>
		<div class="terms-content">
			<p>1. Just please don't mess with my project.</p>
			<div class="form-group form-button">
					  <a href="registration.jsp" class="form-submit">Back to Sign Up</a>
					</div>
		</div>
		
	</div>
    
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