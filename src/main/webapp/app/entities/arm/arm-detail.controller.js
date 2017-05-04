(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ArmDetailController', ArmDetailController);

    ArmDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Arm'];

    function ArmDetailController($scope, $rootScope, $stateParams, previousState, entity, Arm) {
        var vm = this;

        vm.arm = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('battleApp:armUpdate', function(event, result) {
            vm.arm = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
