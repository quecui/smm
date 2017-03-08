/**
 * Created by thanh huyền on 10-Jan-17.
 */
(function () {
    'use strict';

    angular
        .module('services')
        .factory('pageService', pageService);

    function pageService($rootScope, $http, $cookies, $q, $location) {

        return {
            addPage : addPage,
            getPage: getPage,
            getPageDetail: getPageDetail,
            getAllUserWithPage : getAllUserWithPage,
            deleteUserWithPage : deleteUserWithPage,
            editRole: editRole,
            inviteUser:inviteUser,
            verifyUser: verifyUser
        };
        
        function addPage(opts){
            return $http({
                url:  config.basicUrl + "page",
                method: 'POST',
                data: opts
            });
        }
        function getPage() {
            return $http ({
                url: config.basicUrl + "page",
                method: 'GET'
            })
        }
        function getPageDetail(pageId){
            return $http({
                url: config.basicUrl + "page/" + pageId ,
                method: 'GET'
            })
        }

        function getAllUserWithPage() {
            return $http({
                url : config.basicUrl + "user",
                method : 'GET'
            })
        }

        function deleteUserWithPage(opts) {
            return $http({
                url : config.basicUrl + "user/" + opts,
                method : 'DELETE'
            })
        }

        function editRole(opts) {
            return $http({
                url: config.basicUrl + "user/" + opts + "/role",
                method : 'PUT'
            })
        }

        function inviteUser(opts) {
            return $http({
                url: config.basicUrl + "user",
                method : 'POST',
                data : opts
            })
        }

        function verifyUser(opts) {
            return $http({
                url : config.basicUrl + "test/" + opts,
                method : 'POST'
            })
        }
    }
})();