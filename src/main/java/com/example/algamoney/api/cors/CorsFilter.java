package com.example.algamoney.api.cors;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	
	private static final List<String> ALLOW_METHODS = Arrays.asList(
			HttpMethod.POST.name(), HttpMethod.GET.name(), HttpMethod.OPTIONS.name(), 
			HttpMethod.DELETE.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name());
	
	private static final List<String> ALLOW_HEADERS = Arrays.asList(
			HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT);
	
	private static final String MAX_AGE = "3600";
	private static final String ALLOW_CREDENTIALS = "true";
	private static final String ALLOW_ORIGIN = "*";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String allowOrigin = Optional
				.ofNullable(req.getHeader(HttpHeaders.ORIGIN))
				.orElse(ALLOW_ORIGIN);		
		
		resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowOrigin);
		resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOW_CREDENTIALS);
		
		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, String.join(",",  ALLOW_METHODS));
			resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",",  ALLOW_HEADERS));
			resp.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
			
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
	}

}
