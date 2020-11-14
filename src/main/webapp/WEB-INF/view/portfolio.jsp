<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
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
  <title>Your portfolio</title>
</head>


<body>

  <div class="container bg rounded py-3 left-hero">
    <div class="row">

      <div class="search-bar col-8 w-auto">
        <h3 class="font-weight-bold w-auto">Search for a company</h3>
        <p class="w-auto">
          Please search for US companies as companies from other countries are not supported as of now!
        </p>
        <form action="portfolioSearch" method="get" class="">
          <div class="form-group row w-75">
            <label for="ticker" class="col-sm-5 col-form-label">Companies ticker symbol</label>
            <input class="form-control col-sm-5" type="text" name="ticker" placeholder="Ticker">
          </div>
          <div class="form-group row w-75">
            <label for="sharesNum" class="col-sm-5 col-form-label">Number of shares</label>
            <input class="form-control col-sm-5" type="number" name="sharesNum" placeholder="0">
          </div>
          <div class="form-group row w-75">
            <label for="buyInPrice" class="col-sm-5 col-form-label">Per share price</label>
            <input class="form-control col-sm-5" type="text" name="buyInPrice" placeholder="0.0">
          </div>
          <div class="row w-75">
            <div class="col-sm-5"></div>
            <button type="submit" class="btn btn-primary text-white col-sm-5"><b>Add</b></button>
          </div>
        </form>
      </div>

      <div class="totalValue col-4 text-center">
        <h3 class="font-weight-bold">Current Evaluation</h3>
        <p class="mb-0 mt-5 font-weight-bold">You have currently invested:</p>
        <p class="mt-0">${currentInvestment}</p>
        <p class="mb-0 mt-5 font-weight-bold">Your investments are worth:</p>
        <p class="mt-0">${currentEvaluation}</p>
      </div>
    </div>
  </div>

  <div class="container bg rounded py-3 mt-4">
    <p class="mb-0">
      <table class="table">
        <thead class="thead-dark">
          <tr>
            <th scope="col">Company Name</th>
            <th scope="col">Number of Shares<br></th>
            <th scope="col">Buy In Price<br></th>
          </tr>
        </thead>
        <tbody class="text-light">
          <tr>
            <td>${name}</td>
            <td>${shares}</td>
            <td>${buyInPrice}</td>
          </tr>
        </tbody>
      </table>
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
