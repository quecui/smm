'use strict';

angular.module('app')
    .run(
    ['$rootScope', '$state', '$stateParams',
        function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }
    ]
)
    .config(
    ['$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider, $stateParams) {
            $urlRouterProvider
                .otherwise('/app');
            $stateProvider
                .state('app', {
                    url: '/app',
                    templateUrl: 'src/app/app.html',
                    controller: 'appCtrl'
                })

        }
    ]
)
;
