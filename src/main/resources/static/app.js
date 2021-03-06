var app=angular.module('iw3',['ngRoute', 'ngAnimate', 'ngTouch', 'ui.bootstrap',
    'ngSanitize', 'angularUtils.directives.dirPagination',
    'angucomplete-alt', 'ngLoadingSpinner', 'ui.select',
    'adaptv.adaptStrap', 'ui-notification', 'ngStomp', 'uiSwitch']);

app.constant('URL_BASE','/');
app.constant('URL_API_BASE','/api/v1/');

app.run(['$location','$log','$rootScope','$uibModal', 'coreService',
    function($location,$log,$rootScope,$uibModal,coreService){
        $log.log('Iniciando');

        $rootScope.cleanLoginData = function() {
            $rootScope.autenticado = false;
            $rootScope.user = {
                fullName: "",
                name : "",
                password : "",
                roles : []
            };
        };

        $rootScope.cleanLoginData();

        $rootScope.relocate=function(loc) {
            $location.path(loc);
        };

        $rootScope.openLoginForm = function(size) {
            $log.log("Abriendo login");
            if (!$rootScope.loginOpen) {
                $rootScope.cleanLoginData();
                $rootScope.loginOpen = true;
                $uibModal.open({
                    animation : true,
                    backdrop : 'static',
                    keyboard : false,
                    templateUrl : 'views/loginForm.html',
                    controller : 'LoginFormController',
                    size : size,
                    resolve : {
                        user : function() {
                            return $rootScope.user
                        }
                    }
                });
            }
        };

        $rootScope.alertaModal =function() {

            var mi=$uibModal.open({
                animation : true,
                backdrop : 'static',
                keyboard : true,
                templateUrl : 'views/alertaModal.html',
                controller : 'AlertaModalController',
                controllerAs: '$ctrl',
                size : 'small'
            });
        };

        //Callback luego de autenticación
        $rootScope.cbauth=false;
        $rootScope.authInfo=function(cb) {
            if(cb) $rootScope.cbauth=cb;
            coreService.authInfo().then(function(resp){
                if(resp.status===200) {
                    $rootScope.user.fullName=resp.data.name;
                    $rootScope.user.name=resp.data.username;
                    $rootScope.user.roles = resp.data.roles;
                    $rootScope.autenticado=true;
                    if($rootScope.cbauth)
                        $rootScope.cbauth();
                }else{
                    $rootScope.autenticado=false;
                    $rootScope.user.roles=[];
                }
            });
        }

        $rootScope.logout = function(callAuthInfo) {
            coreService.logout().then(function(r){
                $rootScope.cleanLoginData();
                if(callAuthInfo) {
                    $rootScope.authInfo();
                }
            },function(){});
        };
    }
]);