luckynumbersApp.controller('MisProgramacionesController', function ($scope, Jobs) {

//angular.module('luckynumbersApp').controller('MisProgramacionesController',
//    ['$scope', function ($scope, Jobs) {

        $scope.success = null;
        $scope.error = null;

        var programaciones = Jobs.get();

        $scope.programaciones = programaciones;

        $scope.noIniciados  = 0;
        $scope.enProceso = 0;
        $scope.finalizados = 0;
        $scope.showDetails = false;
        $scope.filtroactual = '*';
        $scope.totalJobs  = 0;

        Jobs.get(function(data) {
           programaciones = data;
           $scope.seleccionado =  data[0];
           $scope.totalJobs = data.length;
           angular.forEach(data, function(job) {
                       if (job.state == "NOT_STARTED") {
                           $scope.noIniciados++;
                       }

                       if (job.state == "IN_PROGRESS") {
                           $scope.enProceso++;
                       }

                       if (job.state == "DONE") {
                           $scope.finalizados++;
                       }
                   });
         });

        $scope.modal = {
        	"title":  "Confirmar",
        	"message": "¿Esta seguro que desea eliminar la programación?",
        	"button1": "Aceptar",
        	"button2": "Cancelar"

        }

        $scope.isModalOpen = false;

        $scope.filterSeleccionado = function(job)
        {
            if ($scope.filtroactual == '*') {
                return true;
            }

            if(job.state == $scope.filtroactual)
            {
                return true; // this will be listed in the results
            }

            return false; // otherwise it won't be within the results
        };

        $scope.changeSeleccionado = function(cual) {
            $scope.filtroactual = cual;
        }

        $scope.showDetalles = function(id){
          if (!$scope.showDetails){
              $scope.seleccionado = $scope.programaciones[id];
              $scope.showDetails = true;
          } else {
            $scope.showDetails = false;
          }
        }

        $scope.openModal = function(mensaje){
          if (!$scope.isModalOpen){
          	  $scope.modal.message = mensaje;
              $scope.isModalOpen = true;
          }
        }


    });
