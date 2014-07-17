var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$scope', '$routeParams', '$http', 'Story', function($scope, $routeParams, $http, Story) {
            $scope.loadNewStorySchema = function loadNewStorySchemaFn(){
                Story.getSchemaForCreate(function(res){
                	$scope.storySchemaForCreate = res;
                	$scope.storySchemaForCreateDisplay = getSchemaDisplay(res);
                });
            };
        }
]);