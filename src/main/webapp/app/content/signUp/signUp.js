angular.module('battle')
.controller('signUp',function($scope,signUp,$http,$state,player,login){
	$http.get('app/json/filter.json').success(function(data){
		$scope.filt = data;
	})
	$scope.filte = function(){
		$scope.ifError == true;
		$scope.signUpFilter();
	}
	$scope.signUp = ''
	$scope.ifError == false;
	$scope.signUpFilter = function(){
		var i = 0;
		$scope.ifError = false;
		$scope.errorInfo = new Array();
		if($scope.repeat != $scope.signUp.password){
			$scope.errorInfo[i++] = $scope.filt.repeat;
			$scope.ifError = true;
		}if($scope.form.password.$invalid){
			$scope.errorInfo[i++] = $scope.filt.mima;$scope.ifError = true;
		}if($scope.form.mail.$invalid){
			$scope.errorInfo[i++] = $scope.filt.mail;$scope.ifError = true;
		}if($scope.form.name.$invalid){
			$scope.errorInfo[i] = $scope.filt.name;$scope.ifError = true;
		}
		if($scope.ifError){
			return true;
		}
		return false;
	}
	$scope.signUpForm = function(){
		if($scope.ifError){
			return false;
		}
		signUp.getData($scope.signUp).then(function(data){
			if(data.message != null){
				$scope.errorInfo[0] = "邮箱已经被使用";
				$scope.ifError = true;
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
			$scope.errorInfo[0] = "服务器不好使"
		})
	}
})