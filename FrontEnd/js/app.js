let app = angular.module('familyFirst', ['ngRoute']);


// Controllers:
require('./controllers/loginController')(app);
require('./controllers/taskManagerController')(app);

// Services:
require('./services/userService')(app);
require('./services/tasksService')(app);

// Router:
app.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    .when('/', {
      redirectTo: '/login',
    })
    .when('/login', {
      controller: 'loginController',
      templateUrl: 'templates/login.html',
    })
    .when('/tasks', {
      controller: 'taskManagerController',
      templateUrl: 'templates/taskManager.html',
    })
}]);
