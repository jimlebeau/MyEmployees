package com.jrl.myemployees.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TaxIdAlreadyExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaxIdAlreadyExist(String exception) {
		super(exception);
	}
}
