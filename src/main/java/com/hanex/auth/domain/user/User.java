package com.hanex.auth.domain.user;

import com.hanex.auth.common.util.encrypt.EncryptString;
import com.hanex.auth.controller.user.dto.UserDto;
import com.hanex.auth.domain.user.common.Gender;
import com.hanex.auth.domain.user.common.UserRole;
import com.hanex.auth.domain.user.common.UserState;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Builder
@Getter
@ToString
@Table("tb_user")
public class User {

	@Id
	private UUID id;

	private String loginId;

	private String name;

	private UserState state;

	private Gender gender;

	private UserRole role;

	private EncryptString email;

	private String password;

	private String phone;

	@Builder.Default
	@CreatedDate
	private Instant createdAt = Instant.now();

	@Builder.Default
	@LastModifiedDate
	private Instant updatedAt = Instant.now();

	// ---------------- 비지니스 로직 --------------- //
	public void lock() {
		this.state = UserState.LOCKED;
	}

	public void delete() {
		this.state = UserState.DELETED;
	}

	public UserDto.UserInfoResponse toDto(){
		return UserDto.UserInfoResponse.builder()
			.createdAt(this.createdAt)
			.name(this.name)
			.email(this.email.getValue())
			.loginId(this.loginId)
			.state(this.state)
			.build();
	}

}
