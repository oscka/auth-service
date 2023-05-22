package com.hanex.authservice.common.config;

import com.hanex.authservice.common.util.encrypt.EncryptString;
import com.hanex.authservice.common.util.encrypt.Encryptor;
import com.hanex.authservice.common.util.encrypt.SimpleEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.lang.Nullable;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@EnableJdbcAuditing
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

	@Bean
	@Override
	public JdbcCustomConversions jdbcCustomConversions() {
		Encryptor encryptor = new SimpleEncryptor();
		return new JdbcCustomConversions(List.of(
			new EncryptStringWritingConverter(encryptor),
			new EncryptStringReadingConverter(encryptor),
			new Converter<Clob, String>() {
				@Nullable
				@Override
				public String convert(Clob clob) {
					try {
						return Math.toIntExact(clob.length()) == 0
							? "" : clob.getSubString(1, Math.toIntExact(clob.length()));

					} catch (SQLException e) {
						throw new IllegalStateException("Failed to convert CLOB to String.", e);
					}
				}
			}));
	}

	@Bean
	@Order
	BeforeSaveCallback<?> validateBeforeSave(Validator validator) {
		return (aggregate, change) -> {
			Set<ConstraintViolation<Object>> violations = validator.validate(aggregate);
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(violations);
			}

			return aggregate;
		};
	}


	@WritingConverter
	static class EncryptStringWritingConverter implements Converter<EncryptString, byte[]> {
		private final Encryptor encryptor;

		public EncryptStringWritingConverter(Encryptor encryptor) {
			this.encryptor = encryptor;
		}

		@Override
		public byte[] convert(EncryptString source) {
			return this.encryptor.encrypt(source.getValue());
		}
	}

	@ReadingConverter
	static class EncryptStringReadingConverter implements Converter<byte[], EncryptString> {
		private final Encryptor encryptor;

		public EncryptStringReadingConverter(Encryptor encryptor) {
			this.encryptor = encryptor;
		}

		@Override
		public EncryptString convert(byte[] source) {
			String value = this.encryptor.decrypt(source);
			if (value == null) {
				return null;
			}

			return new EncryptString(value);
		}
	}



	@ReadingConverter
	class StringToBooleanConverter implements Converter<String, Boolean> {

		@Override
		public Boolean convert(String source) {
			return source != null && source.equalsIgnoreCase("Y") ? Boolean.TRUE : Boolean.FALSE;
		}
	}

	@WritingConverter
	class BooleanToStringConverter implements Converter<Boolean, String> {

		@Override
		public String convert(Boolean source) {
			return source != null && source ? "Y" : "N";
		}
	}
}
