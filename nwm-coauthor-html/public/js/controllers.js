var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$scope', '$routeParams', '$http', 'Story', function($scope, $routeParams, $http, Story) {
            $scope.loadNewStorySchema = function loadNewStorySchemaFn(){
                Story.schemaNew();
            };
        }
]);