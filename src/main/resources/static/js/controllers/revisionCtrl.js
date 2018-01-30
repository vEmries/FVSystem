app.controller('revisionCtrl', function($scope, $http, Notification) {
    
    $scope.loadFV = function() {
        $http.get('./fv')
                .success(function(data) {
                    $scope.allFVs = data;
            
                    $scope.allFVsID = [];
                    for(var i = 0; i < data.length; i++){
                        $scope.allFVsID[ data[i].id ] = data[i]; 
                    }
        });
        
        $http.get('./fvr')
                .success(function(data) {
                    $scope.allRevisions = data;
        });
        
        $http.get('./payment')
                .success(function(data) {
                    $scope.allPayments = data;
        });
        
        $http.get('./contractor')
                .success(function(data) {
                    $scope.allContractors = data;
        });
    };
    
    $scope.updateRevision = function(upRID, upRFVNumber, upRFV, upRIssueDate, upRQuota, upRNote) {
     
        var data =({
            id : upRID,
            fvnumber : upRFVNumber,
            fv : upRFV,
            issuedate : upRIssueDate,
            quota : upRQuota,
            note : upRNote
        });
        
        $http.put('./fvr', data).then(function(response) {
            Notification.primary('Edytowano korektę ' + data.fvnumber);
            $scope.loadFV();
        }, function(response) {
            Notification.error('Edycja korekty ' + data.fvnumber + ' nie powiodła się');
        });
    };
    
    $scope.deleteRevision = function(ID) {
        if(confirm('Czy na pewno chcesz usunąć korektę?')) {
            $http.delete('./fvr/' + ID).then(function(response) {
                Notification.warning('Usunięto korektę faktury');
                $scope.loadFV();
            }, function(response) {
                Notification.error('Usunięcie korekty faktury nie powiodło się');
            });
        }
    };
    
});