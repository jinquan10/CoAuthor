var coAuthorControllers = angular.module('coAuthorControllers', []);

coAuthorControllers.controller('mainController', [
        '$interval', '$cookies', '$scope', '$routeParams', '$http', 'Schemas', 'Story', 'StoryOperation', 'EntryOperation', 'PraisesOperation', 'User', '$cookieStore', 
        function($interval, $cookies, $scope, $routeParams, $http, Schemas, Story, StoryOperation, EntryOperation, PraisesOperation, User, $cookieStore) {

            $scope.storyForCreateModel = {};
            $scope.entryRequestModel = {};
            $scope.nativeAuthModel = {};
            $scope.cookies = $cookies;

            $http.defaults.headers.common['TimeZoneOffsetMinutes'] = new Date().getTimezoneOffset();

            if ($cookies.coToken != null) {
                $http.defaults.headers.common['Authorization'] = $cookies.coToken;
            }

            $scope.storyFilter = null;
            $scope.modalContent = null;
            $scope.currStory = null;

            var cursorInterval = null;
            var cursorInited = false;

            getPraisesSchema();

            $scope.praisesSchema = null;
            $scope.praiseNumber = 3;

            $('#modal').on('hidden.bs.modal', function(e) {
                if ($scope.postAuthAction != null) {
                    $scope.postAuthAction();
                    $scope.postAuthAction = null;
                    $scope.$apply();
                }
                ;
            });

            function getPraisesSchema() {
                if ($scope.praisesSchema == null) {
                    Schemas.getPraises(function(res) {
                        $scope.praisesSchema = res;
                        getTopViewStories();
                    });
                }
            }

            $scope.incrementPraise = function(key) {
                PraisesOperation.increment({
                    id : $scope.currStory.id,
                    praise : key
                }, null, function(res) {
                    $scope.currStory = res;

                    var stories = $scope.stories;

                    for (var i = 0; i < stories.length; i++) {
                        if (stories[i].id == res.id) {
                            stories[i].praises = topNPraises($scope.praiseNumber, res);
                        }
                    }
                });
            }

            $scope.clickedTextArea = function() {
                resetPotentialEntriesState();

                $("#storyBody").animate({
                    scrollTop : $(window).scrollTop() + $(window).height() + 10000
                }, 0);
            }

            $scope.countDownPotentialEntries = function(isShowStory) {
                if (isShowStory) {
                    if ($scope.currStory.nextEntryAvailableAt != undefined) {
                        if (new Date().getTime() > $scope.currStory.nextEntryAvailableAt) {
                            $scope.pickEntryCounter = null;
                            $scope.pickEntry($scope.currStory.id);
                        }
                    }

                    $scope.modalContent = 'viewStory';
                }

                if ($scope.currStory.nextEntryAvailableAt != undefined) {
                    $scope.currStoryCountdown = countdown(null, $scope.currStory.nextEntryAvailableAt, countdown.MINUTES | countdown.SECONDS, 0).toString();

                    if ($scope.pickEntryCounter == null) {
                        $scope.pickEntryCounter = $interval(function() {
                            if (new Date().getTime() > $scope.currStory.nextEntryAvailableAt) {
                                $interval.cancel($scope.pickEntryCounter);
                                $scope.pickEntryCounter = null;
                                $scope.pickEntry($scope.currStory.id);
                            }

                            $scope.currStoryCountdown = countdown(null, $scope.currStory.nextEntryAvailableAt, countdown.MINUTES | countdown.SECONDS, 0).toString();
                        }, 1000);
                    }
                }
            }

            $scope.pickEntry = function(storyId) {
                StoryOperation.pickEntry({
                    id : storyId
                }, null, function(res) {
                    $scope.currStory = res;
                    $("#storyBody").animate({
                        scrollTop : $(window).scrollTop() + $(window).height() + 10000
                    }, 0);
                });
            }

            $scope.requestEntry = function() {
                var storyId = $scope.currStory.id;

                $("#nextEntry").text("");

                $("#submitEntryButton").prop("disabled", true);

                StoryOperation.requestEntry({
                    id : storyId
                }, $scope.entryRequestModel, function(res) {
                    $scope.entryRequestModel.entry = null;
                    $('#entryRequestTextArea').val("");
                    $scope.currStory = res;
                    $scope.countDownPotentialEntries();
                    setRequestEntryValidation();
                });
            };

            $scope.voteForEntry = function(storyID, entryID) {
                EntryOperation.vote({
                    storyId : storyID,
                    entryId : entryID
                }, null, function(res) {
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
                $("#modal").modal('show');

                StoryOperation.incrementViews({
                    id : storyId
                }, null);

                Story.getStory({
                    type : storyId
                }, function(res) {
                    $scope.currStory = res;
                    $scope.countDownPotentialEntries(true);
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
                        // $("#storyBody").css("max-height",
                        // window.screen.height - 600);
                        $("#storyBody").css("height", window.screen.height - 600);
                        bindCharsRemaining($scope.entryRequestSchema['entry'].maxLength, '#entryRequestCharsRemaining', '#entryRequestTextArea');
                        bindCharsRequired($scope.entryRequestSchema['entry'].minLength, '#entryRequestCharsRequired', '#entryRequestTextArea');
                    }
                });
            }
            ;

            $scope.initEntries = function() {
                var tooltipSpan = document.getElementById('tooltip-entry');

                window.onmousemove = function(e) {
                    if (!$scope.storyEntryHoveredFlag) {
                        return;
                    }

                    var x = (e.pageX) + 10 + 'px', y = (e.pageY) + 5 + 'px';

                    tooltipSpan.style.left = x;
                    tooltipSpan.style.top = y;
                };
            }

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

            $scope.showAuthModal = function showAuthModal(title, authedAction) {
                if ($cookies.coToken == null) {
                    $scope.postAuthAction = authedAction;
                    $scope.authTitle = title;
                    $scope.modalContent = 'modalLoading';

                    if ($scope.nativeAuthSchema == null) {
                        Schemas.authenticateNative(function(res) {
                            $scope.nativeAuthSchema = res;
                            $scope.nativeAuthSchemaForDisplay = getSchemaDisplay(res);

                            $scope.modalContent = 'authenticate';
                        });
                    } else {
                        $scope.modalContent = 'authenticate';
                    }

                    $("#modal").modal('show');

                    return;
                }

                if (authedAction != null) {
                    authedAction();
                }
                ;
            }

            $scope.showNewStoryModal = function loadNewStorySchemaFn() {
                $scope.showAuthModal('Signup or Login to create a new story', function() {
                    $scope.modalContent = 'modalLoading';
                    if ($scope.storySchemaForCreateDisplay == null) {
                        Schemas.getSchemaForCreate(function(res) {
                            $scope.storySchemaForCreate = res;
                            $scope.storySchemaForCreateDisplay = getSchemaDisplay(res);

                            $scope.modalContent = 'newStory';
                            $("#modal").modal('show');
                            setNewStoryValidation();
                        });
                    } else {
                        $scope.modalContent = 'newStory';
                        $("#modal").modal('show');
                        setNewStoryValidation();
                    }
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
                    for (var i = 0; i < res.length; i++) {
                        res[i].praises = topNPraises($scope.praiseNumber, res[i]);
                    }

                    $scope.stories = res;
                });
            }

            $scope.initStoryTextArea = function() {
                $("#submitEntryButton").prop("disabled", true);

                $('#entryRequestTextArea').keyup(function() {
                    $scope.submitStoryCallback();
                });
            }

            $scope.submitStoryCallback = function() {
                var text = $('#entryRequestTextArea').val();

                if ($scope.entryRequestSchemaDisplay == null) {
                    Schemas.getSchemaForEntryRequest(function(res) {
                        $scope.entryRequestSchema = res;
                        $scope.entryRequestSchemaDisplay = getSchemaDisplay(res);
                    });
                }

                if (text.length >= $scope.entryRequestSchema['entry'].minLength) {
                    $("#submitEntryButton").prop("disabled", false);
                } else {
                    $("#submitEntryButton").prop("disabled", true);
                }

                if (text.length > $scope.entryRequestSchema['entry'].maxLength) {
                    $('#entryRequestTextArea').val(text.substring(0, $scope.entryRequestSchema['entry'].maxLength));
                }

                $('#nextEntry').text(text);
                $scope.entryRequestModel['entry'] = text;

                $("#storyBody").animate({
                    scrollTop : $(window).scrollTop() + $(window).height() + 10000
                }, 0);
            }

            $scope.initCursor = function() {
                if (cursorInterval == null) {
                    cursorInterval = $interval(function() {
                        if ($("#nextEntryCursor").is(":visible")) {
                            $("#nextEntryCursor").hide();
                        } else {
                            $("#nextEntryCursor").show();
                        }
                    }, 500);
                }
            }

            $scope.ellipse = function(text, len, defaultTo) {
                if (text.length > len) {
                    return text.substring(0, len - 1) + "...";
                } else if (defaultTo) {
                    return text + "...";
                }

                return text;
            }

            var peekEntryId = null;

            $scope.peekPotentialEntry = function(peekEntry) {
                if (peekEntryId == null || peekEntryId != peekEntry.id) {
                    if ($('#nextEntry').hasClass("peek-entry")) {
                        $('#nextEntry').text(peekEntry.entry);
                    } else {
                        $('#nextEntry').text(peekEntry.entry).addClass("peek-entry");
                    }

                    peekEntryId = peekEntry.id;

                    $("#storyBody").animate({
                        scrollTop : $(window).scrollTop() + $(window).height() + 10000
                    }, 0);

                    $('.potential-entry-clicked').removeClass("potential-entry-clicked");
                    $('#nextEntry').removeClass("next-entry");
                    $('#potentialEntry' + peekEntryId).addClass("potential-entry-clicked");
                } else {
                    resetPotentialEntriesState();

                    $("#storyBody").animate({
                        scrollTop : $(window).scrollTop() + $(window).height() + 10000
                    }, 0);
                }
            }

            function resetPotentialEntriesState() {
                $('#nextEntry').addClass("next-entry");
                $('#nextEntry').removeClass("peek-entry");
                $('#nextEntry').text($('#entryRequestTextArea').val());
                $('.potential-entry-clicked').removeClass("potential-entry-clicked");

                peekEntryId = null;
            }

            $scope.initModal = function() {
                $('#modal').on('hide.bs.modal', function(e) {
                    $interval.cancel($scope.pickEntryCounter);
                    $scope.pickEntryCounter = null;
                });
            }

            function topNPraises(n, story) {
                var schema = $scope.praisesSchema;

                var praises = [];

                for ( var key in schema) {
                    if (key.indexOf("$") > -1) {
                        continue;
                    }

                    var obj = {};
                    obj['displayName'] = schema[key].displayName;
                    obj['count'] = story.praises[key].count;

                    praises.push(obj);
                }

                praises.sort(function(a, b) {
                    return b.count - a.count;
                });

                var topNPraises = [];

                for (var i = 0; i < n; i++) {
                    topNPraises.push(praises[i]);
                }

                return topNPraises;
            }

            $scope.storyEntryHovered = function(entry) {
                $scope.storyEntryHoveredFlag = true;

                $("#tooltip-entry").addClass("tooltip-entry-hovered");
                $("#tooltip-entry").text("Author: " + entry.createdByDisplayName);
            }

            $scope.storyEntryLeft = function(entry) {
                $scope.storyEntryHoveredFlag = false;

                $("#tooltip-entry").removeClass("tooltip-entry-hovered");
            }

            $scope.storyEntryClicked = function() {
                $scope.toolTipEntryClicked = true;
            }

            $scope.createNativeAccount = function() {
                User.createNative($scope.nativeAuthModel, function(res) {
                    $scope.nativeAuthModel = {};
                    $scope.assignCookies(res);
                    $('#modal').modal('hide');
                });
            }

            $scope.logIn = function() {
                User.logIn($scope.nativeAuthModel, function(res) {
                    $scope.nativeAuthModel = {};
                    $scope.assignCookies(res);
                    $('#modal').modal('hide');
                });
            }

            $scope.isLoggedIn = function() {
                if ($cookies.coToken != null) {
                    return true;
                }

                return false;
            }

            $scope.logout = function() {
                var wrapped = {};
                wrapped['coToken'] = $cookies.coToken;

                User.logout(wrapped, function(res) {
                    $scope.clearCookies();
                });
            }

            $scope.assignCookies = function(res) {
                $cookies.username = res.username;
                $cookies.coToken = res.coToken;
            }

            $scope.clearCookies = function() {
                $cookieStore.remove("username");
                $cookieStore.remove("coToken");
            }
        }
]);