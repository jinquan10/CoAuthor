package com.nwm.coauthor.service.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class BaseClient {
	protected static final String HOST = "http://localhost:8081";
	protected static final String SERVICE = "/nwm-coauthor-webapp";
	protected RestTemplate restTemplate = new RestTemplate();
	
	public BaseClient(){
		initRestTemplate();
	}
	
	protected void initRestTemplate(){
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
	}
	
	protected String urlResolver(String endpoint){
		return HOST + SERVICE + endpoint;
	}
	
	protected HttpEntity<Object> httpEntity(Object object){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new HttpEntity<Object>(object, headers);
	}
}
