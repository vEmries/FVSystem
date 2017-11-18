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
            $scope.loadFV();
        }, function(response) {
            $scope.postResult = "Post Error";
        });
    };
    
    $scope.updateFV = function() {
      
        var data = ({
            id: $scope.upID,
            fvnumber: $scope.upFVNumber,
            contractor: $scope.upContractor,
            duedate: $scope.upDueDate,
            sum: $scope.upSum,
            note: $scope.upNote
        });
        
        $http.post('./fv', data).then(function(response) {
            $scope.updateResult = "Updated";
            $scope.loadFV();
        }, function(response) {
            $scope.updateResult = "Update error";
        });
        
    };
    
    $scope.deleteFV = function(ID) {
        if( confirm('Czy na pewno chcesz skasować tę pozycję?') ){
            $http.delete('./fv/' + ID).then(function(response) {
                $scope.postResult = "Deleted";
                $scope.loadFV();
            }, function(response) {
                $scope.postResult = "Delete Error";
            });
        }
    };
    
});