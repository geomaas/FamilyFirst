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
