app.controller('paymentCtrl', function($scope, $http, Notification) {
    
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