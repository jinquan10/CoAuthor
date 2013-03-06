package com.nwm.coauthor.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.mapping.ExceptionMapper;
import com.nwm.coauthor.exception.mapping.ExceptionMapperWrapper;
import com.nwm.coauthor.service.controller.AuthenticationController;
import com.nwm.coauthor.service.resource.request.AuthenticateFBRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;

public class AuthenticationClient extends BaseClient implements AuthenticationController{
	private static final String AUTHENTICATE_ENDPOINT = "/authenticate";
	
	@Override
	public ResponseEntity<AuthenticationResponse> authenticateFB(AuthenticateFBRequest authResource) throws SomethingWentWrongException, AuthenticationUnauthorizedException {
		ResponseEntity<AuthenticationResponse> response = null;
		
		try{
			response = restTemplate.exchange(urlStoryResolver(AUTHENTICATE_ENDPOINT), HttpMethod.POST, httpEntity(authResource, null), AuthenticationResponse.class);
		}catch(HttpStatusCodeException e){
			ExceptionMapperWrapper emw = convertToExceptionMapper(e);
			
			if(emw.getClazz() == SomethingWentWrongException.class){
				throw new SomethingWentWrongException();
			}else{
				throw new AuthenticationUnauthorizedException();
			}
		}
		
		return response; 
	}
}
