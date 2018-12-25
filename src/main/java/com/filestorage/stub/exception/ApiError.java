package com.filestorage.stub.exception;

/**
 * @author Minhaj
 */
public class ApiError {

	private String error;

	public ApiError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ApiError [error=" + error + "]";
	}

}
