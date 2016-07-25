(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
module.exports = function(app){

// this handles the login view
  app.controller('loginController', ['$scope', 'userService', function($scope, userService){


    $scope.login = function(){
      console.log(`Trying to Send ${$scope.userName} and ${$scope.password}`);

      userService.serverLogin($scope.userName,$scope.password);

    }



  }]);
};

},{}],2:[function(require,module,exports){
module.exports = function(app){

// this handles the task manager view
  app.controller('taskManagerController', ['$scope', 'userService', 'taskService', '$http', function($scope, userService, taskService, $http){

    $scope.taskList = taskService.getAllTasks();
    $scope.tip = taskService.getTip();


    $scope.add = function(){
      // console.log(`send task text ${$scope.taskText}`);
      // console.log(`add a new task`);
      $http({
            method: 'POST',
            url: '/addTask',
            data: $scope.taskText,

        }).then(function(response) {
          // console.log(response);
            taskService.getAllTasks();
        })
    };

    // comments section:
    $scope.model = {};
    $scope.comment = function(id, index) {
      // console.log(`send comment text ${$scope.model.newComment[index]}`);
      // console.log(`task Id: ${id}`);
      $http({
            method: 'POST',
            url: `/comment${id}`,
            data: $scope.model.newComment[index],

        }).then(function(response) {
          // console.log(response);
          $scope.model.newComment[index] = "";
          taskService.getAllTasks();
        })
    };
    // thanks to @developer033 on stack overflow for the assistance with ng-repeat and ng-model usage
    $scope.done = function(id){
      console.log(`mark task as completed`);
      $http({
            method: 'POST',
            url: `/complete${id}`,

        }).then(function(response) {
          console.log(response);
          taskService.getAllTasks();
        })
    }

  }]);
};

},{}],3:[function(require,module,exports){
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

},{"./controllers/loginController":1,"./controllers/taskManagerController":2,"./services/tasksService":4,"./services/userService":5}],4:[function(require,module,exports){
module.exports = function(app){

// this service will handle the task data
  app.factory('taskService', ['$http','$location', function($http, $location){
      let allTasksList = [];
      let allTips = [];


    return {
      getAllTasks: function(){
        console.log(`get tasks from server`);
        $http({
              method: 'GET',
              url: '/tasks',
          }).then(function(response) {
            console.log(response);
            angular.copy(response.data, allTasksList);
          })
          console.log("allTaskList array:", allTasksList);
          return allTasksList
      },

      getTip: function(){
        console.log('get a tip');
        $http({
              method: 'GET',
              url: `/Protip`,


          }).then(function(response) {
            console.log(response);
            angular.copy(response, allTips)
          })
          return allTips
      },

    };

  }]);
};

},{}],5:[function(require,module,exports){
module.exports = function(app){

// this service will handle the user data
  app.factory('userService', ['$http','$location', function($http, $location){
    



    return {
      serverLogin: function(user,pass){
        console.log(`make a http request with ${user} and ${pass}`);
        $http({
              method: 'POST',
              url: '/login',
              data: {
                userName: user,
                password: pass,
              }
          }).then(function(response) {
            console.log("here is whats coming back", response );
            if(response.data === true){
              $location.path('/tasks');
            }

          })
      },



    };

  }]);
};

},{}]},{},[3])