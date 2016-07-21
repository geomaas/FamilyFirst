module.exports = function(app){

// this handles the task manager view
  app.controller('taskManagerController', ['$scope', 'userService', 'taskService', function($scope, userService, taskService){

    $scope.taskList = taskService.getAllTasks();

    $scope.add = function(){
      console.log(`send task text ${$scope.taskText}`);
      taskService.addTask($scope.taskText);
      taskService.getAllTasks();
    };
    $scope.model= {};
    $scope.comment = function(id){
      console.log(`send comment text ${$scope.model.newComment}`);
      console.log(`task Id: ${id}`);
    };
  }]);
};
