angular.module('iw3')
    .controller('AlertaModalController', function($log,$uibModalInstance){

        var $ctrl=this;
        $ctrl.status='Ha Ocurrido un problema';
        $ctrl.aceptar=function(){
            $uibModalInstance.close();
        };
    });