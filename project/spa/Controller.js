(function() {
	  var module = angular.module('pizzeria',["ngRoute"]);
	  module.config(function($routeProvider) {
	    $routeProvider
	    .when("/", {
	      templateUrl : "login.html",
	    })
	    
	    .when("/menu", {
	      templateUrl : "menu.html",
	      controller : "menuController"
	    })
		.when("/cart", {
	      templateUrl : "cart.html",
	      controller : "cartController"
	    })
		.when("/adminMain", {
	      templateUrl : "adminMain.html",
	      controller : "adminMainController"
	    })
		.when("/adminLogIn", {
	      templateUrl : "adminLogIn.html",
	      controller : "adminLogInController"
	    })
		.when("/custom", {
	      templateUrl : "custom.html",
	      controller : "customController"
	    })
	    .otherwise({
	      redirectTo: "/"  
	    });
	  });
	  module.controller('MenuController', ['$scope','$http','$window',  function($scope, $http,$window) {
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
		module.controller('adminMainController', function($scope) {
			$scope.VO=1;
			$scope.VF=0;
			$scope.UI=0;
			$scope.SL=0;
			$scope.AI=1;
			$scope.DI=0;
			$scope.AP=0;
			$scope.DP=0;
		});
		


	}());
