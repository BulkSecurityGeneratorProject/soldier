(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ThingsDetailController', ThingsDetailController);

    ThingsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Things'];

    function ThingsDetailController($scope, $rootScope, $stateParams, previousState, entity, Things) {
        var vm = this;

        vm.things = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('battleApp:thingsUpdate', function(event, result) {
            vm.things = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
