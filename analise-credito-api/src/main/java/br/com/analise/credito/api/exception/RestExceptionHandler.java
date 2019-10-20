package br.com.analise.credito.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.analise.credito.api.response.RestResponse;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseBody
	@ExceptionHandler(RestResponseMessageException.class)
	public final ResponseEntity<RestResponse> handleAllExceptions(final RestResponseMessageException exceptionResponse, final WebRequest request) {

		HttpStatus status = HttpStatus.valueOf(exceptionResponse.getStatus());

		RestResponse response = new RestResponse();

		response.setCode(status.value());
		response.setStatus(status);
		response.setMessage(exceptionResponse.getMessage());
		response.setDate(LocalDateTime.now());
		response.setPath(request.getDescription(false));

		return new ResponseEntity<RestResponse>(response, status);
	}
}
