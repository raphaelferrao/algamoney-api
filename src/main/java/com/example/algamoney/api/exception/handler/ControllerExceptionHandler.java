package com.example.algamoney.api.exception.handler;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.algamoney.api.exception.ErrorDTO;
import com.example.algamoney.api.exception.ObjectNotFoundException;
import com.example.algamoney.api.exception.StandardErrorDTO;
import com.example.algamoney.api.util.Utils;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		final String msgError = messageSource.getMessage("msg.error.method.argument.not.valid", null, LocaleContextHolder.getLocale());
		
		final StandardErrorDTO standardErrorResponse = new StandardErrorDTO(Calendar.getInstance().getTimeInMillis(), httpStatus.value(), 
				msgError, request.getContextPath());
		
		if ( (exception != null) && (exception.getBindingResult() != null) && (!Utils.empty(exception.getBindingResult().getFieldErrors())) ) {
			for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
				// standardErrorResponse.getMessages().add(fieldError.getField() + " " + fieldError.getDefaultMessage());
				standardErrorResponse.getMessages().add(new ErrorDTO(fieldError.toString(), messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())));
			}
		}
		
		return handleExceptionInternal(exception, standardErrorResponse, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException exception,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
	
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		final String msgError = messageSource.getMessage("msg.error.missing.servlet.request.parameter", null, LocaleContextHolder.getLocale());
		
		final StandardErrorDTO standardErrorResponse = new StandardErrorDTO(Calendar.getInstance().getTimeInMillis(), httpStatus.value(), 
				msgError, request.getContextPath());
		
		if (exception != null) {
			final String msgFriendly = messageSource.getMessage("msg.method.argument.type.mismatch", 
					new String[] {exception.getParameterName()}, LocaleContextHolder.getLocale());
			
			standardErrorResponse.getMessages().add(new ErrorDTO(exception.getMessage(), msgFriendly));
		}
		
		return handleExceptionInternal(exception, standardErrorResponse, headers, httpStatus, request);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardErrorDTO> handleObjectNotFound(
			final ObjectNotFoundException exception, final HttpServletRequest httpServletRequest) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<StandardErrorDTO> handleMethodArgumentTypeMismatchException(
			final MethodArgumentTypeMismatchException exception, final HttpServletRequest httpServletRequest) {
		
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		final String msgError = messageSource.getMessage("msg.error.method.argument.type.mismatch", null, LocaleContextHolder.getLocale());
		
		final StandardErrorDTO standardErrorResponse = new StandardErrorDTO(Calendar.getInstance().getTimeInMillis(), httpStatus.value(), 
				msgError, httpServletRequest.getRequestURI());
		
		if (exception != null) {
			final String msgFriendly = messageSource.getMessage("msg.method.argument.type.mismatch", 
					new String[] {exception.getParameter().getParameterName()}, LocaleContextHolder.getLocale());
			
			standardErrorResponse.getMessages().add(new ErrorDTO(exception.getMessage(), msgFriendly));
		}
		
		return ResponseEntity.status(httpStatus).body(standardErrorResponse);
	}


}
