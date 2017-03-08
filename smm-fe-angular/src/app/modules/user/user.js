'use strict';

var user = angular.module('user', []);

user.config(['$stateProvider',  function ($stateProvider, $stateParams) {
    $stateProvider
        .state('app.login', {
            url: '/login',
            templateUrl: 'src/app/modules/user/login.html',
            controller: 'loginCtrl'
        })
}]);