var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$scope', '$routeParams', '$http', 'Schemas', function($scope, $routeParams, $http, Schemas) {

            $scope.storyForCreateModel = {};

            $scope.loadNewStorySchema = function loadNewStorySchemaFn() {
                Schemas.getSchemaForCreate(function(res) {
                    $scope.storySchemaForCreate = res;
                    $scope.storySchemaForCreateDisplay = getSchemaDisplay(res);

                    bindCharsRemaining($scope.storySchemaForCreate['entry'].maxLength, '#newStoryCharsRemaining', '#newStoryTextarea');
                });
            };
        }
]);