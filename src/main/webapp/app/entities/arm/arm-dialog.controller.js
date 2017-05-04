(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ArmDialogController', ArmDialogController);

    ArmDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Arm'];

    function ArmDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Arm) {
        var vm = this;

        vm.arm = entity;
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
            if (vm.arm.id !== null) {
                Arm.update(vm.arm, onSaveSuccess, onSaveError);
            } else {
                Arm.save(vm.arm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('battleApp:armUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
