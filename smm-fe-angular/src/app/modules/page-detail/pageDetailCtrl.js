
'use strict';

angular
    .module('dashboard')
    .controller('pageDetailCtrl', function ($scope, $rootScope, pageService,$stateParams,initialPageDetailData) {
        console.log('inside page detail controller');
        $scope.pageId = $stateParams.pageId;

        $rootScope.pageAccessToken = initialPageDetailData.accessToken;
  
        
    });