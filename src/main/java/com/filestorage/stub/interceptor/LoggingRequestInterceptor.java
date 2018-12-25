package com.filestorage.stub.interceptor;

import java.io.FileOutputStream;
import java.io.IOException;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingRequestInterceptor implements RequestInterceptor {

	private String fileNameWithPath;

	public LoggingRequestInterceptor(String fileNameWithPath) {
		this.fileNameWithPath = fileNameWithPath;
	}

	@Override
	public void apply(RequestTemplate template) {/*
		if (template == null) return;
		String method = template.method();
		String fileName  = fileNameWithPath;
		if ("/AddConsignment".equalsIgnoreCase(template.url())) {
			fileName += "-shipment-rq.json";
		} else if ("/GetSessionKey".equalsIgnoreCase(template.url())) {
			fileName += "-session-rq.json";
		} else if ("/ReleaseConsignment".equalsIgnoreCase(template.url())) {
			fileName += "-release-rq.json";
		}
		
		try (FileOutputStream fos = new FileOutputStream(fileName)) {
			if (template.request() != null && (template.request().body() != null)) {
				log.debug("Saving message to {}", fileName);
				fos.write(template.request().body());
			} else {
				log.debug("No request body");
			}
			if(method.equals("GET") || method.equals("PUT")) {
				fos.write(template.request().url().getBytes());
			}
		} catch (IOException e) {
			log.warn("Unable to save message to {}", fileName, e);
		}
	*/}
}
