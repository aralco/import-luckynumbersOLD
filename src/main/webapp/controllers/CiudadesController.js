luckynumbersApp.controller('CiudadesController', function ($scope, $filter, GetCities, NewCity, UpdateCity) {


	$scope.loading = true;
    $scope.addMode = false;
    $scope.editMode = false;
    $scope.formError = false;
    $scope.newCity = {};


	GetCities.get(function(data) {
	   $scope.ciudades = data;
	 });

	$scope.toggleEdit = function (city) {  
	        $scope.editMode = !$scope.editMode; 
	    };  
     $scope.toggleAdd = function () {  
	        $scope.addMode = !$scope.addMode;  
	    }; 

	 $scope.add = function () { 

	 		NewCity.post({}, $scope.newCity, function(data) {
	 		                                $scope.data = function() { return GetCities.get(function(data) {
	   $scope.ciudades = data;
	 }); }
	 		                                $scope.addMode = false;
	 		                           },
	 		                           function(error) {
	 		                             $scope.formError = true;
	 		                             $scope.errorMessage = error.statusText;

	 		                           });


	 }


	 $scope.save = function(activo) {
	 	var params = {Id:activo.id};
	 	UpdateCity.post(params,activo);
	 	$scope.data = function() { return GetCities.get(); }
	 	$scope.editMode = !$scope.editMode; 
	 }
	});