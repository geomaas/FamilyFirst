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
            // angular.copy(response.data, allSongList);
          })
          // console.log("allsongs arrar", allSongList);
          // return allSonglist
      },
      addTask: function(){
        console.log(`add a new task`);
        $http({
              method: 'POST',
              url: '/new',
          }).then(function(response) {
            console.log(response);
            // angular.copy(response.data, allSongList);
          })
      }

    };

  }]);
};
