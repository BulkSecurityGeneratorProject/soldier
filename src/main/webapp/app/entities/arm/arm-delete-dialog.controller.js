(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ArmDeleteController',ArmDeleteController);

    ArmDeleteController.$inject = ['$uibModalInstance', 'entity', 'Arm'];

    function ArmDeleteController($uibModalInstance, entity, Arm) {
        var vm = this;

        vm.arm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Arm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
