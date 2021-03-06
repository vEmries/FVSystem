app.controller('archiveCtrl', function($scope, $http, Notification){
    
    $scope.sortProperty = 'issuedate';
    $scope.sortReverse = false;
    
    $scope.sortBy = function(sortProperty) {
        $scope.sortReverse = ($scope.sortProperty === sortProperty) ? !$scope.sortReverse : false;
        $scope.sortProperty = sortProperty;
    };
    
    $scope.loadArchive = function() {
        $http.get('./archive/fv')
                .success(function(data) {
                    $scope.archiveFV = data;
        });
        
        $http.get('./archive/fvr')
                .success(function(data) {
                    $scope.archiveRevision = data;
        });
        
        $http.get('./archive/payment')
                .success(function(data) {
                    $scope.archivePayment = data;
        });
        
        $http.get('./contractor')
                .success(function(data) {
                    $scope.allContractors = data;
        });
    };

    $scope.returnFromArchive = function(ID) {
        $http.post('./archive/return/' + ID).then(function(response) {
            Notification.primary('Przywrócono fakturę');
            $scope.loadArchive();
        }, function(response) {
            Notification.error('Przywrócenie faktury nie powiodło się');
        });
    };
    
    $scope.deleteArchive = function(ID) {
        if(confirm('Czy na pewno chceszd usunąć wpis z archiwum?')) {
            $http.delete('./archive/' + ID).then(function(response) {
                Notification.warning('Usunięto rekord z archiwum');
                $scope.loadArchive();
            }, function(response) {
                Notification.error('Usunięcie rekordu z archiwum nie powiodło się');
            });
        }
    };
    
});