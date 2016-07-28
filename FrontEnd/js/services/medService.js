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
