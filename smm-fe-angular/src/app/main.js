'use strict';

angular.module('app')
    .controller('appCtrl',
    function ($rootScope, $scope, $window, $cookies, $state, $timeout) {
        console.log('Inside the AppCtrl');

        $rootScope.logout = logout;

        function logout() {
            $rootScope.authToken = null;
            $rootScope.displayName = null;
            $rootScope.avatarUrl = null;
            $cookies.remove('auth-token');
            $cookies.remove('display-name');
            $cookies.remove('avatar-url');
            $state.go('app.login');
        }
    });

