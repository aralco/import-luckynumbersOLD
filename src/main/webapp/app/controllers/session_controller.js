angular.module('luckynumbersApp').controller('LoginController', function ($scope, $location, AuthenticationSharedService) {
        $scope.rememberMe = true;
        $scope.login = function () {
            AuthenticationSharedService.login({
                username: $scope.username,
                password: $scope.password
            });
        }
    });

angular.module('luckynumbersApp').controller('LogoutController', function ($location, AuthenticationSharedService) {
        AuthenticationSharedService.logout();
    });
