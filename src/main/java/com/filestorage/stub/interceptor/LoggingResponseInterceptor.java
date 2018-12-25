package com.filestorage.stub.interceptor;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingResponseInterceptor implements HttpResponseInterceptor {

	private String fileNameWithPath;

	public LoggingResponseInterceptor(String fileNameWithPath) {
		this.fileNameWithPath = fileNameWithPath;
	}

	@Override
	public void process(HttpResponse response, HttpContext context)	throws HttpException, IOException {/*
		HttpRequest rq = (HttpRequest) context.getAttribute(HttpClientContext.HTTP_REQUEST);
		log.info("DX response for " + rq.getRequestLine().getUri());
		String uri = rq.getRequestLine().getUri();
		String fileName = fileNameWithPath;
		if (uri.indexOf("AddConsignment") != -1) {
			fileName += "-shipment-rs.json";
		} else if (uri.indexOf("GetSessionKey") != -1) {
			fileName += "-session-rs.json";
		} else if (uri.indexOf("ReleaseConsignment") != -1) {
			fileName += "-release-rs.json";
		}
			
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			log.debug("No response body");
			return;
		}
		byte[] entityAsBytes = EntityUtils.toByteArray(entity);
		log.debug("Saving message to {}", fileName);
		try (FileOutputStream fos = new FileOutputStream(fileName)) {
			fos.write(entityAsBytes);
		} catch (IOException e) {
			log.warn("Unable to save message to {}", fileName, e);
		}
		EntityUtils.updateEntity(response, new ByteArrayEntity(entityAsBytes));
		
	*/}

}
