package com.nwm.coauthor.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.HttpException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.mapping.ExceptionMapperWrapper;
import com.nwm.coauthor.service.controller.AuthenticationController;
import com.nwm.coauthor.service.resource.request.AuthenticateFBRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;

public class AuthenticationClient extends BaseClient implements AuthenticationController{
	private static final String AUTHENTICATE_ENDPOINT = "/authenticate";
	
	@Override
	public ResponseEntity<AuthenticationResponse> authenticateFB(AuthenticateFBRequest authResource) throws SomethingWentWrongException, AuthenticationUnauthorizedException {
		try{
		    return doExchange(AUTHENTICATE_ENDPOINT, HttpMethod.POST, httpEntity(authResource, null), AuthenticationResponse.class);
		} catch (HttpException e) {
		    ExceptionMapperWrapper emw = convertToExceptionMapper(e.getHttpStatusCodeException());
            
            if(emw.getClazz() == SomethingWentWrongException.class){
                throw new SomethingWentWrongException();
            }else{
                throw new AuthenticationUnauthorizedException();
            }
        }
	}
}
