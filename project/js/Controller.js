var defaultvalue=-1;
if(mymainuser==undefined)
{
var  mymainuser=-1;
}
var  myorder=-1;

(function() {
	  var module = angular.module('pizzeria',["ngRoute"]).filter('listToMatrix', function() {
    function listToMatrix(list, elementsPerSubArray) {
        var matrix = [], i, k;

        for (i = 0, k = -1; i < list.length; i++) {
            if (i % elementsPerSubArray === 0) {
                k++;
                matrix[k] = [];
            }

            matrix[k].push(list[i]);
        }

        return matrix;
    }
    return function(list, elementsPerSubArray) {
        return listToMatrix(list, elementsPerSubArray);
    };
});
	  module.config(function($routeProvider) {
	    $routeProvider
	    .when("/", {
	      templateUrl : "login.html",
	      controller : "LoginController",
	    })
	    .when("/menu", {
	      templateUrl : "menu.html",
	      controller : "MenuController"
	    })
		.when("/cart", {
	      templateUrl : "cart.html",
	      controller : "CartController"
	    })
		.when("/custom", {
	      templateUrl : "custom.html",
	      controller : "CustomController"
	    })
		.when("/payment", {
	      templateUrl : "payment.html",
	      controller : "PayController"
	    })
		.when("/feedback", {
	      templateUrl : "feedback.html",
	      controller : "FeedController"
	    })
		.when("/adminlogin", {
	      templateUrl : "AdminLogin.html",
	    })
		.when("/adminmain", {
	      templateUrl : "adminMain.html",
	      controller : "AdminMainController"
	    })
		.when("/admin",{
		   redirectTo: "/adminlogin"  
		})
	    .otherwise({
	      redirectTo: "/"  
	    });
	  });
		
		// Creating Service

module.service('myService', function ($http) {


		this.userid;
		var addid = function (name) {
		$http.get('http://localhost:8080/SpringHibernateExample/user/getid/'+name+'/')
						.then(
							function (response) {
								console.log(response.data);
							this.userid=response.data;
							},
							function(errResponse){
								console.error('Failed to fetch id');
							});	
					
		}
		this.setuser= function (name) {

		addid(name);

		}
		this.getuser= function () {

		return this.userid;

		}

});
	  module.controller('LoginController', ['$scope','$http','$window','myService', function($scope,$http,$window,myService) {
			$scope.login=function(){
				var userdetail=[];
				userdetail.push($scope.userName);
				userdetail.push($scope.password);
				console.log(userdetail);
				$http.post("http://localhost:8080/SpringHibernateExample/user/login",userdetail)
	             .then(
	                function () {
					$http.get('http://localhost:8080/SpringHibernateExample/user/getid/'+$scope.userName+'/')
						.then(
							function (response2) {
								console.log(response2.data);
								mymainuser=response2.data;
								console.log("Success in sending post")
								$window.location.href = '#!/menu';
							},
							function(errResponse){
								console.error('Failed to fetch id');
							});
					}	,
	                function(errResponse){
	                    console.error('Error while logging in');
	                });


			};
	        
	  }]);	  



	  module.controller('MenuController', ['$scope','$http','$window','myService', function($scope, $http,$window,myService) {
	        var self = this;
	        console.log(mymainuser);
			$http.get('http://localhost:8080/SpringHibernateExample/menu/getall')
	            .then(
	                function (response) {
					$scope.menulist=response.data;
						console.log($scope.menulist);
	                console.log("Fetched menu Item")
	                },
	                function(errResponse){
	                    console.error('Failed to fetch menu Item');
	                });	
	//		console.log(myService.userid);
			var REST_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/cart/additem ';
	        $scope.addtocart=function(menuid){
	  //      	$http.get(REST_SERVICE_URI+"/"+menuid+"/"+myService.getuser()+"/")
	          	$http.get(REST_SERVICE_URI+"/"+menuid+"/"+mymainuser+"/")
	            .then(
	                function (response) {
	                console.log("Success in sending post")
	                $window.location.href = '#!/cart';
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
	                console.log("Success in logging out");
	                mymainuser=defaultvalue;
	                $window.location.href = '#!/';
	                },
	                function(errResponse){
	                    console.error('Error while logging out');
	                });
	        };
	    }]);
		  



	  module.controller('CartController', ['$scope','$http','$window','myService', function($scope, $http,$window,myService) {
	        var self = this;
			$http.get('http://localhost:8080/SpringHibernateExample/cart/getall/'+mymainuser+'/')
	            .then(
	                function (response) {
					$scope.cartlist=response.data;
						console.log($scope.cartlist);
	                console.log("Fetched menu Item")
	                },
	                function(errResponse){
	                    console.error('Failed to fetch menu Item');
	                });	
			var REST_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/cart/additem ';
	        $scope.addtocart=function(menuid){
	  //      	$http.get(REST_SERVICE_URI+"/"+menuid+"/"+myService.getuser()+"/")
	          	$http.get(REST_SERVICE_URI+"/"+menuid+"/"+mymainuser+"/")
	            .then(
	                function (response) {
	                console.log("Success in sending post")
	                $window.location.href = '#!/cart';
	                },
	                function(errResponse){
	                    console.error('Error while adding to cart');
	                });
	        };
	        var CART_SUB_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/cart/subitem ';
	        $scope.subfromcart=function(menuid){
	  //      	$http.get(REST_SERVICE_URI+"/"+menuid+"/"+myService.getuser()+"/")
	          	$http.get(CART_SUB_SERVICE_URI+"/"+menuid+"/"+mymainuser+"/")
	            .then(
	                function (response) {
	                console.log("Success in sending post")
	                $window.location.href = '#!/cart';
	                },
	                function(errResponse){
	                    console.error('Error while adding to cart');
	                });
	        };

	        var CART_DEL_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/cart/delitem ';
	        $scope.removeitem=function(menuid){
	  //      	$http.get(REST_SERVICE_URI+"/"+menuid+"/"+myService.getuser()+"/")
	          	$http.get(CART_DEL_SERVICE_URI+"/"+menuid+"/"+mymainuser+"/")
	            .then(
	                function (response) {
	                console.log("Success in sending post")
	                $window.location.href = '#!/cart';
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
	                console.log("Success in logging out");
	                mymainuser=defaultvalue;
	                $window.location.href = '#!/';
	                },
	                function(errResponse){
	                    console.error('Error while logging out');
	                });
	        };
	    }]);
		  



	  module.controller('CustomController', ['$scope','$http','$window','myService', function($scope, $http,$window,myService) {
	        var self = this;
			$http.get('http://localhost:8080/SpringHibernateExample/menu/getallIng/')
	            .then(
	                function (response) {
					$scope.inglist=response.data;
					console.log($scope.inglist);
	                console.log("Fetched Ingredient Item")
	                },
	                function(errResponse){
	                    console.error('Failed to fetch Ingredient Item');
	                });	
	        $scope.ingcust=[];
	        
	        $scope.adding=function(ingid,ingname){

	  						console.log($scope.ingcust);
	  						for(var i=0;i<$scope.ingcust.length;i++)
	  						{
	  							//console.log('12121'+ing);
	  							if($scope.ingcust[i]==ingid)
	  								return true;
	  						}
	  						$scope.ingcust.push(ingid);
	  						return true;
	        };

	        $scope.getname=function(ingid){

	  						for(var i=0;i<$scope.inglist.length;i++)
	  						{
	  							if($scope.inglist[i].ingrId==ingid)
	  								return $scope.inglist[i].ingrName;
	  						}
	  						return true;
	        };
	        $scope.deling=function(ingid){
	  						for(var i=0;i<$scope.ingcust.length;i++)
	  						{
	  							//console.log('12121'+ing);
	  							if($scope.ingcust[i]==ingid)
	  								  $scope.ingcust.splice(i, 1);
							}
	  		};

	        var CART_CUSTOM_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/cart/addcustom';
	        $scope.addtocart=function(){
	          	$http.post(CART_CUSTOM_SERVICE_URI+"/"+mymainuser+"/",$scope.ingcust)
	            .then(
	                function (response) {
	                console.log("Success in sending post")
	                $window.location.href = '#!/cart';
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
	                console.log("Success in logging out");
	                mymainuser=defaultvalue;
	                $window.location.href = '#!/';
	                },
	                function(errResponse){
	                    console.error('Error while logging out');
	                });
	        };
	    }]);



	
		module.controller('PayController', ['$scope','$http','$window','myService', function($scope, $http,$window,myService) {
	        var self = this;
			$http.get('http://localhost:8080/SpringHibernateExample/cart/getall/'+mymainuser+'/')
	            .then(
	                function (response) {
					$scope.cartlist=response.data;
					console.log($scope.cartlist);
	                console.log("Fetched menu Items")
	                },
	                function(errResponse){
	                    console.error('Failed to fetch menu Item');
	                });	
			
			$http.get('http://localhost:8080/SpringHibernateExample/payment/coupons/'+mymainuser+'/')
	            .then(
	                function (response) {
					$scope.coupons=response.data;
					$scope.mycoupon=-1;
					console.log($scope.coupons);
	                console.log("Fetched coupons")
	                },
	                function(errResponse){
	                    console.error('Failed to fetch coupons');
	                });	
			
			
			var calculateAmount = function () { 
				$http.get('http://localhost:8080/SpringHibernateExample/cart/getAmt/'+mymainuser+'/')
				.then(
					function (response) {
					$scope.billAmt=response.data;
					console.log($scope.billAmt);
					console.log("Fetched Bill")
					},
					function(errResponse){
						console.error('Failed to fetch Bill');
					}
				);	
			}
			
			calculateAmount();
			

			$scope.addcoupon=function(coup){
				if(coup.discountAmount>$scope.billAmt)
				{
				$scope.couponErr="Amount should not be negative.Cant add coupons";
				}	
				else{
				$scope.mycoupon=coup.couponId;
				$scope.billAmt-=coup.discountAmount;
				$scope.couponselected=true;
				}
			};
			$scope.couponsList=[];
			var MAKE_PAYMENT_URI = 'http://localhost:8080/SpringHibernateExample/payment/make';
			$scope.makepayment= function() {
				if($scope.couponselected === true)
						$scope.couponsList.push($scope.mycoupon);
				$scope.couponsList.push(mymainuser.toString());
				console.log($scope.couponsList);
				$http.post(MAKE_PAYMENT_URI,$scope.couponsList)
	             .then(
	                function (response) {
					$scope.orderId=response.data;
					myorder=response.data;
					$window.location.href = '#!/feedback';
	                console.log("Success in sending post")
	                },
	                function(errResponse){
	                    console.error('Error while adding to cart');
	                });
	        };

		}]);




  module.controller('FeedController', ['$scope','$http','$window','myService', function($scope, $http,$window,myService) {

	        var CART_FEED_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/payment/feedback';
	        console.log(myorder);
	        $scope.sendfeed=function(){
	          	$http.get(CART_FEED_SERVICE_URI+"/"+mymainuser+"/"+myorder+"/"+$scope.feedback+"/")
	            .then(
	                function (response) {
	                console.log("Success in sending feed");
	                $window.location.href = '#!/menu';
	                },
	                function(errResponse){
	                    console.error('Error while adding to cart');
	                $window.location.href = '#!/menu';
	                });
	        };	    	


	        var LOGOUT_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/user/logout';
	        $scope.logout=function(){
	        	$http.get(LOGOUT_SERVICE_URI)
	            .then(
	                function (response) {
	                console.log("Success in logging out");
	                mymainuser=defaultvalue;
	                $window.location.href = '#!/';
	                },
	                function(errResponse){
	                    console.error('Error while logging out');
	                });
	        };
	    	}]);	
	
  module.controller('AdminMainController', ['$scope','$http','$window','myService', function($scope, $http,$window,myService) {
	    	$scope.VO=1;
			$scope.VF=0;
			$scope.UI=0;
			$scope.SL=0;
			$scope.AI=1;
			$scope.DI=0;
			$scope.AP=0;
			$scope.DP=0;
			

		    var self = this;
			$http.get('http://localhost:8080/SpringHibernateExample/admin/getallorder')
	            .then(
	                function (response) {
					$scope.orderlist=response.data;
						console.log($scope.orderlist);
	                console.log("Fetched menu Item")
	                },
	                function(errResponse){
	                    console.error('Failed to fetch menu Item');
	                });

			$http.get('http://localhost:8080/SpringHibernateExample/admin/getallfeedback')
	            .then(
	                function (response) {
					$scope.feedback=response.data;
						console.log($scope.feedback);
	                console.log("Fetched feedback")
	                },
	                function(errResponse){
	                    console.error('Failed to fetch feedback');
	                });

	        $scope.showdetails=function(details){
	  		$scope.disp=details;
	  		console.log('changed view details');
	  		};
	       $scope.togglepizza=function(){
		  		console.log($scope.pizzaId);
		  		$http.get('http://localhost:8080/SpringHibernateExample/admin/chgpizza/'+$scope.pizzaId+'/')
	            .then(
	                function (response) {
					console.log("changed pizza");
	                $window.location.href = '#!/adminmain';
	                },
	                function(errResponse){
	                    console.error('Failed to change');
	                });

	  			};
       		$scope.toggleingredient=function(){
	  			console.log($scope.ing);
				$http.get('http://localhost:8080/SpringHibernateExample/admin/chging/'+$scope.ing+'/')
	            .then(
	                function (response) {
					console.log("changed pizza");
	                $window.location.href = '#!/adminmain';
	                },
	                function(errResponse){
	                    console.error('Failed to change');
	                });
	  		};

	        var LOGOUT_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/admin/logout';
	        $scope.logout=function(){
	        	$http.get(LOGOUT_SERVICE_URI)
	            .then(
	                function (response) {
	                console.log("Success in logging out")
	                $window.location.href = '#!/adminlogin';
	                },
	                function(errResponse){
	                    console.error('Error while logging out');
	                });
	        };
	    }]);
		  
})();