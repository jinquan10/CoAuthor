package com.nwm.coauthor.service.client;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.StringUtils;
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
		HttpComponentsClientHttpRequestFactory httpClient = new HttpComponentsClientHttpRequestFactory();
		httpClient.setReadTimeout(0);
		
		restTemplate.setMessageConverters(messageConverters);
		restTemplate.setRequestFactory(httpClient);
	}
	
	protected String urlResolver(String endpoint){
		return HOST + SERVICE + endpoint;
	}
	
	protected HttpEntity<Object> httpEntity(Object object, String coToken){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if(StringUtils.hasText(coToken)){
			headers.add("Authorization", coToken);
		}
		
		return new HttpEntity<Object>(object, headers);
	}
	
	protected ExceptionMapper convertToExceptionMapper(HttpStatusCodeException e){
		ExceptionMapper exceptionMapper = null;
		
		try {
			BaseException baseException = objectMapper.readValue(e.getResponseBodyAsString(), BaseException.class);
			exceptionMapper = baseException.getId();
			exceptionMapper.setBaseException(baseException); 
		} catch (Throwable t) {
			
		}
		
		return exceptionMapper;
	}
}
