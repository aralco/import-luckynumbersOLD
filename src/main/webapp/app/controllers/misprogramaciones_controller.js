angular.module('luckynumbersApp').controller('MisProgramacionesController',
    ['$scope', function ($scope) {


        $scope.programaciones = [
            {
                "id": '2560',
                "sucursal": 'Cochabamba',
                "desde": '77498989',
                "hasta": '77499999',
                 "tipo": 'Libres',
                "programado": '26/10/2014:08:00PM'
            },
            {
                "id": '2561',
                "sucursal": 'Santa Cruz',
               "desde": '77498989',
                "hasta": '77499999',
                 "tipo": 'Congelados',
                "programado": '26/10/2014:08:00PM'
            }
            ,
{
                "id": '2562',
                "sucursal": 'Montero',
                "desde": '77498989',
                "hasta": '77499999',
                 "tipo": 'Libres',
                "programado": '26/10/2014:08:00PM'
            },
            {
                "id": '2563',
                "sucursal": 'La Paz',
                "desde": '77498989',
                "hasta": '77499999',
                "tipo": 'Libres',
                "programado": '26/10/2014:08:00PM'
            }
        ];

        $scope.modal = {
        	"title":  "Confirmar",
        	"message": "¿Esta seguro que desea eliminar la programación?",
        	"button1": "Aceptar",
        	"button2": "Cancelar"

        }

        $scope.isModalOpen = false;

        $scope.openModal = function(mensaje){
          if (!$scope.isModalOpen){
          	  $scope.modal.message = mensaje;
              $scope.isModalOpen = true;
          }
        }


    }]);
