package ar.com.rocketdelivery.build.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	protected ResponseEntity<Object> handleStatusCode(ResponseStatusException ex, WebRequest request) {
		log.error(ex.getMessage());
		return handleExceptionInternal(ex, ex.getBody(), new HttpHeaders(), ex.getStatusCode(), request);
	}

}
