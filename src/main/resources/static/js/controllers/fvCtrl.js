app.controller('fvCtrl', function($scope, $http) {
    
    $scope.loadFV = function() {
        $http.get('./fv')
                .success(function(data) {
                    $scope.allFVs = data;
        });
    };

    $scope.addFV = function() {
        
        var config = {
                headers : {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
        };
        
        var data = ({
            fvnumber: $scope.addFVNumber,
            contractor: $scope.addContractor,
            issuedate: $scope.addIssueDate,
            duedate: $scope.addDueDate,
            sum: $scope.addSum,
            note: $scope.addNote
        });
        
        
        $http.post('./fv', data).then(function(response) {
            $scope.postResult = "Posted";
        }, function(response) {
            $scope.postResult = "Error";
        });
    };
    
});