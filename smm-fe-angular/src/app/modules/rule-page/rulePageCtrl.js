
'use strict';

angular
    .module('dashboard')
    .controller('rulePageCtrl', function ($scope, $rootScope, pageService,$stateParams,ruleService,initialRuleData,$mdDialog,$timeout) {
        console.log('inside dashboard controller');
        $scope.user = null;
        $scope.users = null;
        $scope.loadUsers = function() {
            // Use timeout to simulate a 650ms request.
            return $timeout(function() {
                $scope.users =  $scope.users  || [
                        { id: 1, name: 'Scooby Doo' },
                        { id: 2, name: 'Shaggy Rodgers' },
                        { id: 3, name: 'Fred Jones' },
                        { id: 4, name: 'Daphne Blake' },
                        { id: 5, name: 'Velma Dinkley' }
                    ];

            }, 650);
        };
        $scope.rules = initialRuleData;
        $scope.pageId = $stateParams.pageId;

        $scope.addRule = function(event){
            console.log($scope.ruleName);
            $mdDialog.show({
                controller: function ($timeout, $q, $scope, $mdDialog) {
                    $scope.cancel = function(event) {
                        $mdDialog.cancel();
                    };
                    $scope.finish = function(event) {
                        $mdDialog.hide();
                    };
                    $scope.answer = function(answer) {
                        //pass quest to hide function.
                        $mdDialog.hide(answer);
                    };
                    $scope.loadUsers = function() {
                        // Use timeout to simulate a 650ms request.
                        return $timeout(function() {
                            $scope.users =  $scope.users  || [
                                    { id: 1, name: 'Scooby Doo' },
                                    { id: 2, name: 'Shaggy Rodgers' },
                                    { id: 3, name: 'Fred Jones' },
                                    { id: 4, name: 'Daphne Blake' },
                                    { id: 5, name: 'Velma Dinkley' }
                                ];

                        }, 650);
                    };
                },
                templateUrl: 'add-rule.html',
                parent: angular.element(document.body),
                bindToController: true,
                controllerAs: 'modal',
                targetEvent: event,
                clickOutsideToClose:true,
                fullscreen: $scope.customFullscreen,
                locals: {
                    users: $scope.users
                }
            }).then(function(answer){
                var request = {
                    pageId : $scope.pageId,
                    ruleName: answer.ruleName,
                    ruleWords: answer.ruleWords
                };
                console.log(request);
                ruleService.addRule(request)
                    .then(function(response){
                        console.log(response.data)
                        console.log("add rule success");
                        $scope.rules.push(response.data);
                    })
            })
        }

        $scope.showDelete = function(event,answer){
            console.log(answer.ruleId);
            var confirm = $mdDialog.confirm()
                .title('Do you sure to delete this rule? ')
                .textContent('You can not undo it')
                .targetEvent(event)
                .ok('Delete')
                .cancel('Cancel');
            $mdDialog.show(confirm).then(function(){
                ruleService.deleteRule(answer)
                    .then(function(){
                        console.log('delete success');
                        $scope.rules.splice($scope.rules.indexOf(answer), 1 );
                    },function(err){
                        console.log(err);
                    })
            });
        }

    });