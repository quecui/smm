'use strict';

angular
    .module('verify')
    .controller('verifyCtrl', function ($state, $scope, $rootScope,$cookies,oauthService, verifyService ) {
        console.log('inside dashboard controller');
        $scope.result = {};
        $scope.param = $cookies.get('verifyID');
        $cookies.remove('verifyID');
        $scope.userId = $cookies.get('user-id');
        verifyService.checkToken($scope.userId, $scope.param).then(function (response) {
            var result = response.data;
            
            if(result == 'success'){

                $scope.result.message = "YOU VERIFIED SUCCESSFULLY";
            }else{
                $scope.result.message = "VERIFY FAIL";
            }
        })

        $scope.returnDashboard = function () {
            $state.go("app.dashboard");
        }
        
    });