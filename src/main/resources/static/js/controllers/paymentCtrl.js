app.controller('paymentCtrl', function($scope, $http) {
    
    $scope.loadFV = function() {
        $http.get('./fv')
                .success(function(data) {
                    $scope.allFVs = data;
        });
        $http.get('./fvr')
                .success(function(data) {
                    $scope.allRevisions = data;
        });
    };
    
    $scope.loadPayments = function() {
        $http.get('./payment')
                .success(function(data) {
                    $scope.allPayments = data;
        });
    };
    
    
    $scope.addPayment = function(fvID, addQuota, addIssueDate) {
        
        var data = ({
           fv: fvID,
           issuedate: addIssueDate,
           quota: addQuota,
           note: " "
        });
        
        $http.post('./payment', data).then(function(response) {
           $scope.postResult = "Posted";
           $scope.loadFV();
           $scope.loadPayments();
        }, function(response) {
            $scope.postResult = "Post Error";
        });
    };
    
    $scope.deletePayment = function(ID) {
        if(confirm('Czy na pewno chcesz usunąć płatność?')) {
            $http.delete('./payment/' + ID).then(function(response) {
                $scope.postResult = "Deleted";
                $scope.loadFV();
                $scope.loadPayments();
            }, function(response) {
                $scope.postResult = "Delete Error";
            });
        }
    };
    
});