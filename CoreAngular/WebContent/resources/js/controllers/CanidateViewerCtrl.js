app.controller('CanidateViewerCtrl', function($scope,$location,$http) {
    
    $scope.fakeRecruits = [];

    $http.get('/fake_db.json')
        .then(function successCallback(res) {
        
        console.log(res);
        for (var i = 0; i < res.data.aes_users.length; i++) {
            var user = res.data.aes_users[i];
            if (user.role_id === 2) {
                $scope.fakeRecruits.push(user);
            }
        }

    });
});