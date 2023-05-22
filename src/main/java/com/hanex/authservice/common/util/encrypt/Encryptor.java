package com.hanex.authservice.common.util.encrypt;

public interface Encryptor {
	byte[] encrypt(String value);

	String decrypt(byte[] encrypted);
}
