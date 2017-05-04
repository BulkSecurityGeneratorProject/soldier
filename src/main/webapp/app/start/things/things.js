angular.module('battle')
	.controller('things', function(initialArm, initialSupply, things, supply, arm, player, $scope, $http, tips) {
		initialArm.getData().then(function(data) {
			arm = data;
		}, function(data) {
			console.log(data);
		})
		initialSupply.getData().then(function(data) {
			supply = data;
		}, function(data) {
			console.log(data);
		})
		$http.get('/api/getSupply/' + player.id)
			.success(function(data) {
				things.supply = data;
				$scope.supply = things.supply;
			})
		$http.get('/api/getArm/' + player.id)
			.success(function(data) {
				things.arm = data;
				$scope.arm = things.arm;
			})
		$scope.use = function(t) {
			var oldHp = player.hp;
			var oldFatigue = player.fatigue;
			var a = player.hp + $scope.supply[t][3];
			var b = player.fatigue - $scope.supply[t][4];
			player.hp = a > 100 ? 100 : a;
			player.fatigue = b < 0 ? 0 : b;
			tips.tip = "你使用了" + $scope.supply[t][1] + ",生命回复" + (player.hp - oldHp) + ",疲劳减轻" + (-player.fatigue + oldFatigue);
			$scope.deleteThing($scope.supply[t][0]);
			$scope.supply.splice(t,1);
		}
		$scope.desert = function(t){
			tips.tip = "丢弃"+$scope.arm[t][1];
			$scope.deleteThing($scope.arm[t][0]);
			$scope.arm.splice(t,1);
		}
		$scope.ware = function(t) {
			player.attack -= player.armOn[3];
			player.defence -= player.armOn[4];
			player.armOn = $scope.arm[t];
			player.attack += player.armOn[3];
			player.defence += player.armOn[4];
			tips.tip = "你装备了" + $scope.arm[t][1] + ",攻击力增加" + $scope.arm[t][3] + ",防御力增加" + $scope.arm[t][4];
		}
		$scope.deleteThing = function(id){
			$http.delete('/api/things/'+id).success(function(data){
			})
		}
	})