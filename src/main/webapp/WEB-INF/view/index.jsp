<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- SEO -->
  <meta name="creator" content="Georgios Davakos">
  <meta name="author" content="Georgios Davakos, giorgos_davak@hotmail.com">
  <meta name="description" content="Stock market research page">
  <meta name="keywords" content="Financial technologi, Stock Market, Research, database">
  <meta name="revised" content="Monday, June 16th, 12:00 pm">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/stylesheet.css">
  <!-- Font Awesome -->
  <script src="https://kit.fontawesome.com/2994acdae5.js" crossorigin="anonymous"></script>
  <title>Stock Market Research</title>
</head>

<body>

  <div class="container-fluid">
    <div class="container mt-4">
      <div class="row">
        <div class="col-8 pl-3 pr-5 pb-4 pt-2 left-hero">
          <h2 class="font-weight-bold">Welcome to my <span class="orange">Portfolio Tracker</span></h2>
          <p>
            Hi and welcome, to use the portfolio tracker please enter a user name inside the text bar to the right. After that please either press enter or click on the "Get Started" button.
            <br>
            <br>
            If you are not feeling like building an entire portfolio and just want to have a quick look into the current prices of different stocks then use the section below.
          </p>
        </div>
        <div class="col-4 pl-5 pr-5 pb-4 pt-2 more-margin">
          <form action="username" method="post">
	   <label for="userName" class="mb-1">Username:</label>
            <input type="text" name="userName" placeholder="Username" class="form-conrol">
	    <label for="password" class="mt-2 mb-1">Password:</label>
            <input type"password" name="password" class="form-conrol"><br>
          <button type="submit" name="login" class="btn btn-primary font-weight-bold mt-3 btn-block">Login</button>
	  <button type="submit" name="register" class="btn btn-secondary font-weight-bold mt-2 btn-block">Register</button>
          </form>
	  <p>${errorMsg}</p>
        </div>
      </div>
    </div>
  </div>

  <div class="container bg rounded py-3">
    <div class="search-bar">
      <h3 class="font-weight-bold">Search for a company</h3>
      <p>
        Please search for US companies as companies from other countries are not supported as of now!
      </p>
      <form action="tickerSearch" method="get">
        <input class="form-control col-sm-3" type="text" name="ticker" placeholder="Ticker">
         <button type="submit" class="btn btn-primary font-weight-bold mt-2">Search</button>         
      </form>
    </div>
    <p class="mt-3 mb-0">
      ${result}
    </p>
  </div>

  <footer class="mt-3 pt-2 bg fixed-bottom">
    <div class="container-fluid py-1 copyrights">
      <p class="text-center mt-1 mb-1"> &copy; 2020 Georgios Davakos, All rights reserved.</p>
    </div>
  </footer>

  <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>

</html>