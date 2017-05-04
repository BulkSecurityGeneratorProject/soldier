(function() {
    'use strict';
    angular
        .module('battleApp')
        .factory('Things', Things);

    Things.$inject = ['$resource'];

    function Things ($resource) {
        var resourceUrl =  'api/things/:id';

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
