'use strict';

angular
    .module('dashboard')
    .controller('dashboardCtrl', function ($scope, $rootScope, pageService,initialPageData,$cookies) {
        console.log('inside dashboard controller');
        
        $scope.pages = initialPageData;
        
        // $scope.getPageDetail = function (page){
        //     // $rootScope.pageAccessToken = page.accessToken;
        //     $cookies.put('page-token', page.accessToken);
        // }
    });