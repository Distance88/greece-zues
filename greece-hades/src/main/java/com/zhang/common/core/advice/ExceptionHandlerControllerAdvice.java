package com.zhang.common.core.advice;

import com.zhang.common.core.exception.BizCoreException;
import com.zhang.common.core.restful.CommonRestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Created by zhouqin on 13/10/2016.
 */
@Slf4j
@ControllerAdvice("com.zhang.project.web.rest11")
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
		// log first
		log.error(ex.getLocalizedMessage(), ex);

		// envelope with CommonResponseResult
		CommonRestResult envelop;
		if (ex instanceof BizCoreException) {
			envelop = new CommonRestResult<>(CommonRestResult.FAIL, ex.getMessage(), ((BizCoreException) ex).getCode().getCode());
		} else {
			envelop = new CommonRestResult<>(false, ex.getMessage(), null);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// force 200 response
		return handleExceptionInternal(ex, envelop, headers, HttpStatus.OK, request);
	}

	@Override
	public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                      WebRequest request) {
		// log first
		log.error(ex.getLocalizedMessage() + "[" + ex.getFieldErrors() + "]", ex);

		CommonRestResult envelop = new CommonRestResult(false, ex.getFieldErrors().toString(), null);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return handleExceptionInternal(ex, envelop, headers, HttpStatus.OK, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

		// log first
		log.error(ex.getLocalizedMessage() + "[" + ex.getBindingResult().getFieldErrors() + "]", ex);

		String message = StringUtils.join(ex.getBindingResult().getFieldErrors().stream()
				.map(item -> item.getDefaultMessage()).collect(Collectors.toList()), ",");

		CommonRestResult envelop = new CommonRestResult(false, message, null);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return handleExceptionInternal(ex, envelop, headers, HttpStatus.OK, request);
	}
	
}
