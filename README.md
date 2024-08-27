<a id="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  
[![Book][book-logo]](https://github.com/w41ch0ng/Bibliothiki)

<h3 align="center">Bibliothiki</h3>

  <p align="center">
    Bibliothiki, a Book Library MVC Java Application and RESTful API
    <br />
    <a href="https://github.com/w41ch0ng/Bibliothiki"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#features">Features</a></li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#credits">Credits</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

[![Bibliothiki][bibliothiki-one]](https://github.com/w41ch0ng/Bibliothiki)

Bibliothiki is a Book Library MVC Java Application and RESTful API. It is a Full-Stack application to demonstrate a book library project with a dedicated API.

This project was made with Java, JavaScript, MySQL and Bootstrap.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

- [![Java][Java]][Java-url]
- [![MySQL][MySQL]][MySQL-url]
- [![JavaScript][JavaScript]][JavaScript-url]
- [![Bootstrap][Bootstrap]][Bootstrap-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- FEATURES -->

## Features

[![Bibliothiki][bibliothiki]](https://github.com/w41ch0ng/Bibliothiki)

  <ol>
    <li>Account signup/login with password hashing registration system(Java/MySQL).</li>
    <li>Book and user reading list fetching.</li>
    <li>Custom API for users, books and users' reading lists.</li>
    <li>Update account details.</li>
    <li>Add or remove books from user's reading list.</li>
  </ol>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To set the project up locally, fork the project and import it into your preferred IDE.

Configure the build path with JavaSE-17 for the Modulepath and add the JAR files found in the WEB-INF/lib folder.

Set up a Tomcat (v9.0) server - documentation <a href="https://tomcat.apache.org/">here</a>.

Set up a local MySQL server - documentation <a href="https://dev.mysql.com/downloads/mysql/">here</a>.

Also download MySQL Workbench to work with the local server - found here <a href="https://www.mysql.com/products/workbench/">here</a>.

Configure login details for the environment variables that will be used to make a connection to the local DB.

After setting up the MySQL server instance, create a connection to it in MySQL workbench with the configured login details. You can leave the default schema or set one yourself.

After succesfully connecting, three tables must be created - 'books', 'users', and 'user_reading_list'.

For the 'books' table, enter this query in MySQL workbench:

![Books-query][books-query]

For the 'users' table, enter this query:

![User-query][user-query]

For the 'user-reading-list' table, enter this:

![User-reading-list-query][user-reading-list-query]

Finally, to set up the DB connection, environment variables must be set. In your IDE, find the environment variables page (in Eclipse, right click the project > Run As > Run Configurations > Environment), and set:

- DB_URL as the JDBC connection string (right click the connection in MySQL Connections from MySQL workbench, 'Copy JDBC Connection String to Clipboard', and add "/sys" at the end of it if you did not set a default schema - as this is typically the schema automatically set by MySQL workbench).
- DB_USER as the username set (should be "root" unless set otherwise).
- DB_PASSWORD as the password set.

Apply these environment variables and run the project on the Tomcat server you set up installed at localhost.

You should now be set up!

> [!IMPORTANT]
> (Books must be uploaded through MySQL workbench, however creating users must be done through the application itself after setup. This is due to jbCrypt's password hashing; passwords must be set and accessed hashed, and so creating an account manually with a password that has not been hashed will not be picked up when logging in, for example).

<!-- CREDITS -->

## Credits

Project icon: <a href="https://icons8.com" target="_blank" rel="noopener noreferrer"> <span class="bold">icons8</span></a>
</br>
Illustrations: <a href="https://undraw.co" target="_blank" rel="noopener noreferrer"><span class="bold">unDraw</span></a>
</br>
Registration system help from <a href="https://www.youtube.com/watch?v=zdWfyBXO2iU" target="_blank" rel="noopener noreferrer"><span class="bold">@UniqueDeveloper</span></a>

<!-- MARKDOWN LINKS & IMAGES -->

[JavaScript]: https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E
[JavaScript-url]: https://javascript.com/
[MySQL]: https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white
[MySQL-url]: https://mysql.com/
[Bootstrap]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com/
[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://java.com/
[book-logo]: src/main/webapp/images/book.png
[bibliothiki]: src/main/webapp/images/bibliothiki.png
[bibliothiki-one]: src/main/webapp/images/bibliothiki-one.png
[books-query]: src/main/webapp/images/books-query.png
[user-query]: src/main/webapp/images/user-query.png
[user-reading-list-query]: src/main/webapp/images/user-reading-list-query.png
