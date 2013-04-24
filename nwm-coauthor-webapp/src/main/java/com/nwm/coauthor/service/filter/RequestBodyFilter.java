package com.nwm.coauthor.service.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nwm.coauthor.util.Singletons;

@Component("requestBodyFilter")
public class RequestBodyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getContentLength() == 0) {
            request = new RequestWrapper(request);
        }

        filterChain.doFilter(request, response);
    }

    public class RequestWrapper extends HttpServletRequestWrapper {

        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public ServletInputStream getInputStream() {
            byte[] bytes = null;

            try {
                bytes = Singletons.objectMapper.writeValueAsBytes(Collections.emptyMap());
            } catch (Throwable e) {

            }

            return new DelegatingServletInputStream(new ByteArrayInputStream(bytes));
        }
    }
}
