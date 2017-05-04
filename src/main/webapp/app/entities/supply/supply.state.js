(function() {
    'use strict';

    angular
        .module('battleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('supply', {
            parent: 'entity',
            url: '/supply',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'battleApp.supply.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/supply/supplies.html',
                    controller: 'SupplyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('supply');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('supply-detail', {
            parent: 'supply',
            url: '/supply/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'battleApp.supply.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/supply/supply-detail.html',
                    controller: 'SupplyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('supply');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Supply', function($stateParams, Supply) {
                    return Supply.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'supply',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('supply-detail.edit', {
            parent: 'supply-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supply/supply-dialog.html',
                    controller: 'SupplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Supply', function(Supply) {
                            return Supply.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('supply.new', {
            parent: 'supply',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supply/supply-dialog.html',
                    controller: 'SupplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                hp: null,
                                fatigue: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('supply', null, { reload: 'supply' });
                }, function() {
                    $state.go('supply');
                });
            }]
        })
        .state('supply.edit', {
            parent: 'supply',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supply/supply-dialog.html',
                    controller: 'SupplyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Supply', function(Supply) {
                            return Supply.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('supply', null, { reload: 'supply' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('supply.delete', {
            parent: 'supply',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supply/supply-delete-dialog.html',
                    controller: 'SupplyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Supply', function(Supply) {
                            return Supply.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('supply', null, { reload: 'supply' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
