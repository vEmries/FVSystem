app.controller('adminCtrl', function($scope, $http, Notification){
    
    $scope.loadUser = function() {
        $http.get('./admin/user')
                .success(function(data) {
                    $scope.allUsers = data;
        });
    };
    
    $scope.addUser = function() {
        
        var data = ({
            username: $scope.addUsername,
            password: $scope.addPassword,
            role: $scope.addRole
        });
        
        $http.post('./admin/user', data).then(function(response) {
            Notification.primary('Dodano użytkownika ' + data.username);
            $scope.loadUser();
        }, function(response) {
            Notification.error('Dodanie użytkownika ' + data.username + ' nie powiodło się');
        });
    };
    
    $scope.updateUser = function(upID, upUsername, upPassword, upRole) {
      
        var data = ({
            id: upID,
            username: upUsername,
            password: upPassword,
            role: upRole
        });
        
        $http.put('./admin/user', data).then(function(response) {
            Notification.primary('Zaktualizowano użytkownika ' + data.username);
            $scope.loadUser();
        }, function(response) {
            Notification.error('Aktualizacja użytkownika' + data.username + ' nie powiodła się');
        }); 
    };
    
    $scope.deleteUser = function(ID) {
        if(confirm('Czy na pewno chcesz usunąć użytkownika?')){
            $http.delete('./admin/user/' + ID).then(function(response) {
                Notification.warning('Usunięto użytkownika');
                $scope.loadUser();
            }, function(response) {
                Notification.error('Nie udało się usunąć użytkownika');
            });
        }
    };
});