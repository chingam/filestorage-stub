package com.filestorage.stub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Item {

	@JsonProperty("code")
	private String code;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("quantity")
	private Integer quantity;
}
