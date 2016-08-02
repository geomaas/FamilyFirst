module.exports = function(app) {

    // this handles the task manager view
    app.controller('medController', ['$scope', 'userService', 'medService', '$http', function($scope, userService, medService, $http) {

        $scope.medList = medService.getAllMeds();
        $scope.tip = medService.getTip();
        $scope.user = userService.getCurrentUser();

        $scope.newMed = function() {
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
                $scope.dosage = "";
                $scope.frequency = "";
                $scope.instructions = "";
            })
        };


        $scope.give = function(id) {
            console.log(`give medication with id of ${id}`);
            $http({
                method: 'POST',
                url: `/given${id}`,

            }).then(function(response) {
                console.log(response);
                medService.getAllMeds();
            })
        };
        $scope.remove = function(id) {
            console.log(`remove medication with id of ${id}`);
            $http({
                method: 'POST',
                url: `/deleteMed${id}`,

            }).then(function(response) {
                console.log(response);
                medService.getAllMeds();
            })
        };
        $scope.model = {};
        $scope.timer = function(timeGivenHr, frequency) {
            let date = new Date();
            let ctHrs = date.getHours();
            // console.log("current time hrs",ctHrs);
            let elapsedTimeHr = ctHrs - timeGivenHr;
            // console.log("hrs elapsed ", elapsedTimeHr);
            // console.log("frequency", frequency);
            let percentage = elapsedTimeHr / frequency;
            // console.log("percentage",percentage*100);
            // let bar = $scope.model.specBar[index]
            let widthPercent = percentage * 100;
            console.log(widthPercent);
            // bar.style.width = `${widthPercent}%`;
            if (widthPercent >= 75 && widthPercent < 100) {
                return {
                    "width": `${widthPercent}%`,
                    "background-color": "#81F04F"
                }
                // bar.style.backgroundColor='green';
            } else if (widthPercent >= 50 && widthPercent < 75) {
                return {
                    "width": `${widthPercent}%`,
                    "background-color": "yellow"
                } // bar.style.backgroundColor='yellow';
            } else if (widthPercent < 50 && widthPercent > 0) {
                return {
                    "width": `${widthPercent}%`,
                    "background-color": "red"
                }
            } else if (widthPercent >= 100) {
                return {
                    "width": "100%",
                    "background-color": "green"
                }
            } else if (widthPercent === 0) {
                return {
                    "width": "10%",
                    "background-color": "red"
                }
            } else if (widthPercent < 0){
              return {
                  "width": "100%",
                  "background-color": "green"
              }
            }
        }


    }]);
};
