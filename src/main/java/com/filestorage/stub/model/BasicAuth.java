package com.filestorage.stub.model;

import feign.auth.BasicAuthRequestInterceptor;

public class BasicAuth {
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
	    return new BasicAuthRequestInterceptor("clientId", "clientSecret");
	}
}
