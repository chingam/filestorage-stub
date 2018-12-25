package com.filestorage.stub.service;

import java.io.File;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filestorage.stub.exception.ApiError;
import com.filestorage.stub.exception.ApiException;
import com.filestorage.stub.interceptor.LoggingResponseInterceptor;
import com.filestorage.stub.model.FileResponse;

import feign.Feign;
import feign.Headers;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * @author Minhaj
 * @version 1.0.0
 */
public interface FileStorageServiceAPI {
	
	/**
	 * Upload file
	 * @param request {@link File}
	 * @return response {@link FileResponse}
	 */
	@RequestLine("POST /file/upload")
	@Headers({"Accept: application/json", "Content-Type: multipart/form-data"})
	FileResponse upload(@Param("file") File file);
	
	/**
	 * Download file
	 * @return response {@link FileResponse}
	 */
	@RequestLine("GET /file/download/{fileName}")
	@Headers({"Accept: application/json", "Content-Type: application/json"})
	FileResponse download(@Param("fileName") String fileName);
	
	static FileStorageServiceAPI connect(final String baseUri) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature. ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		return Feign.builder()
				.encoder(new FormEncoder(new JacksonEncoder()))
				.decoder(new JacksonDecoder(mapper))
				.errorDecoder(new Status400Decoder(mapper))
				.logger(new Slf4jLogger())
				.logLevel(Logger.Level.FULL)
				.target(FileStorageServiceAPI.class, baseUri);
	}
	
	static HttpClient newHttpClient(String logFile) {
		return	HttpClientBuilder.create().addInterceptorFirst(new LoggingResponseInterceptor(logFile)).build();
	}
	
	static class Status400Decoder implements ErrorDecoder {
		ObjectMapper mapper;

		protected Status400Decoder(ObjectMapper mapper) {
			super();
			this.mapper = mapper;
		}

		@Override
		public Exception decode(String methodKey, Response response) {
			if (response.status() != 200) {
				if (response.reason() != null) {
					ApiError body = new ApiError(response.reason());
					throw new ApiException(response.status(), body.getError());
				}
			}
			return new ErrorDecoder.Default().decode(methodKey, response);
		}
	}
	
}
