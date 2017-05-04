(function() {
    'use strict';
    angular
        .module('battleApp')
        .factory('Supply', Supply);

    Supply.$inject = ['$resource'];

    function Supply ($resource) {
        var resourceUrl =  'api/supplies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
