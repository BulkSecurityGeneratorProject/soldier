angular.module('battle')
.service('initialArm',function($http,$q){
	this.getData = function(){
		var defer = $q.defer();
		$http.get('/api/arms')
		.success(function(response){
			defer.resolve(response);
		})
		.error(function(){
			defer.reject("error")
		})
		return defer.promise;
	}
})