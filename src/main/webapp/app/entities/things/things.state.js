(function() {
    'use strict';

    angular
        .module('battleApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('things', {
            parent: 'entity',
            url: '/things',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'battleApp.things.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/things/things.html',
                    controller: 'ThingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('things');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('things-detail', {
            parent: 'things',
            url: '/things/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'battleApp.things.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/things/things-detail.html',
                    controller: 'ThingsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('things');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Things', function($stateParams, Things) {
                    return Things.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'things',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('things-detail.edit', {
            parent: 'things-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/things/things-dialog.html',
                    controller: 'ThingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Things', function(Things) {
                            return Things.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('things.new', {
            parent: 'things',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/things/things-dialog.html',
                    controller: 'ThingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pid: null,
                                type: null,
                                belong: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('things', null, { reload: 'things' });
                }, function() {
                    $state.go('things');
                });
            }]
        })
        .state('things.edit', {
            parent: 'things',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/things/things-dialog.html',
                    controller: 'ThingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Things', function(Things) {
                            return Things.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('things', null, { reload: 'things' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('things.delete', {
            parent: 'things',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/things/things-delete-dialog.html',
                    controller: 'ThingsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Things', function(Things) {
                            return Things.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('things', null, { reload: 'things' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
