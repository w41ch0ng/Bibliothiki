<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.User" %>
<%@ page import="database.UserDAO" %>
  
<% 
String name = (String) session.getAttribute("name");
User user = (User) request.getAttribute("user");
%>  
<%
	if(session.getAttribute("name")==null){
		response.sendRedirect("login.jsp");
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Your Account</title>
<link rel="icon" type="image/x-icon" href="https://img.icons8.com/metro/26/6C63FF/book.png">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<link rel="stylesheet"	href="fonts/material-icon/css/material-design-iconic-font.min.css">
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="alert/dist/sweetalert.css">
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

	<section class="signup">
			<div class="container mt-3">
				<div class="signup-content">
					<div class="signup-form">
						<h2 class="form-title">Update your details</h2>
					
						<form method="post" action="Update" class="register-form"
							id="register-form">
							<div class="form-group">
								<label for="name"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="name" id="name" placeholder="Your Name" value="${user.username}"/>
							</div>
							<div class="form-group">
								<label for="email"><i class="zmdi zmdi-email"></i></label> <input
									type="email" name="email" id="email" placeholder="Your Email" value="${user.email}"/>
							</div>
							<div class="form-group">
								<label for="pass"><i class="zmdi zmdi-lock"></i></label> <input
									type="password" name="pass" id="pass" placeholder="New Password"/>
							</div>
							<div class="form-group">
								<label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
								<input type="password" name="re_pass" id="re_pass"
									placeholder="Repeat Password"/>
							</div>
							<div class="form-group">
								<label for="phone"><i class="zmdi zmdi-phone"></i></label>
								<input type="text" name="phone" id="phone"
									placeholder="Phone number" value="${user.phone}"/>
							</div>
							<input type="hidden" name="id" value="<%= ((User) request.getAttribute("user")).getID() %>">
							<div class="form-group form-button">
								<input type="submit" name="update" id="update"
									class="form-submit" value="Update" />
							</div>
						</form>
            <div class="delete-form">
                <form method="post" action="Delete" class="delete-form" id="delete-form">
                    <div class="form-group form-button">
                        <input type="hidden" name="id" value="<%= ((User) request.getAttribute("user")).getID() %>" />
                        <input type="submit" name="delete" id="delete" class="form-submit delete-remove-button" value="Delete Account" onclick="confirmDelete(event)" />
                    </div>
                </form>
            </div>
					</div>
					<div class="signup-image">
						<figure>
							<img src="images/update.png" alt= "update image">
						</figure>
					</div>
				</div>
			</div>
		</section>
	
<footer class="p-4 bg-light text-black text-center position-relative">
	<p class="lead">Copyright &copy; 2024 Bibliothiki</p>
</footer>
	
	<!-- JS -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>