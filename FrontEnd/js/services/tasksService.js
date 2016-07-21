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
          // console.log("allsongs arrar", allSongList);
          return allTasksList
      },
      addTask: function(text){
        console.log(`add a new task`);
        $http({
              method: 'POST',
              url: '/addTask',
              data: {
                taskText: text,
              }
          }).then(function(response) {
            console.log(response);
          })
      }

    };

  }]);
};
