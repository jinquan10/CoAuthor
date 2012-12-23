package com.nwm.coauthor.service.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.util.Singletons;

@Component("authenticationFilter")
public class AuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	private AuthenticationManagerImpl authenticationManager;
	private static final String CO_TOKEN_HEADER = "Authorization";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String coToken = request.getHeader(CO_TOKEN_HEADER);
			
			if(StringUtils.hasText(coToken)){
				String userId = authenticationManager.authenticateCOToken(coToken);
				request = new CustomHttpHeadersRequest(request, userId);
			}
		} catch (AuthenticationUnauthorizedException e) {
			response.setStatus(e.getStatusCode());
			response.getWriter().write(Singletons.objectMapper.writeValueAsString(e));
			
			return;
		}
		
		filterChain.doFilter(request, response);
	}
	
	public class CustomHttpHeadersRequest extends HttpServletRequestWrapper{
		private String userId;
		
		public CustomHttpHeadersRequest(HttpServletRequest request, String userId) {
			super(request);
			
			this.userId = userId;
		}
		
		@Override
		public String getHeader(String name){
			if(name.equals(CO_TOKEN_HEADER)){
				return userId; 
			}
			
			return super.getHeader(name);
		}
		
		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> list = new ArrayList<String>();
			
			HttpServletRequest request = (HttpServletRequest)getRequest();
			Enumeration<String> e = request.getHeaderNames();
			while(e.hasMoreElements()) {
				//add the names of the request headers into the list
				String n = (String)e.nextElement();
				list.add(n);
			}
			
			list.add(CO_TOKEN_HEADER);
			
			Enumeration<String> en = Collections.enumeration(list);
			return en;
		}		
	}
}