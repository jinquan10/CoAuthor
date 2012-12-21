package com.nwm.coauthor.service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.nwm.coauthor.exception.FBTokenInvalidException;
import com.nwm.coauthor.service.model.FBUser;

@Component
public class FacebookClientImpl {
	public FBUser validate(String fbToken) throws FBTokenInvalidException{
		String fbValidationUrl = "https://graph.facebook.com/me?access_token=" + fbToken;
		
		RestTemplate restTemplate = new RestTemplate();
		
		FBUser fbUser = null;
		try{
			fbUser = restTemplate.getForObject(fbValidationUrl, FBUser.class);
		}catch(Throwable e){
			throw new FBTokenInvalidException();
		}finally{
			if(fbUser == null){
				throw new FBTokenInvalidException(); 
			}
		}
		
		return fbUser;
	}
}
