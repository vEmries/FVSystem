app.controller('fvCtrl', function($scope, $http) {
    
    $scope.allFVs = [];
    
    $scope.loadFV = function() {
        $http.get("http://localhost:8080/fv")
                .success(function(data) {
                    $scope.allFVs = data;
        });
    };
    
});