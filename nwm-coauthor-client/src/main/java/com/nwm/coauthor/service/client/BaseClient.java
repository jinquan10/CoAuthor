package com.nwm.coauthor.service.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class BaseClient {
	protected static final String HOST = "http://localhost:8081";
	protected static final String SERVICE = "/nwm-coauthor-webapp";
	protected RestTemplate restTemplate = new RestTemplate();
	protected ObjectMapper objectMapper = new ObjectMapper();
	
	public BaseClient(){
		initRestTemplate();
	}
	
	protected void initRestTemplate(){
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	}
	
	protected String urlResolver(String endpoint){
		return HOST + SERVICE + endpoint;
	}
	
	protected HttpEntity<Object> httpEntity(Object object){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new HttpEntity<Object>(object, headers);
	}
	
	protected static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	protected ExceptionMapper convertToExceptionMapper(HttpStatusCodeException e){
		ExceptionMapper exceptionMapper = null;
		
		try {
			exceptionMapper = objectMapper.readValue(e.getResponseBodyAsString(), BaseException.class).getId();
		} catch (Throwable t) {
			
		}
		
		return exceptionMapper;
	}
}
