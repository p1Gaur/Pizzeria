<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu Page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
	    <script type="text/javascript">
    'use strict';

    var App = angular.module('menuApp',[]);
    angular.module('menuApp').controller('menuCtrl', ['$scope','$http','$window',  function($scope, $http,$window) {
        var self = this;
        var REST_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/cart/additem';
        $scope.addtocart=function(menuid){
        	$http.post(REST_SERVICE_URI,menuid)
            .then(
                function (response) {
                console.log("Success in sending post")
                $window.location.href = 'cart';
                },
                function(errResponse){
                    console.error('Error while adding to cart');
                });
        };
        var LOGOUT_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/user/logout';
        $scope.logout=function(){
        	$http.get(LOGOUT_SERVICE_URI)
            .then(
                function (response) {
                console.log("Success in logging out")
                $window.location.href = 'index';
                },
                function(errResponse){
                    console.error('Error while logging out');
                });
        };
        
    }]);
    </script>
	
	
	</head>
	<body ng-app="menuApp" ng-controller="menuCtrl">
		<div class="w3-container w3-center">
			<h2>Welcome to PIZZERIA!!!</h2>
			<button style="float:right"class="w3-btn w3-green" ng-click="logout()">Log Out</button>
			<h3>Menu</h3>
		</div>

		<div class="w3-row-padding w3-margin-top">
				<div class="w3-third w3-container">
					<div class="w3-card-4">
						<header class="w3-container w3-blue">
							<h3>Farm house</h3>
						</header>
						<div>
							<img src="farmHouse.jpg" alt="Farm house pizza" style="width:100%">
						</div>
						<footer class="w3-container w3-blue">
							<h3>Rs 500</h3>
							<button class="w3-btn w3-green" ng-click="addtocart(1)">Buy This!</button>
						</footer>
					</div>   
				</div>
			
				<div class="w3-third w3-container">
					<div class="w3-card-4">
						<header class="w3-container w3-blue">
							<h3>Peppy Paneer</h3>
						</header>
						<div>
							<img src="peppyPaneer.jpg" alt="Peppy Paneer pizza" style="width:100%">
						</div>
						<footer class="w3-container w3-blue">
							<h4>Rs 400</h4>
							<button class="w3-btn w3-green">Buy This!</button>
						</footer>
					</div>   
				</div>
				
				<div class="w3-third w3-container">
					<div class="w3-card-4">
						<header class="w3-container w3-blue">
							<h3>Margherita</h3>
						</header>
						<div>
							<img src="Margherita.jpg" alt="Margherita pizza" style="width:100%">
						</div>
						<footer class="w3-container w3-blue">
							<h4>Rs 5500</h4>
							<button class="w3-btn w3-green">Buy This!</button>
						</footer>
					</div>   
				</div>
			</div>		<!-- end of row-->

			<div class="w3-row-padding w3-margin-top">
				<div class="w3-third w3-container">
					<div class="w3-card-4">
						<header class="w3-container w3-blue">
							<h3>Farm house</h3>
						</header>
						<div>
							<img src="farmHouse.jpg" alt="Farm house pizza" style="width:100%">
						</div>
						<footer class="w3-container w3-blue">
							<h4>Rs 500</h4>
							<button class="w3-btn w3-green">Buy This!</button>
						</footer>
					</div>   
				</div>
			
				<div class="w3-third w3-container">
					<div class="w3-card-4">
						<header class="w3-container w3-blue">
							<h3>Peppy Paneer</h3>
						</header>
						<div>
							<img src="peppyPaneer.jpg" alt="Peppy Paneer pizza" style="width:100%">
						</div>
						<footer class="w3-container w3-blue">
							<h4>Rs 400</h4>
							<button class="w3-btn w3-green">Buy This!</button>
						</footer>
					</div>   
				</div>
				
				<div class="w3-third w3-container">
					<div class="w3-card-4">
						<header class="w3-container w3-blue">
							<h3>Margherita</h3>
						</header>
						<div>
							<img src="Margherita.jpg" alt="Margherita pizza" style="width:100%">
						</div>
						<footer class="w3-container w3-blue">
							<h4>Rs 5500</h4>
							<button class="w3-btn w3-green">Buy This!</button>
						</footer>
					</div>   
				</div>
			</div>		<!-- end of row-->

	</body>
</html>
