app.controller('contractorCtrl', function($scope, $http, Notification){
    
    $scope.loadContractor = function() {
        $http.get('./contractor')
                .success(function(data) {
                    $scope.allContractors = data;
        });
        
        $http.get('./contractor/address')
                .success(function(data) {
                    $scope.allAddresses = data;
        });
    };
    
    $scope.addContractor = function() {
        
        var dataContractor = ({
           company : $scope.addCompany,
           nip : $scope.addNip,
           bank : $scope.addBank,
           account : $scope.addAccount,
           contactnr : $scope.addContactnr,
           mail : $scope.addMail,
           note : $scope.addNote
        });
        
        var dataAddress = ({
           country : $scope.addCountry,
           province : $scope.addProvince,
           city : $scope.addCity,
           zip : $scope.addZip,
           street : $scope.addStreet
        });
        
        $http.post('./contractor/address', dataAddress).then(function(response) {
            $scope.postResult = "Posted";
        }, function(response) {
            $scope.postResult = "Post Error";
        });
        
        $http.post('./contractor', dataContractor).then(function(response) {
            Notification.primary('Dodano kontrahenta ' + dataContractor.company);
            $scope.loadContractor();
        }, function(response) {
            Notification.error('Dodanie kontrahenta ' + dataContractor.company + ' nie powiodło się');
        });
    };
    
    $scope.updateContractor = function(upContractorID, upCompany, upNip, upBank, upAccount, upContactnr, upMail, upNote,
                                        upAddressID, upCountry, upProvince, upCity, upZip, upStreet) {
        
            var dataContractor = ({
                id : upContractorID,
                company : upCompany,
                nip : upNip,
                bank : upBank,
                account : upAccount,
                contactnr : upContactnr,
                mail : upMail,
                note : upNote
            });

            $http.put("./contractor", dataContractor).then(function(response) {
                Notification.primary('Zaktualizowano dane kontrahenta ' + dataContractor.company);
            }, function(response) {
                Notification.error('Aktualizacja danych kontrahenta ' + dataContractor.company + ' nie powiodła się');
            });

            var dataAddress = ({
                id : upAddressID,
                country : upCountry,
                province : upProvince,
                city : upCity,
                zip : upZip,
                street : upStreet
            });

            $http.put("./contractor/address", dataAddress).then(function(response) {
                Notification.primary('Zaktualizowane kontrahenta ' + dataContractor.company);
                $scope.loadContractor();
            }, function(response) {
                Notification.error('Aktualizacja kontrahenta ' + dataContractor.company + ' nie powiodła się');
            });
        };
        
        $scope.deleteContractor = function(ID) {
            if(confirm('Czy na pewno chcesz usunąć kontrahenta?')) {
                $http.delete('./contractor/' + ID).then(function(response) {
                    Notification.warning('Usunięto kontrahenta');
                    $scope.loadContractor();
                }, function(response) {
                    Notification.error('Usuwanie kontrahenta nie powiodło się');
                });
            }
        };
    
});