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
module.exports = function(app) {

    // this handles the task manager view
    app.controller('medController', ['$scope', 'userService', 'medService', '$http', function($scope, userService, medService, $http) {

        $scope.medList = medService.getAllMeds();
        $scope.tip = medService.getTip();
        $scope.user = userService.getCurrentUser();

        $scope.newMed = function() {
            console.log(`send medication info ${$scope.medName}, ${$scope.dosage},${$scope.frequency},${$scope.instructions}`);
            console.log(`add a medication`);
            $http({
                method: 'POST',
                url: '/addMed',
                data: {
                    medName: $scope.medName,
                    dose: $scope.dosage,
                    frequency: $scope.frequency,
                    instructions: $scope.instructions,
                },
            }).then(function(response) {
                console.log(response);
                medService.getAllMeds();
                $scope.medName = "";
                $scope.dosage = "";
                $scope.frequency = "";
                $scope.instructions = "";
            })
        };


        $scope.give = function(id) {
            console.log(`give medication with id of ${id}`);
            $http({
                method: 'POST',
                url: `/given${id}`,

            }).then(function(response) {
                console.log(response);
                medService.getAllMeds();
            })
        };
        $scope.model = {};
        $scope.timer = function(timeGivenHr, frequency) {
            let date = new Date();
            let ctHrs = date.getHours();
            // console.log("current time hrs",ctHrs);
            let elapsedTimeHr = ctHrs - timeGivenHr;
            // console.log("hrs elapsed ", elapsedTimeHr);
            // console.log("frequency", frequency);
            let percentage = elapsedTimeHr / frequency;
            // console.log("percentage",percentage*100);
            // let bar = $scope.model.specBar[index]
            let widthPercent = percentage * 100;
            console.log(widthPercent);
            // bar.style.width = `${widthPercent}%`;
            if (widthPercent >= 75 && widthPercent < 100) {
                return {
                    "width": `${widthPercent}%`,
                    "background-color": "green"
                }
                // bar.style.backgroundColor='green';
            } else if (widthPercent > 50 && widthPercent < 75) {
                return {
                    "width": `${widthPercent}%`,
                    "background-color": "yellow"
                } // bar.style.backgroundColor='yellow';
            } else if (widthPercent <= 50 && widthPercent > 0) {
                return {
                    "width": `${widthPercent}%`,
                    "background-color": "red"
                }
            } else if (widthPercent >= 100) {
                return {
                    "width": "100%",
                    "background-color": "green"
                }
            } else if (widthPercent === 0) {
                return {
                    "width": "10%",
                    "background-color": "red"
                }
            } else if (widthPercent < 0){
              return {
                  "width": "100%",
                  "background-color": "green"
              }
            }
        }


    }]);
};

},{}],3:[function(require,module,exports){
module.exports = function(app){

// this handles the task manager view
  app.controller('taskManagerController', ['$scope', 'userService', 'taskService', '$http', function($scope, userService, taskService, $http){

    $scope.taskList = taskService.getAllTasks();
    $scope.tip = taskService.getTip();
    $scope.user = userService.getCurrentUser();

    $scope.add = function(){
      console.log(`send task text ${$scope.taskText}`);
      console.log(`add a new task`);
      $http({
            method: 'POST',
            url: '/addTask',
            data: $scope.taskText,

        }).then(function(response) {
          console.log(response);
            taskService.getAllTasks();
            $scope.taskText = "";
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
          // console.log(response);
          taskService.getAllTasks();
        })
    };
    var mark = function(){
      if (completedBy !== null) {
        document.getElementById("check").setAttribute('checked')
      }
    };
  }]);
};

},{}],4:[function(require,module,exports){
let app = angular.module('familyFirst', ['ngRoute']);


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

},{"./controllers/loginController":1,"./controllers/medController":2,"./controllers/taskManagerController":3,"./services/medService":5,"./services/tasksService":6,"./services/userService":7}],5:[function(require,module,exports){
module.exports = function(app){

// this service will handle the task data
  app.factory('medService', ['$http','$location', function($http, $location){
      let allMedsList = [];
      let allTips = [];


    return {
      getAllMeds: function(){
        // console.log(`get tasks from server`);
        $http({
              method: 'GET',
              url: '/meds',
          }).then(function(response) {
            console.log(response);

            angular.copy(response.data.reverse(), allMedsList);
          })
          console.log("allTaskList array:", allMedsList);
          return allMedsList
      },

      getTip: function(){
        // console.log('get a tip');
        $http({
              method: 'GET',
              url: `/Protip`,


          }).then(function(response) {
            // console.log(response);
            angular.copy(response.data, allTips)
            // console.log(allTips);
          })
          return allTips
      },

    };

  }]);
};

},{}],6:[function(require,module,exports){
module.exports = function(app){

// this service will handle the task data
  app.factory('taskService', ['$http','$location', function($http, $location){
      let allTasksList = [];
      let allTips = [];


    return {
      getAllTasks: function(){
        // console.log(`get tasks from server`);
        $http({
              method: 'GET',
              url: '/tasks',
          }).then(function(response) {
            console.log(response);

            angular.copy(response.data.reverse(), allTasksList);
          })
          console.log("allTaskList array:", allTasksList);
          return allTasksList
      },

      getTip: function(){
        // console.log('get a tip');
        $http({
              method: 'GET',
              url: `/Protip`,


          }).then(function(response) {
            // console.log(response);
            angular.copy(response.data, allTips)
            // console.log(allTips);
          })
          return allTips
      },

    };

  }]);
};

},{}],7:[function(require,module,exports){
module.exports = function(app){

// this service will handle the user data
  app.factory('userService', ['$http','$location', function($http, $location){
  let currentUser = {};



    return {
      serverLogin: function(user,pass){
        // console.log(`make a http request with ${user} and ${pass}`);
        $http({
              method: 'POST',
              url: '/login',
              data: {
                userName: user,
                password: pass,
              }
          }).then(function(response) {
            // console.log("here is whats coming back", response );
            if(response.data.userName === user){
              angular.copy(response.data, currentUser);
              $location.path('/tasks');
            }
            return currentUser
          })
      },
      getCurrentUser: function() {
      console.log("user info", currentUser);
      return currentUser
    },



    };

  }]);
};

},{}]},{},[4])