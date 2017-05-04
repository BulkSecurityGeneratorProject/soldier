(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('SupplyDetailController', SupplyDetailController);

    SupplyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Supply'];

    function SupplyDetailController($scope, $rootScope, $stateParams, previousState, entity, Supply) {
        var vm = this;

        vm.supply = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('battleApp:supplyUpdate', function(event, result) {
            vm.supply = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
