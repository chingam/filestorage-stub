package com.filestorage.stub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthResponse {

	@JsonProperty("organization")
	private String organization;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("jti")
	private String jti;

	@JsonProperty("expires_in")
	private String expiresIn;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("access_token")
	private String accessToken;
}
