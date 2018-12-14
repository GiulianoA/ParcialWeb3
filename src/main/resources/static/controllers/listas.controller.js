angular.module('iw3')
    .controller('ListasController', function($scope,$http,$log,listasService,tareasService){
        $scope.titulo="Listas";
        $scope.listas=[];	//Array
        $scope.tareas=[];
        $scope.instanciaL={};
        $scope.instanciaT={};
        $scope.instanciaM=false;


        $scope.refresh=function() {
            listasService.list().then(
                function (resp) {   //el lista recibe el json en el data
                    $scope.listas = resp.data;   //el data es un hdrmp no tocar
                },
                function (err) {
                }
            );

            tareasService.tarea().then(
                function (resp) {   //el lista recibe el json en el data
                    $scope.tareas = resp.data;   //el data es un hdrmp no tocar
                },
                function (err) {
                }
            );


        };

        $scope.addListaController=function(){
            listasService.add($scope.instanciaL).then(
                function(resp){
                    $scope.listas.push(resp.data);
                    //$scope.refresh()
                },
                function (err) {
                }
            );
        };

        $scope.deleteListaController=function(id){
            if(!confirm("Desea eliminar la lista seleccionada y todas sus tareas?"))
                return;
            listasService.delete(id).then(
                function(resp){
                    if(resp.status==200) {
                        $scope.listas.forEach(function(o,i){
                            if(o.id==id) {
                                $scope.listas.splice(i,1);
                            }
                        });
                    }
                },
                function(err){}
            );
        };

        $scope.addTareaController=function(){
            tareasService.add($scope.instanciaT).then(
                function(resp){
                    $scope.tareas.push(resp.data);
                },
                function (err) {
                }
            );
        };

        $scope.deleteTareaController=function(id){
            if(!confirm("Desea eliminar la tarea seleccionada?"))
                return;
            tareasService.delete(id).then(
                function(resp){
                    if(resp.status==200) {
                        $scope.tareas.forEach(function(o,i){
                            if(o.id==id) {
                                $scope.tareas.splice(i,1);
                            }
                        });
                    }
                },
                function(err){}
            );
        };


        $scope.refresh();
    });