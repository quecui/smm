'use strict';

var verify = angular.module('verify', []);

verify.config(['$stateProvider', function ($stateProvider, $stateParams, oauthService, $cookies) {
    $stateProvider
        .state('app.verify', {
            url: '/verify/:token',
            templateUrl: 'src/app/modules/verify/verify-user.html',
            controller: 'verifyCtrl'
            // resolve: {
            //     initialVerify: ['verifyService','$stateParams','oauthService', function (verifyService,$stateParams,oauthService) {
            //         var token = oauthService.getToken();
            //         var userID = $cookies.get('user-id');
            //         return verifyService.checkToken(token, userID)
            //             .then(function (response) {
            //                 return response.data;
            //             }, function (error) {
            //                 console.log(error);
            //             })
            //     }]
            // }
        })
}]);
