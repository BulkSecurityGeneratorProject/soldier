(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ThingsController', ThingsController);

    ThingsController.$inject = ['Things'];

    function ThingsController(Things) {

        var vm = this;

        vm.things = [];

        loadAll();

        function loadAll() {
            Things.query(function(result) {
                vm.things = result;
                vm.searchQuery = null;
            });
        }
    }
})();
