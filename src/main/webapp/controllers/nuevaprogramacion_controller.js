luckynumbersApp.controller('NuevaProgramacionController', function ($scope, $filter, $location, GetCities, NewTask, NewJob, Jobs) {

	$scope.tareas = [];
	$scope.inmediato = false;
	$scope.dateNow  = !$scope.inmediato;

	$scope.changeClick = function() {
		$scope.inmediato = !$scope.inmediato;
		$scope.dateNow  = !$scope.inmediato;
	}

	$scope.newJobFn = function() {
		$scope.inmediato = document.getElementById("checkbox1").checked;
		if (!$scope.inmediato) {
			$scope.serverdate = $filter('date')($scope.new.date,'yyyy-MM-ddThh:mm:ss') + "-04:00";
		} else {
			$scope.serverdate = "0000-00-00T00:00:00-00:00";
		}
		NewJob.post({},
			{"name" : $scope.new.nombre,"description": $scope.new.nombre,
			 "now" : $scope.inmediato,"scheduledDate": $scope.serverdate , 'tasks':$scope.tareas} );
		$scope.data = function() { return Jobs.get(); }
		$location.path('/mprogramaciones').replace();
	}

});
