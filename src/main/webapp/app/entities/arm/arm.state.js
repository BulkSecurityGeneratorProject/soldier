(function() {
    'use strict';

    angular
        .module('battleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('arm', {
            parent: 'entity',
            url: '/arm',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'battleApp.arm.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/arm/arms.html',
                    controller: 'ArmController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('arm');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('arm-detail', {
            parent: 'arm',
            url: '/arm/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'battleApp.arm.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/arm/arm-detail.html',
                    controller: 'ArmDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('arm');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Arm', function($stateParams, Arm) {
                    return Arm.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'arm',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('arm-detail.edit', {
            parent: 'arm-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/arm/arm-dialog.html',
                    controller: 'ArmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Arm', function(Arm) {
                            return Arm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('arm.new', {
            parent: 'arm',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/arm/arm-dialog.html',
                    controller: 'ArmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                attack: null,
                                defence: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('arm', null, { reload: 'arm' });
                }, function() {
                    $state.go('arm');
                });
            }]
        })
        .state('arm.edit', {
            parent: 'arm',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/arm/arm-dialog.html',
                    controller: 'ArmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Arm', function(Arm) {
                            return Arm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('arm', null, { reload: 'arm' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('arm.delete', {
            parent: 'arm',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/arm/arm-delete-dialog.html',
                    controller: 'ArmDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Arm', function(Arm) {
                            return Arm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('arm', null, { reload: 'arm' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
