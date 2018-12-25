package com.filestorage.stub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FileResponse {

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("file")
	private String file;
	
}
