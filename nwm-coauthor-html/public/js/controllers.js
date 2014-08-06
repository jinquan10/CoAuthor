var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$interval', '$cookies', '$scope', '$routeParams', '$http', 'Schemas', 'Story', 'StoryOperation', 'EntryOperation', function($interval, $cookies, $scope, $routeParams, $http, Schemas, Story, StoryOperation, EntryOperation) {

            $scope.storyForCreateModel = {};
            $scope.entryRequestModel = {};

            $scope.loggedIn = ($cookies.Authorization != undefined);

            $http.defaults.headers.common['TimeZoneOffsetMinutes'] = new Date().getTimezoneOffset();
            $http.defaults.headers.common['Authorization'] = $cookies.Authorization;

            $scope.storyFilter = null;
            $scope.modalContent = null;
            $scope.currStory = null;

            // - put this in the main.html somewhere
            getTopViewStories();

            $scope.requestEntry = function() {
                var storyId = $scope.currStory.id;

                StoryOperation.requestEntry({
                    id : storyId
                }, $scope.entryRequestModel, function(res) {
                    Story.getStory({
                        type : storyId
                    }, function(res) {
                        $scope.currStory = res;
                        $(".wrapper").dotdotdot();
                    });
                });
            };

            $scope.voteForEntry = function(storyID, entryID) {
                EntryOperation.vote({storyId: storyID, entryId: entryID}, null, function(res){
                    Story.getStory({
                        type : storyID
                    }, function(res) {
                        $scope.currStory = res;
                    });
                });
            }
            
            $scope.showGetStoryModal = function(storyId, index) {
                $scope.currStoryIndex = index;

                $scope.modalContent = 'modalLoading';
                $("#modal").modal();

                StoryOperation.incrementViews({
                    id : storyId
                }, null);

                Story.getStory({
                    type : storyId
                }, function(res) {
                    $scope.currStory = res;
                    $scope.modalContent = 'viewStory';

                    $scope.currStoryCountdown = countdown(null, $scope.currStory.nextEntryAvailableAt, countdown.MINUTES|countdown.SECONDS, 0).toString();
                    
                    $interval(function() {
                        $scope.currStoryCountdown = countdown(null, $scope.currStory.nextEntryAvailableAt, countdown.MINUTES|countdown.SECONDS, 0).toString();
                    }, 1000);
                    
                    $("#test").dotdotdot({});
                });

                if ($scope.entryRequestSchemaDisplay == null) {
                    Schemas.getSchemaForEntryRequest(function(res) {
                        $scope.entryRequestSchema = res;
                        $scope.entryRequestSchemaDisplay = getSchemaDisplay(res);

                        setRequestEntryValidation();
                    });
                } else {
                    setRequestEntryValidation();
                }
            };

            function setRequestEntryValidation() {
                $scope.$watch('modalContent', function(newVal, oldValue) {
                    if (newVal === 'viewStory') {
                        bindCharsRemaining($scope.entryRequestSchema['entry'].maxLength, '#entryRequestCharsRemaining', '#entryRequestTextArea');
                        bindCharsRequired($scope.entryRequestSchema['entry'].minLength, '#entryRequestCharsRequired', '#entryRequestTextArea');
                    }
                });
            }
            ;

            function setNewStoryValidation() {
                $scope.$watch('modalContent', function(newVal, oldValue) {
                    if (newVal === 'newStory') {
                        bindCharsRemaining($scope.storySchemaForCreate['entry'].maxLength, '#newStoryCharsRemaining', '#newStoryTextarea');
                        bindCharsRequired($scope.storySchemaForCreate['entry'].minLength, '#newStoryCharsRequired', '#newStoryTextarea');
                    }
                });
            }
            ;

            $scope.selectedStoryFilter = function(v) {
                $scope.storyFilter = v;
            };

            $scope.showNewStoryModal = function loadNewStorySchemaFn() {
                $scope.modalContent = 'modalLoading';
                $("#modal").modal();

                if ($scope.storySchemaForCreateDisplay == null) {
                    Schemas.getSchemaForCreate(function(res) {
                        $scope.storySchemaForCreate = res;
                        $scope.storySchemaForCreateDisplay = getSchemaDisplay(res);

                        $scope.modalContent = 'newStory';
                        setNewStoryValidation();
                    });
                } else {
                    $scope.modalContent = 'newStory';
                    setNewStoryValidation();
                }
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