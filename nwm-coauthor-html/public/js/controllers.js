var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$cookies', '$scope', '$routeParams', '$http', 'Schemas', 'Story', function($cookies, $scope, $routeParams, $http, Schemas, Story) {

            $scope.storyForCreateModel = {};
            $scope.loggedIn = ($cookies.Authorization != undefined);

            $http.defaults.headers.common['TimeZoneOffsetMinutes'] = new Date().getTimezoneOffset();
            $http.defaults.headers.common['Authorization'] = $cookies.Authorization;

            $scope.storyFilter = null;
            $scope.modalContent = null;
            $scope.currStory = null;
            
            // - put this in the main.html somewhere
            getTopViewStories();

            $scope.initTooltips = function(){
                $('.tooltips').tooltip();
            }
            
            $scope.showGetStoryModal = function(storyId) {
                $scope.modalContent = 'modalLoading';
                $("#modal").modal();
                
                Story.getStory({type: storyId}, function(res) {
                    $scope.currStory = res;
                    $scope.modalContent = 'viewStory';
                });
            };
            
            $scope.selectedStoryFilter = function(v) {
                $scope.storyFilter = v;
            };

            $scope.showNewStoryModal = function loadNewStorySchemaFn() {
                $scope.modalContent = 'modalLoading';
                $("#modal").modal();
                
                Schemas.getSchemaForCreate(function(res) {
                    $scope.storySchemaForCreate = res;
                    $scope.storySchemaForCreateDisplay = getSchemaDisplay(res);
                    
                    $scope.modalContent = 'newStory';
                    
                    $scope.$watch('modalContent', function(newVal, oldValue){
                        if (newVal === 'newStory') {
                            bindCharsRemaining($scope.storySchemaForCreate['entry'].maxLength, '#newStoryCharsRemaining', '#newStoryTextarea');
                            bindCharsRequired($scope.storySchemaForCreate['entry'].minLength, '#newStoryCharsRequired', '#newStoryTextarea');
                        }
                    });
                });
            };

            $scope.createStory = function createStoryFn() {
                Story.create($scope.storyForCreateModel, function(res) {
                    $('#modal').modal('hide');
                    $scope.storyForCreateModel = {};
                    getTopViewStories();
                });
            }
            
            function getTopViewStories() {
                Story.getTopViewStories(function(res) {
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