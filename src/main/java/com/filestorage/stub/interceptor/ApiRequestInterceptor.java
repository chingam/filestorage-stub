package com.filestorage.stub.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ApiRequestInterceptor implements RequestInterceptor {
	
	private final String token;

	public ApiRequestInterceptor(String token) {
		super();
		this.token = token;
	}

	@Override
	public void apply(RequestTemplate template) {
		if (token != null && !token.isEmpty()) {
			template.header("Authorization","Bearer " + token);
		}
	}
}
