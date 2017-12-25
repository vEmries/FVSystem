app.controller('archiveCtrl', function($scope, $http, Notification){
    
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