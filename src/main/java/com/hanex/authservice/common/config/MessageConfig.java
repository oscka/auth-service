package com.hanex.authservice.common.config;

import com.hanex.authservice.common.properties.MessageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

//@RequiredArgsConstructor
//@Configuration
//public class MessageConfig {
//
//	private final MessageProperties properties;
//
//	@Bean
//	public MessageSource messageSource(){
//
//		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//		messageSource.setBasenames(properties.getBasename());
//		messageSource.setDefaultEncoding(properties.getEncoding());
//		messageSource.setCacheSeconds(properties.getCacheDuration());
//		messageSource.setAlwaysUseMessageFormat(properties.getAlwaysUseMessageFormat().booleanValue());
//		messageSource.setUseCodeAsDefaultMessage(properties.getUseCodeAsDefaultMessage().booleanValue());
//		messageSource.setFallbackToSystemLocale(properties.getFallbackToSystemLocale().booleanValue());
//		return messageSource;
//	}
//
//	@Bean
//	public MessageSourceAccessor messageSourceAccessor (){
//		return new MessageSourceAccessor(messageSource());
//	}
//
//
//
//}
