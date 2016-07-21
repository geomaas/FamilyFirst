module.exports = function(app){

// this handles the login view
  app.controller('loginController', ['$scope', 'userService', function($scope, userService){


    $scope.login = function(){
      console.log(`Trying to Send ${$scope.userName} and ${$scope.password}`);

      userService.serverLogin($scope.userName,$scope.password);

    }



  }]);
};
