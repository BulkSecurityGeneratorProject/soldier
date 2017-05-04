(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('SupplyController', SupplyController);

    SupplyController.$inject = ['Supply'];

    function SupplyController(Supply) {

        var vm = this;

        vm.supplies = [];

        loadAll();

        function loadAll() {
            Supply.query(function(result) {
                vm.supplies = result;
                vm.searchQuery = null;
            });
        }
    }
})();
