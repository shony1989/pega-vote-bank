package com.pega.vote.bank.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pega.vote.bank.exception.InputNotValidException;
import com.pega.vote.bank.exception.SameCountryVoteException;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	// API
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		log.error("Error in Service", ex);
		return new ResponseEntity<Object>(getErrorResponseEntity("", ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		log.error("Error in Service", ex);
		return new ResponseEntity<Object>(getErrorResponseEntity("", ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
	}

	// 400
	@ExceptionHandler({ InputNotValidException.class })
	public ResponseEntity<Object> handleClienterror(final Exception ex, final WebRequest request) {
		log.error("400 error :", ex);
		return new ResponseEntity<Object>(getErrorResponseEntity("", ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
	}

	// 404
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFound(final Exception ex, final WebRequest request) {
		log.error("404 error :", ex);
		return new ResponseEntity<Object>(getErrorResponseEntity("", ex.getMessage(), 404), HttpStatus.NOT_FOUND);
	}

	// 409
	@ExceptionHandler({ SameCountryVoteException.class })
	public ResponseEntity<Object> handleConflict(final Exception ex, final WebRequest request) {
		log.error("404 error :", ex);
		return new ResponseEntity<Object>(getErrorResponseEntity("", ex.getMessage(), 404), HttpStatus.CONFLICT);
	}

	// 500
	@ExceptionHandler({ NullPointerException.class, Exception.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		log.error("500 Status Code", ex);
		return new ResponseEntity<Object>(getErrorResponseEntity("", "Server Error", 500),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 
	 * @param traceId
	 * @param message
	 * @param status
	 * @return
	 */
	private Error getErrorResponseEntity(String traceId, String message, int status) {

		ErrorProperty property = new ErrorProperty();
		property.setTraceId(traceId);
		property.setMessage(message);
		property.setStatus(status);

		Error responsePOJO = new Error();
		List<ErrorProperty> propertyList = new ArrayList<>();
		propertyList.add(property);
		responsePOJO.setErrors(propertyList);

		return responsePOJO;

	}

}
