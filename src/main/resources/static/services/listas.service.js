angular.module('iw3')
    .factory('listasService',function($http,URL_API_BASE){

        return {
            list : function(){
                return $http.get(URL_API_BASE+"listas");
            },
            add : function(lista){
                return $http.post(URL_API_BASE+"listas",lista);
            },
            delete : function(id){
                return $http.delete(URL_API_BASE+"listas/"+id);
            },
            listTareas : function(lista){
            	return $http.get(URL_API_BASE+"listas/"+lista)
            }
        };
    });