app.controller('myCtrl', function($scope, $http){
   
    $scope.fv = [];
    
    $scope.loadFV = function() {
        $http.get("http://localhost:8080/fv")
                .success(function(data) {
                    $scope.fv = data;
        });
    };
    
});

