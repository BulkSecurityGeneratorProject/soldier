(function() {
    'use strict';

    angular
        .module('battleApp')
        .controller('ThingsDialogController', ThingsDialogController);

    ThingsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Things'];

    function ThingsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Things) {
        var vm = this;

        vm.things = entity;
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
            if (vm.things.id !== null) {
                Things.update(vm.things, onSaveSuccess, onSaveError);
            } else {
                Things.save(vm.things, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('battleApp:thingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
