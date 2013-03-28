package com.nwm.coauthor.service.client;

import static com.nwm.coauthor.service.client.EnvProps.LOCAL_HOST;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.HttpException;
import com.nwm.coauthor.exception.WebApplicationException;
import com.nwm.coauthor.exception.mapping.ExceptionMapperWrapper;

public abstract class BaseClient {
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
	
	protected String urlStoryResolver(String endpoint, Object[] params){
	    return LOCAL_HOST.getHostAndServicePath() + String.format(endpoint, params);
	}
	
	protected HttpEntity<Object> httpEntity(Object object, String coToken){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		headers.add("Authorization", coToken);
		
		return new HttpEntity<Object>(object, headers);
	}
	
	protected ExceptionMapperWrapper convertToExceptionMapper(HttpStatusCodeException e){
		BaseException baseException = null;
		try {
			baseException = objectMapper.readValue(e.getResponseBodyAsString(), BaseException.class);
		} catch (Throwable t) {
			
		}
		
		if(baseException == null){
			throw new WebApplicationException();
		}
		
		return new ExceptionMapperWrapper(baseException.getId(), baseException);
	}
	
	protected <T, E> ResponseEntity<E> doExchange(String endpoint, HttpMethod httpMethod, HttpEntity<T> entity, Class<E> responseClass, Object ... params) throws HttpException{
	    try{
	        return restTemplate.exchange(urlStoryResolver(endpoint, params), httpMethod, entity, responseClass);
	    }catch(HttpStatusCodeException e){
	        throw new HttpException(e);
	    }
	}
}
