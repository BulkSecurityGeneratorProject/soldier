angular.module('battle')
.config(function($stateProvider){
		$stateProvider.state('app',{
			views:{
				'navbar':{
					templateUrl:'app/head/head.html',
					controller:'navbar'
				},
				'content':{
					templateUrl:'app/content/content.html',
					controller:'content'
				}
			}
		})
})
.value("login",false)
.value("player",{})
.value("things",{})
.value('supply',[])
.value('arm',[])
.value('tips',{'tip':'you are in battle!'})