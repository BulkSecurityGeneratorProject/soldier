angular.module('battle')
.service('signUp',function($http,$q){
	this.getData = function(form){
		var defer = $q.defer();
		$http({
			method:'post',
			url:'/api/players',
			data:form
		}).success(function(response){
				defer.resolve(response);
			})
			.error(function(response){
				defer.resolve(response);
			})
			return defer.promise;
	}
})

