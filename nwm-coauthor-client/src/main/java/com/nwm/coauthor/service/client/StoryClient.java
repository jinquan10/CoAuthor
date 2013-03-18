package com.nwm.coauthor.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.mapping.ExceptionMapperWrapper;
import com.nwm.coauthor.service.controller.StoryController;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.NewStoryResponse;

public class StoryClient extends BaseClient implements StoryController{
	private static final String CREATE_STORY_ENDPOINT = "/";
	private static final String GET_PRIVATE_STORIES_ENDPOINT = "/privates";
	private static final String ADD_ENTRY_ENDPOINT = "/entry";
	private static final String GET_PRIVATE_STORY_ENDPOINT = "/private";
	private static final String LIKE_ENDPOINT = "/private/like";
	private static final String PUBLISH_ENDPOINT = "/publish";
	private static final String CHANGE_TITLE_ENDPOINT = "/title";
	private static final String COMMENT_ENDPOINT = "/comment";
	
	public StoryClient(){
		
	}
	
	@Override
	public ResponseEntity<NewStoryResponse> createStory(String coToken, NewStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException {
		try{
			return restTemplate.exchange(urlStoryResolver(CREATE_STORY_ENDPOINT), HttpMethod.POST, httpEntity(createStoryRequest, coToken), NewStoryResponse.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == SomethingWentWrongException.class){
				throw new SomethingWentWrongException();
			}else if(emw.getClazz() == BadRequestException.class){
				throw new BadRequestException(emw.getBaseException());
			}else{
				throw new AuthenticationUnauthorizedException();
			}
		}
	}

//	@Override
//	public ResponseEntity<PrivateStoriesResponseWrapper> getPrivateStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException, StoryNotFoundException{
//		try{
//			return restTemplate.exchange(urlStoryResolver(GET_PRIVATE_STORIES_ENDPOINT), HttpMethod.GET, httpEntity(null, coToken), PrivateStoriesResponseWrapper.class);
//		}catch(HttpStatusCodeException e){
//			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
//			
//			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
//				throw new AuthenticationUnauthorizedException();
//			}else if(emw.getClazz() == StoryNotFoundException.class){
//				throw new StoryNotFoundException();
//			}else{
//				throw new SomethingWentWrongException();
//			}
//		}
//	}
//
//	@Override
//	public ResponseEntity<AddEntryResponse> addEntry(String coToken, EntryRequest entry) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException, StoryNotFoundException, AddEntryVersionException {
//		try{
//			return restTemplate.exchange(urlStoryResolver(ADD_ENTRY_ENDPOINT), HttpMethod.POST, httpEntity(entry, coToken), AddEntryResponse.class);
//		}catch(HttpStatusCodeException e){
//			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
//			
//			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
//				throw new AuthenticationUnauthorizedException();
//			} else if(emw.getClazz() == BadRequestException.class){
//				throw new BadRequestException(emw.getBaseException());
//			} else if(emw.getClazz() == AddEntryException.class){
//				throw new AddEntryException();
//			} else if(emw.getClazz() == StoryNotFoundException.class){
//				throw new StoryNotFoundException();
//			} else if(emw.getClazz() == AddEntryVersionException.class){
//				throw new AddEntryVersionException();
//			} else{
//				throw new SomethingWentWrongException();
//			}
//		}
//	}
//
//	@Override
//	public ResponseEntity<PrivateStoryResponse> getStoryForEdit(String coToken, String storyId) throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, UnauthorizedException{
//		try{
//			return restTemplate.exchange(urlStoryResolver("/" + storyId + GET_PRIVATE_STORY_ENDPOINT), HttpMethod.GET, httpEntity(null, coToken), PrivateStoryResponse.class);
//		}catch(HttpStatusCodeException e){
//			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
//			
//			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
//				throw new AuthenticationUnauthorizedException();
//			} else if(emw.getClazz() == UnauthorizedException.class){
//				throw new UnauthorizedException();
//			} else if(emw.getClazz() == BadRequestException.class){
//				throw new BadRequestException(emw.getBaseException());
//			} else if(emw.getClazz() == StoryNotFoundException.class){
//				throw new StoryNotFoundException();
//			} else{
//				throw new SomethingWentWrongException();
//			}
//		}
//	}
//
//	@Override
//	public void likeStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException, StoryNotFoundException, SomethingWentWrongException, UserLikingOwnStoryException, UnpublishedStoryLikedException{
//		try{
//			restTemplate.exchange(urlStoryResolver("/" + storyId + LIKE_ENDPOINT), HttpMethod.POST, httpEntity(null, coToken), String.class);
//		}catch(HttpStatusCodeException e){
//			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
//			
//			if(emw.getClazz() == AuthenticationUnauthorizedException.class){
//				throw new AuthenticationUnauthorizedException();
//			} else if(emw.getClazz() == BadRequestException.class){
//				throw new BadRequestException(emw.getBaseException());
//			} else if(emw.getClazz() == UserLikingOwnStoryException.class){
//				throw new UserLikingOwnStoryException();
//			} else if(emw.getClazz() == AlreadyLikedException.class){
//				throw new AlreadyLikedException();
//			} else if(emw.getClazz() == StoryNotFoundException.class){
//				throw new StoryNotFoundException();
//			} else if(emw.getClazz() == UnpublishedStoryLikedException.class){
//			    throw new UnpublishedStoryLikedException();
//			} else{
//				throw new SomethingWentWrongException();
//			}			
//		}
//	}
//
//	@Override
//	public void publishStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, SomethingWentWrongException{
//		try{
//		    restTemplate.exchange(urlStoryResolver("/" + storyId + PUBLISH_ENDPOINT), HttpMethod.POST, httpEntity(null, coToken), String.class);
//		}catch(HttpStatusCodeException e){
//		    ExceptionMapperWrapper emw = convertToExceptionMapper(e);
//		    
//		    if(emw.getClazz() == AuthenticationUnauthorizedException.class){
//                throw new AuthenticationUnauthorizedException();
//            } else if(emw.getClazz() == BadRequestException.class){
//                throw new BadRequestException(emw.getBaseException());
//            } else if(emw.getClazz() == UserIsNotLeaderException.class){
//                throw new UserIsNotLeaderException();
//            } else if(emw.getClazz() == NoTitleForPublishingException.class){
//                throw new NoTitleForPublishingException();
//            } else if(emw.getClazz() == StoryNotFoundException.class){
//                throw new StoryNotFoundException();
//            } else{
//                throw new SomethingWentWrongException();
//            }           
//		}
//	}
//	
//	@Override
//	public void changeStoryTitle(String coToken, String storyId, ChangeTitleRequest request) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException{
//	    try{
//	        restTemplate.exchange(urlStoryResolver("/" + storyId + CHANGE_TITLE_ENDPOINT), HttpMethod.PUT, httpEntity(request, coToken), String.class);
//	    }catch(HttpStatusCodeException e){
//            ExceptionMapperWrapper emw = convertToExceptionMapper(e);
//            
//            if(emw.getClazz() == AuthenticationUnauthorizedException.class){
//                throw new AuthenticationUnauthorizedException();
//            } else if(emw.getClazz() == BadRequestException.class){
//                throw new BadRequestException(emw.getBaseException());
//            } else if(emw.getClazz() == UserIsNotLeaderException.class){
//                throw new UserIsNotLeaderException();
//            } else if(emw.getClazz() == StoryNotFoundException.class){
//                throw new StoryNotFoundException();
//            } else if(emw.getClazz() == AlreadyPublishedException.class){
//                throw new AlreadyPublishedException();
//            } else{
//                throw new SomethingWentWrongException();
//            }           
//        }
//	}
//	
//	@Override
//	public void comment(String coToken, String storyId, CommentRequest request){
//		restTemplate.exchange(urlStoryResolver("/" + storyId + COMMENT_ENDPOINT), HttpMethod.POST, httpEntity(request, coToken), String.class);
//	}
}
