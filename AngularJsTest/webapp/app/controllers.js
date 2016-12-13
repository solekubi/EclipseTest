var app = angular.module('myApp', []);

app.controller('PhoneListCtrl',function($scope,$rootScope){
	$scope.phones = [
	                 {"name": "Nexus S",
	                  "snippet": "Fast just got faster with Nexus S."},
	                 {"name": "Motorola XOOM with Wi-Fi",
	                  "snippet": "The Next, Next Generation tablet."},
	                 {"name": "MOTOROLA XOOM合适",
	                  "snippet": "The Next, Next Generation tablet."}
	               ];
	$rootScope.name = '尾声'
})

app.directive("max", function() {
    return {
        template : "<h1>自定义命令方式!</h1>"
    };
});

app.controller('costCtrl',function($scope){
	$scope.quantity = 1;
	$scope.price = 9.99;
});

app.controller('urlCtrl', function($scope, $location) {
    $scope.myUrl = $location.absUrl();
});

app.controller('httpCtrl', function($scope, $http) {
	  $http.get("test.php").then(function (response) {
	      $scope.myWelcome = response.data;
	  });
	});

app.service('hexafy',function(){
	this.myFunc = function (test) {
	return 	test.toString(16);
	}
});

app.controller('serviceCtrl',function($scope,hexafy){
	$scope.hex = hexafy.myFunc(255);
});

app.controller('selectCtrl',function($scope){
	$scope.sites = [
	        	    {site : "Google", url : "http://www.google.com"},
	        	    {site : "Runoob", url : "http://www.runoob.com"},
	        	    {site : "Taobao", url : "http://www.taobao.com"}
	        	];
})

