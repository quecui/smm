// config

var app =
    angular.module('app')
        .config(
        ['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
            function ($controllerProvider, $compileProvider, $filterProvider, $provide
                       ) {

                // lazy controller, directive and service
                app.controller = $controllerProvider.register;
                app.directive = $compileProvider.directive;
                app.filter = $filterProvider.register;
                app.factory = $provide.factory;
                app.service = $provide.service;
                app.constant = $provide.constant;
                app.value = $provide.value;
            }
        ])
        .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
            function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {
                $urlRouterProvider.when('', '/app/login');

                $httpProvider.defaults.headers.get = {'Content-Type': 'application/json'};
                $httpProvider.defaults.headers.common = {'Content-Type': 'application/json'};
                $httpProvider.defaults.headers.post = {'Content-Type': 'application/json'};
                $httpProvider.defaults.headers.put = {'Content-Type': 'application/json'};
                $httpProvider.defaults.headers.patch = {'Content-Type': 'application/json'};

                $httpProvider.interceptors.push('httpRequestInterceptor');
            }]);