(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ArmController', ArmController);

    ArmController.$inject = ['Arm'];

    function ArmController(Arm) {

        var vm = this;

        vm.arms = [];

        loadAll();

        function loadAll() {
            Arm.query(function(result) {
                vm.arms = result;
                vm.searchQuery = null;
            });
        }
    }
})();
