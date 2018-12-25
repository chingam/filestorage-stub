package com.filestorage.stub.service;

import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filestorage.stub.exception.ApiError;
import com.filestorage.stub.exception.ApiException;
import com.filestorage.stub.interceptor.ApiRequestInterceptor;
import com.filestorage.stub.interceptor.LoggingRequestInterceptor;
import com.filestorage.stub.interceptor.LoggingResponseInterceptor;
import com.filestorage.stub.model.AuthResponse;
import com.filestorage.stub.model.Item;
import com.filestorage.stub.model.ItemResponse;

import feign.Feign;
import feign.Headers;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * @author Minhaj
 * @version 1.0.0
 */
public interface InventoryServiceAPI {
	
	/**
	 * Create API session
	 * @param grantType
	 * @param clientId
	 * @param username
	 * @param password
	 * @return response {@link AuthResponse} 
	 */
	@RequestLine("POST /oauth/token?grant_type={grantType}&client_id={clientId}&username={userName}&password={password}")
	AuthResponse getSession(@Param(value= "grantType") String grantType, @Param(value= "clientId") String clientId, @Param(value= "userName") String username, @Param(value= "password") String password);
	
	
	/**
	 * Add Item to API system
	 * @param request {@link Item}
	 * @return response {@link ItemResponse}
	 */
	@RequestLine("POST /item")
	@Headers({"Accept: application/json", "Content-Type: application/json"})
	ItemResponse addItem(Item request);
	
	/**
	 * Get all items to API system
	 * @return response {@link ItemResponse}
	 */
	@RequestLine("GET /item")
	@Headers({"Accept: application/json", "Content-Type: application/json"})
	List<Item> getAllItems();
	
	/**
	 * Get all items to API system
	 * @param code
	 * @return response {@link ItemResponse}
	 */
	@RequestLine("GET /item/{code}")
	@Headers({"Accept: application/json", "Content-Type: application/json"})
	Item getItem(@Param("code") String code);
	
	/**
	 * Delete items to API system
	 * @param code
	 * @return response {@link ItemResponse}
	 */
	@RequestLine("DELETE /item/{code}")
	@Headers({"Accept: application/json", "Content-Type: application/json"})
	ItemResponse delete(@Param("code") String code);
	
	
	static InventoryServiceAPI authorization(final String clientId, final String secret, String baseUri) {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature. ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		return Feign.builder()
				.encoder(new JacksonEncoder(mapper))
				.decoder(new JacksonDecoder(mapper))
				.errorDecoder(new Status400Decoder(mapper))
				.logger(new Slf4jLogger())
				.logLevel(Logger.Level.FULL)
				.requestInterceptors(Arrays.asList(new BasicAuthRequestInterceptor(clientId, secret)))
				.target(InventoryServiceAPI.class, baseUri);
	}
	static InventoryServiceAPI connect(final String baseUri, String token, String fileName) {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.enable(DeserializationFeature. ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		return Feign.builder()
				.client(new ApacheHttpClient(newHttpClient(fileName)))
				.encoder(new JacksonEncoder(mapper))
				.decoder(new JacksonDecoder(mapper))
				.errorDecoder(new Status400Decoder(mapper))
				.logger(new Slf4jLogger())
				.logLevel(Logger.Level.FULL)
				.requestInterceptors(Arrays.asList(new ApiRequestInterceptor(token), new LoggingRequestInterceptor(fileName)))
				.target(InventoryServiceAPI.class, baseUri);
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
