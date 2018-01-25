app.controller('fvCtrl', function($scope, $http, Notification) {
    
    $scope.loadFV = function() {
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
        
        $http.get('./fv')
                .success(function(data) {
                    $scope.allFVs = data;
        });
    };
    
//    $scope.getContractorByID = function(ID) {
//        for(var i = 0, iLen = allContractors.length; i < iLen; i++) {
//            if (allContractors[i].id == ID) return allContractors[i];
//        }
//    };

    $scope.addFV = function() {
        
        var data = ({
            fvnumber: $scope.addFVNumber,
            contractor: $scope.addContractor.id,
            issuedate: $scope.addIssueDate,
            duedate: $scope.addDueDate,
            value: $scope.addValue,
            note: $scope.addNote
        });
        
        $http.post('./fv', data).then(function(response) {
            Notification.primary('Dodano fakturę ' + data.fvnumber);
            $scope.loadFV();
        }, function(response) {
            Notification.error('Dodanie faktury ' + data.fvnumber + ' nie powiodło się');
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
            Notification.primary('Zaktualizowano fakturę ' + data.fvnumber);
            $scope.loadFV();
        }, function(response) {
            Notification.error('Aktualizacja faktury' + data.fvnumber + ' nie powiodła się');
        }); 
    };
    
    $scope.deleteFV = function(ID) {
        if(confirm('Czy na pewno chcesz usunąć fakturę?')){
            $http.delete('./fv/' + ID).then(function(response) {
                Notification.warning('Usunięto fakturę');
                $scope.loadFV();
            }, function(response) {
                Notification.error('Nie udało się usunąć faktury');
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
            Notification.primary('Dodano korektę ' + data.fvnumber);
            $scope.loadFV();
        }, function(response) {
            Notification.error('Dodanie korekty' + data.fvnumber + 'nie powiodło się');
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
    
    $scope.autoArchive = function() {
        $http.post('./archive').then(function(response) {
            Notification.primary('Zarchiwizowano faktury: ' + response);
            $scope.loadFV();
        }, function(response) {
            Notification.error('Archiwizacja faktur nie powiodła się');
        });
        
        //Trzeba poprawić notyfikację, bo źle wyświetla response
        //Response to List<FV>, które zarchiwizował
    };
    
});