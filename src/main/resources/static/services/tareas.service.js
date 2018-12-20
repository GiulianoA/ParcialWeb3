angular.module('iw3')
    .factory('tareasService',function($http,URL_API_BASE){

        return {
            tarea: function () {
                return $http.get(URL_API_BASE + "tareas");
            },
            add : function(tarea){
                return $http.post(URL_API_BASE+"tareas",tarea);
            },
            delete : function(id){
                return $http.delete(URL_API_BASE+"tareas/"+id);
            },
            mov : function(id,tarea){
                return $http.put(URL_API_BASE+"tareas/"+id,tarea);
            },
            listTareasByLista : function(nombreLista){
            	return $http.get(URL_API_BASE + "tareas/?buscar="+nombreLista);
            },
            tareasByListaSort: function(nombreLista,sort){
            	return $http.get(URL_API_BASE + "tareas/?buscar="+nombreLista+"&ordenar="+sort);
            }
        }

    });