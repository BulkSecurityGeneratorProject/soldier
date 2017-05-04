angular.module('battle')
	.controller('actions', function($scope, $interval, $http, initialSupply, initialArm, arm, supply, player, tips, things) {
		$interval(function() {
			$scope.save();
		}, 60000)
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
		$interval(function() {
			if(player.fatigue > 0) {
				--player.fatigue;
			}
		}, 3000)
		$http.get('app/json/action.json').success(function(data) {
			$scope.actionButton = data;
		})
		$scope.action = function(name) {
			switch(name) {
				case "save":
					$scope.save();
					break;
				case "work":
					$scope.updateC();
					break;
				case "train":
					$scope.updateA();
					break;
				case "shop":
					alert("商店没建好");
					break;
				case "explore":
					$scope.updateD();
					break;
				default:
					break;
			}
		}
		$scope.save = function(){
			$http({
				method:'put',
				url:'/api/players',
				data:player
			}).success(function(data){
				tips.tip = "已保存";
			})
		}
		$scope.updateD = function() {
			console.log(things);
			if(player.fatigue < 99) {
				player.fatigue+=2;
				var r = Math.random();
				if(r>0.8){
					var thing = {};
					thing.belong=player.id;
					var r1;
					if(r>0.9){
						thing.type=0;
						r1 = Math.pow(Math.random(),2)*supply.length;
						thing.pid = Math.ceil(r1);
						tips.tip = "获得食物"+supply[thing.pid-1].name+"!";
					}
					else{
						thing.type=1;
						r1 = Math.pow(Math.random(),2)*arm.length;
						thing.pid = Math.ceil(r1);
						tips.tip = "获得武器"+arm[thing.pid-1].name+"!";
					}
					$http({
						method:'put',
						url:'/api/things',
						data:thing
					}).success(function(response){
						var addThing = [];
						if(response.type == 0){
							var a = supply[response.pid];
							addThing = [response.id,a.name,a.price,a.hp,a.fatigue];
							things.supply.push(addThing);
						}else{
							var a = arm[response.pid];
							addThing = [response.id,a.name,a.price,a.hp,a.fatigue];
							things.arm.push(addThing);
						}
					})
				}else{
					tips.tip = "啥都没得到"
				}
			} else {
				tips.tip = "你太疲劳了，不能进行这次活动";
			}
		}
		$scope.updateC = function() {
			if(player.fatigue < 100) {
				tips.tip = "赚了10元";
				player.fatigue += 1;
				player.money += 10;
			} else {
				tips.tip = "你太疲劳了，不能进行这次活动";
			}
		}
		$scope.updateA = function() {
			if(player.fatigue < 100) {
				var r = Math.random();
				if(r < 0.5) {
					tips.tip = "这次训练并没有卵用";
				} else if(r > 0.75) {
					player.agility += 1;
					tips.tip = "敏捷 +1";
				} else {
					player.strength += 1;
					tips.tip = "力量 +1";
				}
				player.fatigue += 1;
				$scope.updateB();
			} else {
				tips.tip = "你太疲劳了，不能进行这次活动";
			}
		}
		$scope.updateB = function() {
			player.attack = Math.pow(player.strength, 2) * player.agility + player.armOn[3];
			player.defence = Math.pow(player.agility, 2) * player.strength + player.armOn[4];
		}
	})