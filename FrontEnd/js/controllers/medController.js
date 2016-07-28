module.exports = function(app){

// this handles the task manager view
  app.controller('medController', ['$scope', 'userService', 'medService', '$http', function($scope, userService, medService, $http){

    $scope.medList = medService.getAllMeds();
    $scope.tip = medService.getTip();
    $scope.user = userService.getCurrentUser();

    $scope.newMed = function(){
      console.log(`send medication info ${$scope.medName}, ${$scope.dosage},${$scope.frequency},${$scope.instructions}`);
      console.log(`add a medication`);
      $http({
            method: 'POST',
            url: '/addMed',
            data: {
              medName: $scope.medName,
              dose: $scope.dosage,
              frequency: $scope.frequency,
              instructions: $scope.instructions,
            },
        }).then(function(response) {
          console.log(response);
            medService.getAllMeds();
            $scope.medName = "";
            $scope.dosage ="";
            $scope.frequency ="";
            $scope.instructions = "";
        })
    };


    $scope.give = function(id){
      console.log(`give medication with id of ${id}`);
      $http({
            method: 'POST',
            url: `/given${id}`,

        }).then(function(response) {
          console.log(response);
          medService.getAllMeds();
        })
    };
    $scope.model = {};
    $scope.timer = function(timeGivenHr,frequency){
      let date = new Date();
      let ctHrs = date.getHours();
      console.log("current time hrs",ctHrs);
      console.log("type of time given",timeGivenHr);
      let elapsedTime = ctHrs - timeGivenHr;
      console.log("elapsed Time", elapsedTime);
      let percentage = elapsedTime / frequency;
      console.log("percentage",percentage*100);
      let bar = document.getElementById('timerBar')
      let widthPercent = percentage*100;
      console.log(widthPercent);
      bar.style.width = `${widthPercent}%`;
      if (widthPercent >= 75 ) {
        bar.style.backgroundColor='green';
      } else if (widthPercent > 25 && widthPercent < 75) {
        bar.style.backgroundColor='yellow';
      } else {
        bar.style.backgroundColor='red';
      }
    }

  }]);
};
