luckynumbersApp.controller('NuevaProgramacionController', function ($scope, $filter, $location, GetCities, NewTask, NewJob, Jobs) {

	$scope.tareas = [];
	$scope.inmediato = false;


	$scope.newJobFn = function() {
		$scope.serverdate = $filter('date')($scope.new.date,'yyyy-MM-ddThh:mm:ss') + "-04:00";
		NewJob.post({},
			{"name" : $scope.new.nombre,"description": $scope.new.nombre,
			 "now" : $scope.inmediato,"scheduledDate": $scope.serverdate , 'tasks':$scope.tareas} );
		$scope.data = function() { return Jobs.get(); }
		$location.path('/mprogramaciones').replace();
	}

});
