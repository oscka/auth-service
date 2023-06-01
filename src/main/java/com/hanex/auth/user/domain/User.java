package com.hanex.auth.user.domain;

import com.hanex.auth.user.dto.UserDto;
import com.hanex.auth.common.enums.UserRole;
import com.hanex.auth.common.enums.UserStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
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

	@Column("user_name")
	private String name;

	@Column("user_status")
	private UserStatus userStatus;

	@Column("user_role")
	private UserRole role;

	private String email;

	@Column("user_password")
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
		this.userStatus = UserStatus.LOCKED;
	}

	public void delete() {
		this.userStatus = UserStatus.DELETED;
	}

	public UserDto.UserInfoResponse toDto(){
		return UserDto.UserInfoResponse.builder()
				.createdAt(this.createdAt)
				.name(this.name)
				.email(this.email)
				.loginId(this.loginId)
				.userStatus(this.userStatus)
				.role(this.role)
				.phone(this.phone)
				.build();
	}

}
