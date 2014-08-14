//package com.nwm.coauthor.service.controller;
//
//import org.springframework.http.ResponseEntity;
//
//import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
//import com.nwm.coauthor.exception.SomethingWentWrongException;
//import com.nwm.coauthor.service.resource.request.AuthenticateFBRequest;
//import com.nwm.coauthor.service.resource.response.AuthenticationResponse;
//
//// - DONE: figure out how to integrate tomcat7 into maven build cycle
////		- bug in current maven tomcat plugin 2.0, can't use package phase and above executed from parent pom
//// - DONE: configure long standing service instance
//// - DONE: create maven archetype
//// - DONE: find out how to use the MongoDB Datasource
//// - TODO: add authFilter
//// - TODO: embedded mongodb!
//// - TODO: auto documentation
//
//public interface AuthenticationController {
//	public ResponseEntity<AuthenticationResponse> authenticateFB(AuthenticateFBRequest authResource) throws SomethingWentWrongException, AuthenticationUnauthorizedException;
//}
