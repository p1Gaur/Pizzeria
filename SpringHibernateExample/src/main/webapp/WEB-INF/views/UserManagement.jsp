<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>AngularJS $http Example</title>
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script type="text/javascript">
    'use strict';

    var App = angular.module('myApp',[]);

    'use strict';

    angular.module('myApp').controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
        var self = this;
        self.user={custId:null,custName:'',custUserName:'',emailId:'',contactNo:'',password:'',resAddress:''};
        self.users=[];

        self.submit = submit;
        self.edit = edit;
        self.remove = remove;
        self.reset = reset;


        fetchAllUsers();

        function fetchAllUsers(){
            UserService.fetchAllUsers()
                .then(
                function(d) {
                    self.users = d;
                },
                function(errResponse){
                    console.error('Error while fetching Users');
                }
            );
        }

        function createUser(user){
            UserService.createUser(user)
                .then(
                fetchAllUsers,
                function(errResponse){
                    console.error('Error while creating User');
                }
            );
        }

        function updateUser(user, id){
            UserService.updateUser(user, id)
                .then(
                fetchAllUsers,
                function(errResponse){
                    console.error('Error while updating User');
                }
            );
        }

        function deleteUser(id){
            UserService.deleteUser(id)
                .then(
                fetchAllUsers,
                function(errResponse){
                    console.error('Error while deleting User');
                }
            );
        }

        function submit() {
            if(self.user.custId===null){
                console.log('Saving New User', self.user);
                createUser(self.user);
            }else{
                updateUser(self.user, self.user.id);
                console.log('User updated with id ', self.user.id);
            }
            reset();
        }

        function edit(id){
            console.log('id to be edited', id);
            for(var i = 0; i < self.users.length; i++){
                if(self.users[i].id === id) {
                    self.user = angular.copy(self.users[i]);
                    break;
                }
            }
        }

        function remove(id){
            console.log('id to be deleted', id);
            if(self.user.id === id) {//clean form if the user to be deleted is shown there.
                reset();
            }
            deleteUser(id);
        }


        function reset(){
            self.user={id:null,username:'',address:'',email:''};
            $scope.myForm.$setPristine(); //reset Form
        }

    }]);

    'use strict';

    angular.module('myApp').factory('UserService', ['$http', '$q', function($http, $q){

        var REST_SERVICE_URI = 'http://localhost:8080/SpringHibernateExample/customer/';

        var factory = {
            fetchAllUsers: fetchAllUsers,
            createUser: createUser,
            updateUser:updateUser,
            deleteUser:deleteUser
        };

        return factory;

        function fetchAllUsers() {
            var deferred = $q.defer();
            $http.get(REST_SERVICE_URI)
                .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Users');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }

        function createUser(user) {
            var deferred = $q.defer();
            $http.post(REST_SERVICE_URI, user)
                .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while creating User');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }


        function updateUser(user, id) {
            var deferred = $q.defer();
            $http.put(REST_SERVICE_URI+id, user)
                .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while updating User');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        }
        function deleteUser(id) {
            var deferred = $q.defer();
            $http.delete(REST_SERVICE_URI+id)
                .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while deleting User');
                    deferred.reject(errResponse);
                }
            );
            return deferred.promise;
        } 

    }]);

    </script>
    
    
    
    
      
    <style>
      .username.ng-valid {
          background-color: lightgreen;
      }
      .username.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .username.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }
 
      .email.ng-valid {
          background-color: lightgreen;
      }
      .email.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .email.ng-dirty.ng-invalid-email {
          background-color: yellow;
      }
 
    </style>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <!--  link href="../css/app.css" rel="stylesheet"></link-->
  </head>
  <body ng-app="myApp" >
      <div class="generic-container" ng-controller="UserController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">User Registration Form </span></div>
              <div class="formcontainer">
<!--submit-->                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.user.custId" />
                      
					  <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="cname">Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.custName" id="cname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.cname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.cname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.cname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
					  
					  <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="cuname">User Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.custUserName" id="cuname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.cuname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.cuname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.cuname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                         
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="email">Email</label>
                              <div class="col-md-7">
                                  <input type="email" ng-model="ctrl.user.emailId" id="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.email.$error.required">This is a required field</span>
                                      <span ng-show="myForm.email.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
					  
					  <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="pno">Contact Number</label>
                              <div class="col-md-7">
                                  <input type="tel" ng-model="ctrl.user.contactNo" id="pno" class="email form-control input-sm" placeholder="Enter your Email" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.pno.$error.required">This is a required field</span>
                                      <span ng-show="myForm.pno.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
					  
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="address">Address</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.resAddress" id="address" class="form-control input-sm" placeholder="Enter your Address. [This field is validation free]"/>
                              </div>
                          </div>
                      </div>
 
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="password">Password</label>
                              <div class="col-md-7">
                                  <input type="password" ng-model="ctrl.user.password" id="password" class="email form-control input-sm" placeholder="Enter your Email" required/>
                              </div>
                          </div>
                      </div>
 
                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value='Add' class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              </div>
                      </div>
                  </form>
              </div>
          </div>
          
      </div>
      
<!-- 
${session}
      
      <script src="app.js"></script>
      <script src="<c:url value='WEB-INF/views/static/js/service/user_service.js' />"></script>
      <script src="<c:url value='WEB-INF/views/static/js/controller/user_controller.js' />"></script>
  -->
  </body>
</html>