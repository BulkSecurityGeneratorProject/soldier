(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('SupplyDialogController', SupplyDialogController);

    SupplyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Supply'];

    function SupplyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Supply) {
        var vm = this;

        vm.supply = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.supply.id !== null) {
                Supply.update(vm.supply, onSaveSuccess, onSaveError);
            } else {
                Supply.save(vm.supply, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('battleApp:supplyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
