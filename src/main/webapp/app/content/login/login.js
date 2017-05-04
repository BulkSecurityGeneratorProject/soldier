angular.module('battle')
.controller('login',function($scope,login,$state,player){
	$scope.errorInfo="";
	$scope.loginForm = function(form){
		login.getData(form).then(function(data){
			if(data.message != null){
				$scope.errorInfo = "账号密码错误";
			}else{
				player.name = data.name;
				player.fatigue = data.fatigue;
				player.hp = data.hp;
				player.money = data.money;
				player.strength = data.strength;
				player.agility = data.agility;
				player.attack = data.attack;
				player.defence = data.defence;
				player.id = data.id;
				player.image = data.image;
				player.mail = data.mail;
				player.password = data.password;
				player.armOn = [0,'',0,0,0];
				login = true;
				$state.go("app.start");
			}
		},function(data){
			$scope.errorInfo = "账号密码错误";
		})
	}
})