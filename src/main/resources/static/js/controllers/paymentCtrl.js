app.controller('paymentCtrl', function($scope, $http, Notification) {
    
    $scope.loadPayments = function() {
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
        
        $http.get('./contractor')
                .success(function(data) {
                    $scope.allContractors = data;
        });
        
        $http.get('./contractor/address')
                .success(function(data) {
                    $scope.allAddresses = data;
        });
    };
    
    $scope.addNote = '';

    $scope.sortProperty = 'duedate';
    $scope.sortReverse = false;
    
    $scope.sortBy = function(sortProperty) {
        $scope.sortReverse = ($scope.sortProperty === sortProperty) ? !$scope.sortReverse : false;
        $scope.sortProperty = sortProperty;
    };

    $scope.addPayment = function(fvID, addQuota, addIssueDate, addNote) {

        var data = ({
            fv: fvID,
            issuedate: addIssueDate,
            quota: addQuota,
            note: addNote
        });

        $http.post('./payment', data).then(function(response) {
            Notification.primary('Dodano płatność');
            $scope.loadPayments();
        }, function(response) {
            Notification.error('Dodanie płatności nie powiodło się');
        });
    };
    
    $scope.deletePayment = function(ID) {
        if(confirm('Czy na pewno chcesz usunąć płatność?')) {
            $http.delete('./payment/' + ID).then(function(response) {
                Notification.warning('Usunięto płatność');
                $scope.loadPayments();
            }, function(response) {
                Notification.error('Usunięcie płatności nie powiodło się');
            });
        }
    };
    
});