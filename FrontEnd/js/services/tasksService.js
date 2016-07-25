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
