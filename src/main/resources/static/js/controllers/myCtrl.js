app.controller('myCtrl', function($scope, $http){
   
    $scope.fv = [];
    
    $scope.loadFV = function() {
        $http.get("https://localhost:8080/fv")
                .success(function(data) {
                    $scope.fv = data;
        });
    };
    
});

