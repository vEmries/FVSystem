app.controller('myCtrl', function($scope, $http){
   
    $scope.fv = [];
    
    $scope.loadFV = function() {
        $http.get("https://fv-system.herokuapp.com/fv")
                .success(function(data) {
                    $scope.fv = data;
        });
    };
    
});

