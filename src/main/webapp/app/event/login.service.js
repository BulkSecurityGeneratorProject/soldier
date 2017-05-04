angular.module('battle')
.service('login',function($http,$q){
	this.getData = function(form){
		var defer = $q.defer();
		$http({
			method:'post',
			url:'/api/login',
			data:form
		}).success(function(response){
				defer.resolve(response);
			})
			.error(function(){
				defer.reject("error");
			})
			return defer.promise;
	}
})