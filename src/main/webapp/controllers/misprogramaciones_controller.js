luckynumbersApp.controller('MisProgramacionesController', function ($scope, $filter, Jobs, DeleteJob, ModifyJob, GetCities, NewTask, DeleteTask, UpdateTask) {

//angular.module('luckynumbersApp').controller('MisProgramacionesController',
//    ['$scope', function ($scope, Jobs) {

        $scope.success = null;
        $scope.error = null;

        var programaciones = Jobs.get();
        //$scope.ciudades = GetCities.get();

        $scope.programaciones = programaciones;

        $scope.noIniciados  = 0;
        $scope.enProceso = 0;
        $scope.conError = 0;
        $scope.finalizados = 0;
        $scope.showDetails = false;
        $scope.filtroactual = 'ALL';
        $scope.totalJobs  = 0;
        $scope.tipoNumeros = "FROZEN";
        $scope.ciudad = 1;

        GetCities.get(function(data) {
           $scope.ciudades = data;
           $scope.friendlyCities = {};
           angular.forEach(data, function(ciudad) {
                      $scope.friendlyCities[ciudad.code] = ciudad.name;
                   });
         });

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

                       if (job.state == "CRITERIA_ACCEPTANCE") {
                           $scope.conError++;
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


        $scope.friendlyState = function (state){

             if (state == "NOT_STARTED") {
                           return "Agendado";
                       }

             if (state == "IN_PROGRESS") {
                           return "En progreso";
                       }

             if (state == "CRITERIA_ACCEPTANCE") {
                           return "Procesado con error";
                       }

              if (state == "DONE") {
                           return "Finalizado";
                       }
        }


        $scope.filterSeleccionado = function(job)
        {
            if ($scope.filtroactual == 'ALL') {
                return true;
            }

            if(job.state == $scope.filtroactual)
            {
                return true; // this will be listed in the results
            }

            return false; // otherwise it won't be within the results
        };

        $scope.changeSeleccionado = function(cual) {
            $( "#" +  $scope.filtroactual).removeClass( "active" );
            $scope.filtroactual = cual;
            $( "#" +  $scope.filtroactual).addClass( "active" );
        }

        $scope.changeFuenteNumeros = function(theCurrent, theOld) {
            $( "#" +  theOld).removeClass( "active" );
            $( "#" +  theCurrent).addClass( "active" );
            $( "#" +  theOld + "S").removeClass( "fui-arrow-right" );
            $( "#" +  theCurrent + "S").addClass( "fui-arrow-right" );
            if (theCurrent == "congelados") {
                $scope.tipoNumeros = "FROZEN";
            }
            if (theCurrent == "libres") {
                $scope.tipoNumeros = "FREE";
            }
        }

        $scope.showDetalles = function(id){
          if (!$scope.showDetails){
              $scope.seleccionado = $scope.programaciones[id];
              $scope.jobActivoId = $scope.programaciones[id].id;
              $scope.showDetails = true;
          } else {
            $scope.showDetails = false;
          }
        }

        $scope.deleteJobByID = function(){
          var params = {id1:$scope.jobActivoId};
          DeleteJob.delete(params);
        }

        $scope.deleteTaskByID = function(){
          var params = {id1:$scope.taskActivaId};
          DeleteTask.delete(params);
        }

        $scope.modifyDateByID = function(){
          var params = {id1:$scope.jobActivoId};
          $scope.serverdate = $filter('date')($scope.modal.updateDate,'yyyy-MM-ddThh:mm:ss') + "-04:00";
          $scope.jobActivoRecord.scheduledDate = $scope.serverdate;
          ModifyJob.update(params,$scope.jobActivoRecord);
        }

        $scope.modifyNowByID = function(){
          var params = {id1:$scope.jobActivoId};
          $scope.jobActivoRecord.now = true;
          ModifyJob.update(params,$scope.jobActivoRecord);
        }

        $scope.updateTaskByID = function(){
          var params = {Id:$scope.taskActivaId};
          $scope.taskActiva.from = $scope.modal.desde;
          $scope.taskActiva.to = $scope.modal.hasta;
          $scope.taskActiva.executionDate = $filter('date')($scope.taskActiva.executionDate,'yyyy-MM-ddThh:mm:ss') + "-04:00";
          $scope.taskActiva.createdDate = $filter('date')($scope.taskActiva.createdDate,'yyyy-MM-ddThh:mm:ss') + "-04:00";
          $scope.taskActiva.lastUpdate = $filter('date')($scope.taskActiva.lastUpdate,'yyyy-MM-ddThh:mm:ss') + "-04:00";
          UpdateTask.post(params,$scope.taskActiva);
        }

        $scope.createTaskByID = function(){
          NewTask.post({jobId: $scope.jobActivoId}, {"city" : parseInt($scope.modal.ciudad),"type":$scope.tipoNumeros,"from" : $scope.modal.desde,"to": $scope.modal.hasta} );
        }

        $scope.editDate = function(indice) {
          if (!$scope.isModalOpen){
            $scope.modal.title = "Modificar fecha programada";
            $scope.modal.showDate = true;
            $scope.modal.message =  "";
            $scope.isModalOpen = true;
            $scope.modal.showCTask = false;
            $scope.modal.showRangos = false;
            $scope.modal.updateDate = $scope.programaciones[indice].scheduledDate;
            $scope.jobActivoId = $scope.programaciones[indice].id;
            $scope.jobActivoRecord = $scope.programaciones[indice];
            $scope.funcionBorrar = $scope.modifyDateByID;
          }

        }

        $scope.playNow = function(mensaje, indice){
          if (!$scope.isModalOpen){
            $scope.modal.message = mensaje;
            $scope.modal.title = "Ejecución"
            $scope.modal.showDate = false;
            $scope.isModalOpen = true;
            $scope.modal.showCTask = false;
             $scope.modal.showRangos = false;
            $scope.jobActivoId = $scope.programaciones[indice].id;
            $scope.jobActivoRecord = $scope.programaciones[indice]
            $scope.funcionBorrar = $scope.modifyNowByID;
          }
        }

        $scope.openModal = function(mensaje, indice){
          if (!$scope.isModalOpen){
           $scope.modal.message = mensaje;
           $scope.modal.title = "Eliminar"
           $scope.modal.showDate = false;
           $scope.modal.showCTask = false;
            $scope.modal.showRangos = false;
           $scope.isModalOpen = true;
           $scope.jobActivoId = $scope.programaciones[indice].id;
           $scope.funcionBorrar = $scope.deleteJobByID;
         }
       }

        $scope.editRangos = function(mensaje, indice){
          if (!$scope.isModalOpen){
           $scope.modal.message = mensaje;
           $scope.modal.title = "Cambiar Rangos"
           $scope.modal.showDate = false;
           $scope.modal.showCTask = false;
           $scope.modal.showRangos = true;
           $scope.isModalOpen = true;
           $scope.taskActivaId = $scope.seleccionado.tasks[indice].id;
           $scope.taskActiva = $scope.seleccionado.tasks[indice];
           $scope.modal.desde = $scope.taskActiva.from;
           $scope.modal.hasta = $scope.taskActiva.to;
           $scope.funcionBorrar = $scope.updateTaskByID;
         }
       }

        $scope.openDeleteTask = function(mensaje, indice){
          if (!$scope.isModalOpen){
           $scope.modal.message = mensaje;
           $scope.modal.title = "Eliminar"
           $scope.modal.showDate = false;
           $scope.modal.showCTask = false;
            $scope.modal.showRangos = false;
           $scope.isModalOpen = true;
           $scope.taskActivaId = $scope.seleccionado.tasks[indice].id;
           $scope.funcionBorrar = $scope.deleteTaskByID;
         }
       }

       $scope.createTask = function() {
        if (!$scope.isModalOpen){
          $scope.modal.message = "Ingrese los rangos de números para la tarea";
          $scope.modal.title = "Crear nueva tarea"
          $scope.modal.showDate = false;
          $scope.modal.showCTask = true;
          $scope.isModalOpen = true;
          $scope.modal.ciudad ="0";
          $scope.modal.desde = "";
          $scope.modal.hasta = "";
          $scope.funcionBorrar = $scope.createTaskByID;
        }
      }


    });
