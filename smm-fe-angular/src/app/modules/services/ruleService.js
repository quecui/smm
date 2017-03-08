
(function () {
    'use strict';

    angular
        .module('services')
        .factory('ruleService', ruleService);

    function ruleService($rootScope, $http, $cookies, $q, $location, $state) {

        return {
            getRule: getRule,
            addRule: addRule,
            deleteRule: deleteRule
        };
        function getRule(pageId) {
            return $http ({
                url: config.basicUrl + "rule/page/" + pageId,
                method: 'GET'
            })
        }
        function addRule(opts){
            return $http({
                url:  config.basicUrl + "rule",
                method: 'POST',
                data: opts
            });
        }
        function deleteRule(rule){
            return $http({
                url:  config.basicUrl + "/delete/rule/" + rule.ruleId,
                method: 'DELETE'
            })
        }

    }
})();