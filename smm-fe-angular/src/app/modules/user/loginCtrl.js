'use strict';

angular
    .module('user')
    .controller('loginCtrl',
    function ($scope, $rootScope, $location, $window, $cookies, userService, oauthService, $stateParams, $state) {

        $scope.initData = initData;
        $scope.loginFB = loginFB;
        $scope.exchangeToken = exchangeToken;

        $scope.mes = $cookies.get('verifyID');
        function initData() {
            // init Facebook login
            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {
                    return;
                }
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));

            window.fbAsyncInit = function () {
                FB.init({
                    appId : OAuthConfig.facebook.appId,
                    cookie : false,
                    xfbml : true,
                    version : OAuthConfig.facebook.graphAPIVersion
                });
            };
        }

        function loginFB() {
            FB.login(function (response) {
                $scope.exchangeToken(
                    OAuthProvider.FACEBOOK,
                    response.authResponse.accessToken
                );
            },
                {scope:'public_profile, manage_pages, publish_pages, pages_show_list'}
            );
        }
        
        function exchangeToken(provider, accessToken) {
            var opts = {
                provider: provider,
                data: {
                    token: accessToken
                }
            };
            
            if( $scope.mes != null && $scope.mes != ''){ // check token exist on link
                oauthService.addToken($scope.mes);
                oauthService.exchangeToken(
                    opts,'2',
                    function (response) {
                        console.log(response);
                    }, function (error) {
                        console.log(error);
                    });
            }else{
                oauthService.exchangeToken(
                    opts,'1',
                    function (response) {
                        console.log(response);
                    }, function (error) {
                        console.log(error);
                    });
            }
            
        }
        $scope.initData();
    });
