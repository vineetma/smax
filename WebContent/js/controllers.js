'use strict';

angular.module('myApp.controllers', [])
    .controller('MainCtrl', ['$scope', '$rootScope', '$window', '$location', '$http', function ($scope, $rootScope, $window, $location, $http) {
     $scope.$watch('firstName', function(ov, nv){
    	 console.log("FirstName updated:", ov, nv);
    	 console.log("First: ", $scope.firstName);
     });
     $scope.createStudent = function() {
//    	 var emp = Employee.readAll({employeeId: 223});
    	 console.log("Testing student creation..", $scope.firstName, $scope.lastName);
    		/*$.ajax({
    		    url:'http://localhost:8080/StudentTimetable/provision',
    		    dataType: 'jsonp',
    		    data: {'action':'add',
		 		    'student':{
			 			'rollNo': $scope.rollNo,
			 			'firstName':$scope.firstName,
			 			'lastName': $scope.lastName,
			 			'email' : $scope.email,
			 			'section': $scope.section,
			 			'semester' : $scope.semester,
			 			'department' : $scope.department,
			 			'password': $scope.password
			 		}
    		    },
    		    success: function(data) {
			 		console.log(data);
			 	}
//			 	contentType : "application/json",
    		});
    	Employee.readAll();*/
  	 $http.post('http://localhost:8080/StudentTimetable/provision', 
    			 	  {'action':'add', 
    		 		    'student':{
    			 			'rollNo': $scope.rollNo,
    			 			'firstName':$scope.firstName,
    			 			'lastName': $scope.lastName,
    			 			'email' : $scope.email,
    			 			'section': $scope.section,
    			 			'semester' : $scope.semester,
    			 			'department' : $scope.department,
    			 			'password': $scope.password
    			 		}
    			 	  }
    	 			).success(function(data){
    		 console.log("Return value from ajax call: ", data);
    	 });
     };
    }]);