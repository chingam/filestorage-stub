package com.filestorage.stub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemResponse {

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("message")
	private String message;
	
}
