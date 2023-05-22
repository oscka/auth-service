package com.hanex.authservice.domain.user.common;

/*
권한 Dimension 설명

A (관리항목) - 화주, 채널 등

B (업무) - CSR 그룹, 재고관리 그룹, 계약관리 그룹, 기획 그룹 등

C (기능) - Dashboard, CO, SO, 계약 등
 */
public enum UserRole {

	ROLE_ADMIN, // 관리자 (내부 사용자)

	ROLE_CLIENT, // 고객사 (화주)

	ROLE_CUSTOMER // 사용자 (거래처,실행사,택배사)

}
