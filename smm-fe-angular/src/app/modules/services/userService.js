(function () {
    'use strict';

    angular
        .module('services')
        .factory('userService', userService);

    function userService($rootScope, $http, $cookies, $q, $location, $state) {

        return {
            init : init
        };

        function init($location) {
            console.log("[user service] : initState");
        }
    }
})();