angular.module('iw3')
    .service('APIInterceptor', function($rootScope) {
        var service = this;

        service.responseError = function(response) {
            if(response.status===401) {
                $rootScope.openLoginForm();
            }else if(response.status===406){
                $rootScope.alertaModal();
                $rootScope.authInfo();
            }else if(response.status===404){
                $rootScope.alertaModal();
                $rootScope.authInfo();
        } else{
                $rootScope.authInfo();
            }
            return response;
        };


    });