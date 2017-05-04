(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('PlayerDetailController', PlayerDetailController);

    PlayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Player'];

    function PlayerDetailController($scope, $rootScope, $stateParams, previousState, entity, Player) {
        var vm = this;

        vm.player = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('battleApp:playerUpdate', function(event, result) {
            vm.player = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
