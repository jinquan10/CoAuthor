var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$cookies', '$scope', '$routeParams', '$http', 'Schemas', 'Story', function($cookies, $scope, $routeParams, $http, Schemas, Story) {

            $scope.storyForCreateModel = {};
            $scope.loggedIn = ($cookies.Authorization != undefined);
            
            $http.defaults.headers.common['TimeZoneOffsetMinutes'] = new Date().getTimezoneOffset();
            $http.defaults.headers.common['Authorization'] = $cookies.Authorization;

            $scope.storyFilter = null;
            
            getTopViewStories();
            
            $scope.selectedStoryFilter = function(filter) {
                $scope.storyFilter = filter;
            }
            
            $scope.loadNewStorySchema = function loadNewStorySchemaFn() {
                Schemas.getSchemaForCreate(function(res) {
                    $scope.storySchemaForCreate = res;
                    $scope.storySchemaForCreateDisplay = getSchemaDisplay(res);

                    bindCharsRemaining($scope.storySchemaForCreate['entry'].maxLength, '#newStoryCharsRemaining', '#newStoryTextarea');
                });
            };
            
            $scope.createStory = function createStoryFn() {
                Story.create($scope.storyForCreateModel, function(res){
                    $('#newStoryModal').modal('hide');
                    $scope.storyForCreateModel = {};
                    getTopViewStories();
                });
            }
            
            function getTopViewStories() {
                Story.getTopViewStories(function(res){
                    $scope.stories = res;
                });
            }
            
            $scope.getPublicStoryClass = function() {
                if ($scope.loggedIn) {
                    return "col-md-6";
                } else {
                    return "col-md-12";
                }
            }
        }
]);