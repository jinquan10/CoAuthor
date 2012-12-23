package com.nwm.coauthor.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;

@Controller
@RequestMapping(value = "/story", produces = "application/json", consumes = "application/json")
public class StoryControllerImpl extends BaseControllerImpl implements StoryController{
	@Autowired
	private AuthenticationManagerImpl authenticationManager = null;
	
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createStory(@RequestHeader("Authorization") String userId) {
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}
