(function() {
    'use strict';
    angular
        .module('battleApp')
        .factory('Arm', Arm);

    Arm.$inject = ['$resource'];

    function Arm ($resource) {
        var resourceUrl =  'api/arms/:id';

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
