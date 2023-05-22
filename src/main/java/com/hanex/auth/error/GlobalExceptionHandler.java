package com.hanex.auth.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final MessageSource messageSource;


	public String getMessage(String code){
		return getMessage(code,null);
	}

	public String getMessage(String code,Object[] args){
		return messageSource.getMessage(code ,args, LocaleContextHolder.getLocale());
	}




}
