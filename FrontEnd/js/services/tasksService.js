module.exports = function(app){

// this service will handle the task data
  app.factory('taskService', ['$http','$location', function($http, $location){
      let allTasksList = [];



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
      addTask: function(text){
        console.log(`add a new task`);
        $http({
              method: 'POST',
              url: '/addTask',
              data: text,

          }).then(function(response) {
            console.log(response);
          })
      }

    };

  }]);
};
