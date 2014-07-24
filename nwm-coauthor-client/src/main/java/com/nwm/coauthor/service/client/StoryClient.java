package com.nwm.coauthor.service.client;


public class StoryClient extends BaseClient /*implements StoryController*/ {
//    private static final String CREATE_STORY_ENDPOINT = "/story";
//    private static final String GET_MY_STORIES_ENDPOINT = "/story/mine";
//    private static final String GET_ENTRIES_ENDPOINT = "/story/%s/entries/%s/clientCharVersion/%s";
//    private static final String NEW_ENTRY_ENDPOINT = "/story/%s/entry";
//    private static final String GET_MY_STORY_ENDPOINT = "/story/%s/mine";
//    private static final String LIKE_ENDPOINT = "/story/%s/like";
//    private static final String PUBLISH_ENDPOINT = "/story/%s/publish";
//    private static final String CHANGE_TITLE_ENDPOINT = "/story/%s/title";
//    private static final String NEW_FRIENDS_ENDPOINT = "/story/%s/friends";
//    private static final String RATE_STORY_ENDPOINT = "/story/%s/rate/%s";
//
//    // private static final String COMMENT_ENDPOINT = "/comment";
//
//    private StoryClient() {
//    }
//
//    private static final StoryClient STORY_CLIENT = new StoryClient();
//
//    public static StoryClient instance() {
//        return STORY_CLIENT;
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> createStory(String coToken, NewStory createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
//        try {
//            return doExchange(CREATE_STORY_ENDPOINT, HttpMethod.POST, httpEntity(createStoryRequest, coToken), StoryResponse.class);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == SomethingWentWrongException.class) {
//                throw new SomethingWentWrongException();
//            } else if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else {
//                throw new AuthenticationUnauthorizedException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoriesResponse> getMyStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException {
//        try {
//            return doExchange(GET_MY_STORIES_ENDPOINT, HttpMethod.GET, httpEntity(null, coToken), StoriesResponse.class);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<EntriesResponse> getEntries(String coToken, String storyId, Integer beginIndex, Integer clientCharVersion) throws BadRequestException, AuthenticationUnauthorizedException,
//            CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException, SomethingWentWrongException {
//        try {
//            return doExchange(GET_ENTRIES_ENDPOINT, HttpMethod.GET, httpEntity(null, coToken), EntriesResponse.class, storyId, beginIndex, clientCharVersion);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else if (emw.getClazz() == CannotGetEntriesException.class) {
//                throw new CannotGetEntriesException();
//            } else if (emw.getClazz() == MoreEntriesLeftException.class) {
//                throw new MoreEntriesLeftException(emw.getBaseException());
//            } else if (emw.getClazz() == VersioningException.class) {
//                throw new VersioningException();
//            } else if (emw.getClazz() == StoryNotFoundException.class) {
//                throw new StoryNotFoundException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> newEntry(String coToken, String storyId, NewEntryRequest newEntryRequest) throws BadRequestException, AuthenticationUnauthorizedException,
//            VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException, SomethingWentWrongException {
//        try {
//            return doExchange(NEW_ENTRY_ENDPOINT, HttpMethod.POST, httpEntity(newEntryRequest, coToken), StoryResponse.class, storyId);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else if (emw.getClazz() == VersioningException.class) {
//                throw new VersioningException();
//            } else if (emw.getClazz() == NonMemberException.class) {
//                throw new NonMemberException();
//            } else if (emw.getClazz() == ConsecutiveEntryBySameMemberException.class) {
//                throw new ConsecutiveEntryBySameMemberException();
//            } else if (emw.getClazz() == StoryNotFoundException.class) {
//                throw new StoryNotFoundException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> getMyStory(String coToken, String storyId) throws AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException,
//            SomethingWentWrongException, NonMemberException {
//        try {
//            return doExchange(GET_MY_STORY_ENDPOINT, HttpMethod.GET, httpEntity(null, coToken), StoryResponse.class, storyId);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else if (emw.getClazz() == NonMemberException.class) {
//                throw new NonMemberException();
//            } else if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else if (emw.getClazz() == StoryNotFoundException.class) {
//                throw new StoryNotFoundException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> likeStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException, StoryNotFoundException,
//            SomethingWentWrongException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
//        try {
//            return doExchange(LIKE_ENDPOINT, HttpMethod.POST, httpEntity(null, coToken), StoryResponse.class, storyId);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else if (emw.getClazz() == UserLikingOwnStoryException.class) {
//                throw new UserLikingOwnStoryException();
//            } else if (emw.getClazz() == AlreadyLikedException.class) {
//                throw new AlreadyLikedException();
//            } else if (emw.getClazz() == StoryNotFoundException.class) {
//                throw new StoryNotFoundException();
//            } else if (emw.getClazz() == UnpublishedStoryLikedException.class) {
//                throw new UnpublishedStoryLikedException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> publishStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException,
//            UserIsNotLeaderException, NoTitleForPublishingException, SomethingWentWrongException {
//        try {
//            return doExchange(PUBLISH_ENDPOINT, HttpMethod.POST, httpEntity(null, coToken), StoryResponse.class, storyId);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else if (emw.getClazz() == UserIsNotLeaderException.class) {
//                throw new UserIsNotLeaderException();
//            } else if (emw.getClazz() == NoTitleForPublishingException.class) {
//                throw new NoTitleForPublishingException();
//            } else if (emw.getClazz() == StoryNotFoundException.class) {
//                throw new StoryNotFoundException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> changeTitle(String coToken, String storyId, ChangeTitleRequest request) throws SomethingWentWrongException, AuthenticationUnauthorizedException,
//            BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
//        try {
//            return doExchange(CHANGE_TITLE_ENDPOINT, HttpMethod.PUT, httpEntity(request, coToken), StoryResponse.class, storyId);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else if (emw.getClazz() == UserIsNotLeaderException.class) {
//                throw new UserIsNotLeaderException();
//            } else if (emw.getClazz() == StoryNotFoundException.class) {
//                throw new StoryNotFoundException();
//            } else if (emw.getClazz() == AlreadyPublishedException.class) {
//                throw new AlreadyPublishedException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> newFriends(String coToken, String storyId, NewFriendsRequest request) throws SomethingWentWrongException, BadRequestException,
//            AuthenticationUnauthorizedException, StoryNotFoundException, AlreadyAMemberException, NonMemberException {
//        try {
//            return doExchange(NEW_FRIENDS_ENDPOINT, HttpMethod.POST, httpEntity(request, coToken), StoryResponse.class, storyId);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else if (emw.getClazz() == AuthenticationUnauthorizedException.class) {
//                throw new AuthenticationUnauthorizedException();
//            } else if (emw.getClazz() == StoryNotFoundException.class) {
//                throw new StoryNotFoundException();
//            } else if (emw.getClazz() == AlreadyAMemberException.class) {
//                throw new AlreadyAMemberException();
//            } else if (emw.getClazz() == NonMemberException.class) {
//                throw new NonMemberException();
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }
//
//    @Override
//    public ResponseEntity<StoryResponse> rateStory(String coToken, String storyId, Integer rating) throws SomethingWentWrongException, BadRequestException{
//        try {
//            return doExchange(RATE_STORY_ENDPOINT, HttpMethod.POST, httpEntity(null, coToken), StoryResponse.class, storyId, rating);
//        } catch (HttpException e) {
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
//
//            if (emw.getClazz() == BadRequestException.class) {
//                throw new BadRequestException(emw.getBaseException());
//            } else {
//                throw new SomethingWentWrongException();
//            }
//        }
//    }    
//    
//    //
//    // @Override
//    // public void comment(String coToken, String storyId, CommentRequest
//    // request){
//    // restTemplate.exchange(urlStoryResolver("/" + storyId + COMMENT_ENDPOINT),
//    // HttpMethod.POST, httpEntity(request, coToken), String.class);
//    // }

}
