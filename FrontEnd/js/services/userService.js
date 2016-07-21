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
                username: user,
                password: pass,
              }
          }).then(function(response) {
            console.log("here is whats coming back", response );
            // from APITunes:
            // if(response.data.isArtist === true){
            //   $location.path('/artist');
            //   angular.copy(response.data, currentUser )
            //   console.log(currentUser);
            // }else if (response.data.isUser === true) {
            //   $location.path('/guest');
            //   angular.copy(response.data, currentUser )
            //   console.log(currentUser);
            //
            // }
          })
      },



    };

  }]);
};
