'use strict';

var dashboard = angular.module('dashboard', []);

dashboard.config(['$stateProvider', function ($stateProvider,$scope,$stateParams,$rootScope) {
    $stateProvider
        .state('app.dashboard', {
            url: '/dashboard',
            templateUrl: 'src/app/modules/dashboard/dashboard.html',
            controller: 'dashboardCtrl',
            resolve: {
                initialPageData: ['pageService', function (pageService) {
                    return pageService.getPage()
                        .then(function (response) {
                            return response.data;
                        });
                }]
            }
        }) 
        .state('app.dashboard.page', {
            url: '/page',
            templateUrl: 'src/app/modules/page/page-list.html',
            controller: 'pageCtrl'
        })
        .state('app.dashboard.page-detail', {
            url: '/page/:pageId',
            templateUrl: 'src/app/modules/page-detail/page-detail.html',
            controller: 'pageDetailCtrl',
            resolve: {
                initialPageDetailData: ['pageService','$stateParams', function (pageService,$stateParams) {
                    return pageService.getPageDetail($stateParams.pageId)
                        .then(function (response) {
                            return response.data;
                        }, function (error) {
                            console.log(error);
                        })
                }]
            }
        })
        .state('app.dashboard.page-detail.inbox', {
            url: '/inbox',
            templateUrl: 'src/app/modules/inbox/inbox.html',
            controller: 'inboxCtrl'
        })
        .state('app.dashboard.page-detail.rule-page', {
            url: '/rule-page',
            templateUrl: 'src/app/modules/rule-page/rule-page.html',
            controller: 'rulePageCtrl',
            resolve:{
                initialRuleData: ['ruleService','$stateParams', function (ruleService,$stateParams) {
                    return ruleService.getRule($stateParams.pageId)
                        .then(function (response) {
                            return response.data;
                        });
                }]
            }
        })
        .state('app.dashboard.page-detail.manage-user', {
            url: '/manage-user',
            templateUrl: 'src/app/modules/manage-user/manage-user.html',
            controller: 'manageUserCtrl'
        })
}]);