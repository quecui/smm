'use strict';

angular.module('app').run(
    function ($rootScope, userService, oauthService, $location, $state, $cookies, $window, $uibModalStack, $stateParams) {
        
        $rootScope.authToken = $cookies.get('auth-token');
        $rootScope.displayName = $cookies.get('display-name');
        $rootScope.avatarUrl = $cookies.get('avatar-url');
        // $rootScope.pageAccessToken = $cookies.get('page-token');



        $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
            console.log("Step: stateChangeStart");
           // alert("URL = " + fromState.url)
            $rootScope.isShowOverlay = false;
            $rootScope.showHeader = true;
            var top = $uibModalStack.getTop();
            if (top) {
                $uibModalStack.dismiss(top.key);
                event.preventDefault();
                return;
            }

            
            var authToken = $cookies.get('auth-token');
            if(toState.name.indexOf("verify")==4 && ($cookies.get('verifyID') == null || $cookies.get('verifyID') == ''))
            {
                $cookies.put('verifyID',toParams.token);
                $state.go("app.login");
                event.preventDefault();
                return;
            }
            if (authToken == undefined || authToken == null) {
                if (toState.name.indexOf("redirect.") == -1
                    && toState.name.indexOf("login") == -1) {
                    $state.go("app.login");//==================
                    event.preventDefault();
                    return;
                }
            }
            else {
                if($cookies.get('verifyID') != null && $cookies.get('verifyID') != ''){
                    $state.go("app.verify");
                    event.preventDefault();
                    return;
                }

                if(toState.name.indexOf("app.login") == 0) {
                    event.preventDefault();
                    $state.go("app.dashboard");
                    return;
                }
            }

            if (fromState.isFather && toState.disableTransient) {
                return;
            }

            window.onresize = null;
        });

        $rootScope.$on("$stateChangeError", function (event, current, previous, rejection) {
            $rootScope.isShowOverlay = false;
        });

        $rootScope.$on("$stateChangeSuccess", function (event, current, previous, rejection) {
            $rootScope.isShowOverlay = false;
        });

        $rootScope.$on("closeOverlay", function (event, current, previous, rejection) {
            $rootScope.isShowOverlay = false;
        });

        $rootScope.validationCommon = function (scope) {
            if (scope.validate()) {
                scope.continous();
                scope.enable = false;
            }
        };

    });