package com.nwm.coauthor.service.manager;

import java.util.UUID;

import com.nwm.coauthor.exception.FBTokenInvalidException;
import com.nwm.coauthor.service.client.FacebookClientImpl;
import com.nwm.coauthor.service.dao.AuthenticationDAOImpl;
import com.nwm.coauthor.service.model.FBUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerImpl {
	@Autowired private AuthenticationDAOImpl authenticationDAO;
	@Autowired private FacebookClientImpl fbClient;
	
//	public String authenticateCOToken(String coToken){
//		return authenticationDAO.authenticateCOToken(coToken);
//	}

	public String authenticateFB(String fbToken) throws FBTokenInvalidException {
		FBUser fbUser = fbClient.validate(fbToken);
		String coToken = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString(); 
		
		authenticationDAO.login(coToken, fbUser.getId());
		
		return coToken; 
	}
}
