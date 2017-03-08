(function () {
    'use strict';

    angular
        .module('services')
        .factory('verifyService', verifyService);

    function verifyService($rootScope, $http, $cookies, $q, $location, $state) {
        return {
            checkToken:checkToken
        };
        
        function checkToken(userId, token) {
            return $http({
                url : config.basicUrl + 'verify/'+ userId + "/" + token,
                method : 'POST',
               // data : opts
            })
        }
    }
})();