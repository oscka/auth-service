package com.hanex.auth.user.dto;

import java.util.UUID;

import com.hanex.auth.common.util.CommonEncoder;
import com.hanex.auth.user.domain.User;
import com.hanex.auth.common.enums.UserRole;
import com.hanex.auth.common.enums.UserStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

public class UserDto{


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
		private UserStatus userStatus;

		@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
		@NotBlank(message = "{validation.notBlank.email}")
		private String email;

		@NotBlank(message = "{validation.notBlank.password}")
		private String password;


		private String phone;

		private UserRole role;


		public User toEntity(){
			return User.builder()
				.id(UUID.randomUUID())
				.loginId(this.loginId)
				.name(this.name)
				.userStatus(this.userStatus)
				.phone(this.phone)
				.role(this.role)
				.password(new CommonEncoder().encode(this.password))
				.email(this.email)
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

		private UserStatus userStatus;

		private UserRole role;

		private String email;

		private String phone;

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
					.password(new CommonEncoder().encode(this.password))
					.email(this.email)
					.build();
		}

	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class LoginRequest {

		@NotBlank(message = "{validation.notBlank.id}")
		private String loginId;

		@NotBlank(message = "{validation.notBlank.password}")
		private String password;
	}

}
