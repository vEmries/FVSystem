app.controller('fvCtrl', function($scope, $http, Notification) {
    
    $scope.currentDate = convertDate(new Date());
    $scope.addNote = '';
    
    $scope.sortProperty = 'duedate';
    $scope.sortReverse = false;
    
    $scope.sortBy = function(sortProperty) {
        $scope.sortReverse = ($scope.sortProperty === sortProperty) ? !$scope.sortReverse : false;
        $scope.sortProperty = sortProperty;
    };
    
    function convertDate(date) {
        var yyyy = date.getFullYear().toString();
        var mm = (date.getMonth()+1).toString();
        var dd  = date.getDate().toString();

        var mmChars = mm.split('');
        var ddChars = dd.split('');

        return yyyy + '-' + (mmChars[1]?mm:"0"+mmChars[0]) + '-' + (ddChars[1]?dd:"0"+ddChars[0]);
    }
    
    $scope.checkPriority = function(fv) {
        var threeLeftDate = new Date();
        threeLeftDate.setDate(threeLeftDate.getDate() + 4);
        
        if (fv.duedate < convertDate(threeLeftDate) && fv.status < 1) {
            return {background : "red", color : "white"};
        };
        
        var fiveLeftDate = new Date();
        fiveLeftDate.setDate(fiveLeftDate.getDate() + 6);
        
        if (fv.duedate < convertDate(fiveLeftDate) && fv.status < 1) {
            return {background : "#f4c542", color : "white"};
        };
    };
    
    $scope.loadFV = function() {
        $http.get('./fv')
            .success(function(data) {
                $scope.allFVs = data;
            });

        $http.get('./fv/new')
            .success(function (data) {
                $scope.newFVs = data;
            })

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

                    $scope.allContractorsID = [];
                    for(var i = 0; i < data.length; i++){
                        $scope.allContractorsID[ data[i].id ] = data[i]; 
                    }
        });
        
        $http.get('./contractor/address')
                .success(function(data) {
                    $scope.allAddresses = data;
        });
    };

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
            Notification.error('Dodanie korekty ' + data.fvnumber + ' nie powiodło się');
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
            Notification.primary('Zarchiwizowano faktury');
            $scope.loadFV();
        }, function(response) {
            Notification.error('Archiwizacja faktur nie powiodła się');
        });
    };

    $scope.loadFV();
    
});