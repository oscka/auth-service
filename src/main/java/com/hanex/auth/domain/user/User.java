package com.hanex.auth.domain.user;

import com.hanex.auth.controller.user.dto.UserDto;
import com.hanex.auth.common.enums.UserRole;
import com.hanex.auth.common.enums.UserState;
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
@Table("user_tb")
public class User {

	@Id
	private UUID id;

	private String loginId;

	private String name;

	private UserState state;

	private UserRole role;

	private String email;

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
			.email(this.email)
			.loginId(this.loginId)
			.state(this.state)
			.build();
	}

}
