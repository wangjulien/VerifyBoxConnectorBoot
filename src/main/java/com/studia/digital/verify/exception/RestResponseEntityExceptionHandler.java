package com.studia.digital.verify.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.studia.digital.verify.domain.NeoGedResponseMsg;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol.ReturnCode;

/**
 * Central managed exception for REST response
 * 
 * @author Jiliang.WANG
 *
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	public RestResponseEntityExceptionHandler() {
		super();
	}

	// API

	@ExceptionHandler({ VerifySignatureException.class })
	public ResponseEntity<Object> handleSftpConfigException(final RuntimeException ex, final WebRequest request) {
		logger.error("400 Status Code", ex);
		final NeoGedResponseMsg bodyOfResponse = new NeoGedResponseMsg();
		bodyOfResponse.setCodeRetour(ReturnCode.KO.toString());
		bodyOfResponse.setMessageErreur(ex.getMessage());
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		logger.error("500 Status Code", ex);
		final NeoGedResponseMsg bodyOfResponse = new NeoGedResponseMsg();
		bodyOfResponse.setCodeRetour(ReturnCode.KO.toString());
		bodyOfResponse.setMessageErreur(ex.getMessage());
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
