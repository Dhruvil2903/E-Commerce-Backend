package com.product.CustomException;

public class ProductAlreadyExistException extends Exception {

	public ProductAlreadyExistException(String str) {
		super(str);
	}
}
