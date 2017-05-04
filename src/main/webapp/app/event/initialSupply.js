angular.module('battle')
.service('initialSupply',function($http,$q){
	this.getData = function(){
		var defer = $q.defer();
		$http.get('/api/supplies')
		.success(function(response){
			defer.resolve(response);
		})
		.error(function(){
			defer.reject("error")
		})
		return defer.promise;
	}
})