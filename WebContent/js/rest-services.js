'use strict';

angular.module('myApp.restServices', ['ngResource'])
    .factory('Student', ['$resource',
        function ($resource) {
            return $resource('http://localhost:8080/StudentTimetable/provision', {},
            		{create:{method:'JSONP', params:{
            			test:'abc', 
            			callback: 'JSON_CALLBACK'}}});
        }]);