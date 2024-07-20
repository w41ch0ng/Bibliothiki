<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
String name = (String) session.getAttribute("name");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>About Bibliothiki</title>
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

<section class="about-1">

	<div class="container mt-3 d-sm-flex">
		
		<div class="about-content">
			<h3 class="about-title">About the Bibliothiki Project</h3>
				<p>The Bibliothiki project was developed using a wide range of technologies, including <span class="bold">Java</span> and 
				 <span class="bold">JavaScript</span>, <span class="bold">MySQL</span>, as the DBMS, and <span class="bold">Bootstrap</span> as a 
				 front-end framework. This project serves as a Full-Stack demonstration, with front-end, back-end, and database
				 implementations.</p>
		</div>
		<div class="about-image">
			<figure>
				<img src="images/web-development.svg" alt="web-development">
			</figure>
		</div>
	</div>
	
</section>

<section class="about-2">

	<div class="container mt-3 d-sm-flex">
		
		<div class="about-image about-image-even">
			<figure>
				<img src="images/javascript-frameworks.svg" alt="javascript-frameworks">
			</figure>
		</div>
		<div class="about-content-opposite">
			<h3 class="about-title">Back-End Technologies</h3>
				<p><span class="bold">Java</span> and <span class="bold">Java Servlets</span> formed the backbone of the project's server-side implementation to handle user interactions and data processing. 
				<span class="bold">JavaScript</span>, including libraries such as <span class="bold">jQuery</span>, were instrumental in delivering an interactive user experience, allowing for dynamic content and smooth user interactions, such as asynchronous searches.</p>
		</div>
	</div>
	
</section>

<section class="about-3">

	<div class="container mt-3 d-sm-flex">
		
		<div class="about-content">
			<h3 class="about-title">Data Management</h3>
				<p>For data storage and retrieval, <span class="bold">MySQL</span> is utilised to manage data-sets (such as a table with <span class="bold">5</span> whole books!) while ensuring data integrity and security.
				<span class="bold">jBCrypt</span> has also played an integral role in this project's data management, allowing for secure user <span class="bold">password hashing</span> to ensure security 
				standards are being met.</p>
		</div>
		<div class="about-image">
			<figure>
				<img src="images/server.svg" alt="server">
			</figure>
		</div>
	</div>
	
</section>

<section class="about-4">

	<div class="container mt-3 d-sm-flex">
		
		<div class="about-image about-image-even">
			<figure>
				<img src="images/assets.svg" alt="assets">
			</figure>
		</div>
		<div class="about-content-opposite">
			<h3 class="about-title">Front-End Development</h3>
				<p>Front-end development was centered around <span class="bold">HTML</span> and <span class="bold">CSS</span>. 
				A responsive and intuitive user interface is used, making navigation effortless for the <span class="bold">5</span> users that will end up checking this out. 
				Additionally, <span class="bold">Bootstrap</span> was leveraged, a popular CSS framework, to achieve responsiveness and compatibility across various devices.</p>
		</div>
	</div>
	
</section>

<section class="about-6">

	<div class="container mt-3 d-sm-flex">
		<div class="about-content">
			<h3 class="about-title">Illustration Credits</h3>
				<p>
				The illustrations used in the project are from the open source project <a href="https://undraw.co" 
            target="_blank"
            rel="noopener noreferrer">
    <span class="bold">unDraw</span>
</a> (huge credits!), and the favicon is from 
				<a href="https://icons8.com" 
            target="_blank"
            rel="noopener noreferrer">
    <span class="bold">icons8</span>
</a>.</p>

		</div>
		
		<div class="about-image">
			<figure>
				<img src="images/icons.svg" alt="icons">
			</figure>
		</div>
	</div>
	
</section>

<footer class="p-4 bg-light text-black text-center position-relative">
	<p class="lead">Copyright &copy; 2024 Bibliothiki</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>