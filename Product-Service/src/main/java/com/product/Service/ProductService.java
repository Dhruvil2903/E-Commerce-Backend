package com.product.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.CustomException.ProductAlreadyExistException;
import com.product.CustomException.ProductNotFoundException;
import com.product.Entity.Product;
import com.product.Repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public Product addProducts(Product product) throws ProductAlreadyExistException {
		Optional<Product> existingProduct = productRepository.findByProductName(product.getProductName());

		if (existingProduct.isPresent()) {
			throw new ProductAlreadyExistException("Product already exist");
		}

		return productRepository.save(product);
	}

	public Optional<Product> getProducts(String product) throws ProductNotFoundException {
		Optional<Product> productFound = productRepository.findByProductName(product);

		if (!productFound.isPresent()) {
			throw new ProductNotFoundException("Product not found");
		}

		return productFound;
	}

	public Product updateProduct(String productName, Product updatedProductDetails) throws ProductNotFoundException {
	    // Fetch the product from the database using the productName
	    Optional<Product> productFound = productRepository.findByProductName(productName);

	    if (productFound.isEmpty()) {
	        throw new ProductNotFoundException("Product not found");
	    }

	    // Get the existing product
	    Product product2 = productFound.get();

	    // Update the fields of the existing product with the new values from updatedProductDetails
	    product2.setProductName(updatedProductDetails.getProductName());  // Update product name
	    product2.setProductPrice(updatedProductDetails.getProductPrice());  // Update product price
	    product2.setProductId(updatedProductDetails.getProductId());  // Update product id or other fields as necessary

	    // Save the updated product back to the repository
	    return productRepository.save(product2);
	}

	public Product deleteProduct(String product) throws ProductNotFoundException {
		Optional<Product> productFound = productRepository.findByProductName(product);

		if (productFound.isEmpty()) {
			throw new ProductNotFoundException("Product not found");

		}

		Product deleteProduct = productFound.get();

		productRepository.delete(deleteProduct);
		return deleteProduct;
	}
}
