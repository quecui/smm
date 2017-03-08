(function () {
    'use strict';

    angular
        .module('services')
        .factory('oauthService', oauthService);

    function oauthService($rootScope, $http, $cookies, $q, $location, $state) {

        var token = '';

        return {
            exchangeToken : exchangeToken,
            addToken:addToken,
            getToken:getToken
        };

        
        function exchangeToken(opts,key, successCallback, failureCallback) {
            return $http({
                url: config.oauthServiceUrl + "/" + opts.provider + '/access-token/exchange',
                method: 'POST',
                data: opts.data
            }).then(function (data) {
                $cookies.put('auth-token', data.data.accessToken.token);
                $cookies.put('display-name', data.data.accessToken.user.name);
                $cookies.put('avatar-url', data.data.accessToken.user.avatarUrl);
                $cookies.put('user-id', data.data.accessToken.user.providerUserId);
                $rootScope.authToken = data.data.accessToken.token;
                $rootScope.displayName = data.data.accessToken.user.name;
                $rootScope.avatarUrl = data.data.accessToken.user.avatarUrl;

                // alert("key = " + key);
                if(key == '1'){
                    $state.go("app.dashboard");
                }
                else{
                    $state.go("app.verify");
                }
                    
                successCallback(data);
            }).catch(function (error) {
                console.log(error);
                failureCallback(error);
            });
        }

        function addToken(opts){
            token = opts;
        }

        function getToken(){
            return token;
        }

    }
})();