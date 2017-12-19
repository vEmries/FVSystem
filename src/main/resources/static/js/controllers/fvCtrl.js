app.controller('fvCtrl', function($scope, $http) {
    
    $scope.loadFV = function() {
        $http.get('./fv')
                .success(function(data) {
                    $scope.allFVs = data;
        });
        
        $http.get('./fvr')
                .success(function(data) {
                    $scope.allRevisions = data;
        });
        
        $http.get('./payment')
                .success(function(data) {
                    $scope.allPayments = data;
        });
    };

    $scope.addFV = function() {
        
        var data = ({
            fvnumber: $scope.addFVNumber,
            contractor: $scope.addContractor,
            issuedate: $scope.addIssueDate,
            duedate: $scope.addDueDate,
            value: $scope.addValue,
            note: $scope.addNote
        });
        
        $http.post('./fv', data).then(function(response) {
            $scope.postResult = "Posted";
            $scope.loadFV();
        }, function(response) {
            $scope.postResult = "Post Error";
        });
    };
    
    $scope.updateFV = function(upID, upFVNumber, upContractor, upIssueDate, upDueDate, upValue, upNote) {
      
        var data = ({
            id: upID,
            fvnumber: upFVNumber,
            contractor: upContractor,
            issuedate: upIssueDate,
            duedate: upDueDate,
            value: upValue,
            note: upNote
        });
        
        $http.put('./fv', data).then(function(response) {
            $scope.updateResult = "Updated";
            $scope.loadFV();
        }, function(response) {
            $scope.updateResult = "Update error";
        }); 
    };
    
    $scope.deleteFV = function(ID) {
        if(confirm('Czy na pewno chcesz usunąć fakturę?')){
            $http.delete('./fv/' + ID).then(function(response) {
                $scope.postResult = "Deleted";
                $scope.loadFV();
            }, function(response) {
                $scope.postResult = "Delete Error";
            });
        }
    };
    
    $scope.addRevision = function(fvID, addFVNumber, addIssueDate, addQuota, addNote) {
        
        var data = ({
            fvnumber : addFVNumber,
            fv : fvID,
            issuedate : addIssueDate,
            quota : addQuota,
            note : addNote
        });
        
        $http.post('./fvr', data).then(function(response) {
            $scope.postResult = "Posted";
            $scope.loadFV();
        }, function(response) {
            $scope.postResult = "Post Error";
        });
    };
    
//    $scope.updateRevision = function(upRID, upRFVNumber, upRFV, upRIssueDate, upRQuota, upRNote) {
//     
//        var data =({
//            id : upRID,
//            fvnumber : upRFVNumber,
//            fv : upRFV,
//            issuedate : upRIssueDate,
//            quota : upRQuota,
//            note : upRNote
//        });
//        
//        $http.put('./fvr', data).then(function(response) {
//            $scope.updateResult = "Updated";
//            $scope.loadFV();
//        }, function(response) {
//            $scope.updateResult = "Update Error";
//        });
//    };
    
    $scope.deleteRevision = function(ID) {
        if(confirm('Czy na pewno chcesz usunąć korektę?')) {
            $http.delete('./fvr/' + ID).then(function(response) {
                $scope.postResult = "Deleted";
                $scope.loadFV();
            }, function(response) {
                $scope.postResult = "Delete Error";
            });
        }
    };
    
//    $scope.loadPayment = function() {
//        $http.get('./payment')
//                .success(function(data) {
//                    $scope.allPayments = data;
//        });
//    };
    
});