module.exports = function(app){

// this handles the task manager view
  app.controller('taskManagerController', ['$scope', 'userService', 'taskService', function($scope, userService, taskService){

    $scope.taskList = taskService.getAllTasks();

    $scope.add = function(){
      console.log(`send task text ${$scope.taskText}`);
      taskService.addTask($scope.taskText);
    }
    $scope.comment = function(){
      console.log((`send comment text ${$scope.newComment}`));
    }
  }]);
};
