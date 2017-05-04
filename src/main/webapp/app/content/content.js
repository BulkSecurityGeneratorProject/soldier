angular.module('battle')
.controller('content',function($state,login){
	if(login == true){
		$state.go('app.start')
	}else{
		$state.go('app.common')
	}
})