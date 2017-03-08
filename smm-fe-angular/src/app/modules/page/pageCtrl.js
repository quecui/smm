
'use strict';
angular
    .module('dashboard')
    .controller('pageCtrl', function ($scope, $rootScope,$location, $cookies,pageService) {
        console.log('inside page list controller');

        $scope.pages = [];

        function init() {
            console.log(window.FB);
            getPage();
        }

        init();

        function getPage() {
            FB.api (
                "/me/accounts?fields=picture,name,id,access_token&access_token=" + $cookies.get('auth-token'),
                function (response) {
                    if (response && !response.error) {
                        console.log(response.data[0]);
                        for (var i=0; i<response.data.length;i++) {
                            $scope.pages.push(response.data[i]);
                        }
                    }
                }
            );
        }

        $scope.addPage = function (page) {

            var request = {
                pageId:   page.id,
                pageName: page.name,
                accessToken: page.access_token,
                avatarPageUrl: page.picture.data.url
            };
            console.log(request);
            pageService.addPage(request)
                .then(function(){
                    console.log("success");
                })
        };

    });