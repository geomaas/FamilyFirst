let app = angular.module('familyFirst', ['ngRoute', 'ngAnimate']);


// Controllers:
require('./controllers/loginController')(app);
require('./controllers/taskManagerController')(app);
require('./controllers/medController')(app);

// Services:
require('./services/userService')(app);
require('./services/tasksService')(app);
require('./services/medService')(app);

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
    .when('/medication', {
      controller: 'medController',
      templateUrl: 'templates/medTemplate.html',
    })
}]);
