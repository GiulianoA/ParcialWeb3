angular.module('iw3')
    .controller('ListasController', function($scope,$http,$log,$timeout,$uibModal,$rootScope, listasService,tareasService){
        $scope.titulo="Listas";
        $scope.nombrelista="";
        $scope.listas=[];	//Array
        $scope.tareas=[];
        $scope.instanciaL={};
        $scope.instanciaT={};
        $scope.instanciaM=false;
        $scope.listaConTareas={};
        $scope.mostrarAgregarLista=false;
        $scope.mostrarAgregarTarea=false;
        $scope.mostrarDIVENTERO=true;
        $scope.nombreDeLaLista="";


        $scope.refresh=function() {
            listasService.list().then(
                function (resp) {   //el lista recibe el json en el data
                    $scope.listas = resp.data;

                    tareasService.tarea().then(
                        function (resp) {
                            $scope.tareas = resp.data;
                        },
                        function (err) {
                        }
                    );

                },
                function (err) {
                }
            );




        };

        $scope.addListaController=function(){
        	if(!confirm("Desea Agregar la Lista?"))
                return;
            listasService.add($scope.instanciaL).then(
                function(resp){
                    $log.log("Aca si esta entrando");
                    $scope.listas.push(resp.data);
                    $scope.instanciaL={};
                    $scope.mostrarAgregarLista = false;
                    $scope.mostrarDivLista();
                },
                function (err) {
                }
            );
        };

        $scope.addTareaController=function(){
            if(!confirm("Desea Agregar la tarea?"))
                return;
            tareasService.add($scope.instanciaT).then(
                function(resp){
                    //  $scope.tareas.push(resp.data);
                    $log.log("No esta entrando aca");
                    $scope.listaConTareas[resp.data.nombreLista.nombre] = resp.data;
                    $scope.instanciaT={};
                    // $scope.verTareas('backlog');
                    //$scope.mostrarAgregarTarea = false;
                    //$scope.mostrarDIVENTERO = false;
                    $scope.mostrarDivTarea();
                },
                function (err) {
                }
            );
        };
        
        $scope.verTareas=function(nombreLista){
        	$log.log($scope.listas);
        	$scope.nombreDeLaLista=nombreLista;
        	tareasService.listTareasByLista(nombreLista).then(
        			function(resp){
        				if(resp.status==200) {
                            $scope.listaConTareas[nombreLista] = resp.data;
                            $scope.nombrelista=nombreLista;
                        }else if(resp.status==404){
                        	$scope.listaConTareas[nombreLista] = {};
                        	$scope.nombrelista=nombreLista;
                        }
        			},
                    function (err) {
                    }
        			);
        };
        
        $scope.sortBy=function(nombre,sort){
        	tareasService.tareasByListaSort(nombre,sort).then(
        			function(resp){
        				$scope.listaConTareas[nombre]={};
        				$scope.listaConTareas[nombre]=resp.data;
        			}
        			);
        };

        $scope.deleteListaController=function(id){
            if(!confirm("Desea eliminar la lista seleccionada y todas sus tareas?"))
                return;
            listasService.delete(id).then(
                function(resp){
                    if(resp.status===200) {

                            $scope.listas.forEach(function(o,i){
                                if(o.id===id) {
                                    $scope.listas.splice(i,1);
                                    $scope.listaConTareas = {};
                                }
                            });

                    }
                },
                function(err){}
            );
        };



        $scope.deleteTareaController=function(id,nombrelista){
            if(!confirm("Desea eliminar la tarea seleccionada?"))
                return;
            tareasService.delete(id).then(
                function(resp){
                    if(resp.status===200) {
                        for(var index in $scope.listaConTareas[nombrelista]){   //recorro los elementos de la lista de la clave nombrelista
                            if($scope.listaConTareas[nombrelista][index].id === id){ //devuelve el indice, no el elemento
                                $scope.listaConTareas[nombrelista].splice(index,1);
                            }
                        }
                    }
                },
                function(err){}
            );
        };

        $scope.moverTareaController=function(ta,nombrelista){
            if(!confirm("Desea mover la tarea seleccionada?"))
                return;
            var t;
            for(var l in $scope.listas){
                if($scope.listas[l].nombre === nombrelista){
                    t = JSON.stringify({"nombre": ta.nombre,
                        "fechacreacion": ta.fechacreacion,
                        "fechamodificacion": ta.fechamodificacion,
                        "prioridad": ta.prioridad,
                        "nombreLista": {
                            "id": $scope.listas[l].id
                        },
                        "estimacion": ta.estimacion});
                }
            }
            tareasService.mov(ta.id,t).then(
                function(resp){
                    if(resp.status===200) {
                        for(var index in $scope.listaConTareas[ta.nombreLista.nombre]){   //recorro los elementos de la lista de la clave nombrelista
                            if($scope.listaConTareas[ta.nombreLista.nombre][index].id === ta.id){ //devuelve el indice, no el elemento
                                $scope.listaConTareas[ta.nombreLista.nombre].splice(index,1);
                            }
                        }
                    }
                },
                function(err){}
            );
        };
        
        $scope.mostrarBotonGuardarLista=function(){
    		var i=$scope.instanciaL;
    		return i.nombre &&  i.nombre.length>0 && i.nombreSprint && i.nombreSprint.length>0;
    	};
    	
    	$scope.mostrarBotonGuardarTarea=function(){
    		var t=$scope.instanciaT;
    		return t.nombre &&  t.nombre.length>0 && t.prioridad && t.prioridad.length>0 ;
    	};
        
        $scope.mostrarDivLista=function(){
           // $scope.mostrar = false;
            if($scope.mostrarDIVENTERO === false){
                $scope.mostrarDIVENTERO = true;
                $scope.mostrarAgregarLista = false;
                $scope.mostrarAgregarTarea = false;
            }else{
                $scope.mostrarDIVENTERO =false;
                $scope.mostrarAgregarLista = true;
                $scope.mostrarAgregarTarea = false;
            }
        };

        $scope.mostrarDivTarea=function(){
            if($scope.mostrarDIVENTERO === false){
                $scope.mostrarAgregarTarea = false;
                $scope.mostrarAgregarLista = false;
                $scope.mostrarDIVENTERO = true;
            }else{
                $scope.mostrarDIVENTERO = false;
                $scope.mostrarAgregarLista = false;
                $scope.mostrarAgregarTarea = true;
            }
        };

        //$scope.refresh();
        $rootScope.authInfo($scope.refresh);
    });