app.controller('LoginCtrl', function($scope, $http) {
  
  $scope.login = function() {
    makeUser($scope);
    $http.post("http://localhost:8080/CoreInterface/login",'', $scope.user)
    .then(function(response) {
      console.log("INSIDE RESPONSE TO /LOGIN");
      console.log(response.data);
      $http.get("http://localhost:8080/CoreInterface/user")
      .then(function(response) {
        console.log("INSIDE RESPONSE TO /USER");
        console.log(response.data);
        
      })
    })
  }
});

function makeUser($scope) {
  var user = {
      params: {
        username: $scope.username,
        password: $scope.password
      }
  };

  $scope.user = user
  console.log(user);
}