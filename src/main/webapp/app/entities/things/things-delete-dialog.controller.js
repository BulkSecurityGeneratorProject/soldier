(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ThingsDeleteController',ThingsDeleteController);

    ThingsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Things'];

    function ThingsDeleteController($uibModalInstance, entity, Things) {
        var vm = this;

        vm.things = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Things.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
