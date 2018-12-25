package com.filestorage.stub.exception;

import feign.FeignException;

/**
 * @author Minhaj
 */
public class ApiException extends FeignException {
	private static final long serialVersionUID = 4378917955940226517L;

	public ApiException(int status, String message) {
		super(status, message);
	}
}