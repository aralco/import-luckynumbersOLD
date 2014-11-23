luckynumbersApp.controller('NuevaProgramacionController', function ($scope, $filter, GetCities, NewTask, NewJob) {

	$scope.tareas = [];
	$scope.inmediato = false;


	$scope.newJobFn = function() {
		$scope.serverdate = $filter('date')($scope.new.date,'yyyy-MM-ddThh:mm:ss') + "-04:00";
		NewJob.post({},
			{"name" : $scope.new.nombre,"description": $scope.new.nombre,
			 "now" : $scope.inmediato,"scheduledDate": $scope.serverdate , 'tasks':$scope.tareas} );
	}

});
