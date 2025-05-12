package com.user.CustomException;

public class UserAlreadyExistException extends Exception {
	public UserAlreadyExistException(String str) {
		super(str);
	}
}
