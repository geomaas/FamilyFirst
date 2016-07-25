module.exports = function(app){

// this handles the task manager view
  app.controller('taskManagerController', ['$scope', 'userService', 'taskService', '$http', function($scope, userService, taskService, $http){

    $scope.taskList = taskService.getAllTasks();

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
        })
    };
    $scope.model = {};
    $scope.comment = function(id, index) {
      console.log(`send comment text ${$scope.model.newComment[index]}`);
      console.log(`task Id: ${id}`);
      $http({
            method: 'POST',
            url: `/comment${id}`,
            data: $scope.model.newComment[index],

        }).then(function(response) {
          console.log(response);
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
