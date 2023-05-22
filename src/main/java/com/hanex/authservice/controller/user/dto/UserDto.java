package com.hanex.authservice.controller.user.dto;


import com.hanex.authservice.common.util.encrypt.EncryptString;
import com.hanex.authservice.domain.user.User;
import com.hanex.authservice.domain.user.common.UserState;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

public class UserDto {

	@Autowired
	private static PasswordEncoder passwordEncoder;

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SaveRequest {


		@NotBlank(message = "{validation.notBlank.id}")
		private String loginId;

		@NotBlank(message = "{validation.notBlank.name}")
		private String name;

		@NotNull
		private UserState state;

		@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
		@NotBlank(message = "{validation.notBlank.email}")
		private String email;

		@NotBlank(message = "{validation.notBlank.password}")
		private String password;

		public User toEntity(){
			return User.builder()
				.loginId(this.loginId)
				.name(this.name)
				.state(this.state)
				.password(passwordEncoder.encode(this.password))
				.email(new EncryptString(this.email))
				.build();
		}

	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UserInfoResponse {

		private String loginId;

		private String name;

		private UserState state;

		private String email;

		private Instant createdAt;

	}


	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class UpdateRequest {

		@NotBlank(message = "{validation.notBlank.name}")
		private String name;

		@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
		@NotBlank(message = "{validation.notBlank.email}")
		private String email;

		@NotBlank(message = "{validation.notBlank.password}")
		private String password;

		public User toEntity(){
			return User.builder()
					.name(this.name)
					.password(passwordEncoder.encode(this.password))
					.email(new EncryptString(this.email))
					.build();
		}

	}
}
