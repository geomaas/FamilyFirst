let app = angular.module('familyFirst', ['ngRoute']);


// Controllers:

// Services:

// Router:
app.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/', {
      redirectTo: '/login',
    })
    .when('/login', {
      controller: 'userController',
      templateUrl: 'templates/loginTemplate.html',
    })
    .when('/login', {
      controller: 'userController',
      templateUrl: 'templates/loginTemplate.html',
    })
