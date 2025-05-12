package com.product.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.CustomException.ProductAlreadyExistException;
import com.product.CustomException.ProductNotFoundException;
import com.product.Entity.Product;
import com.product.Service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody Product product)
			throws ProductAlreadyExistException {
		try {
			Product addingProduct = productService.addProducts(product);

			return ResponseEntity.status(HttpStatus.CREATED).body(addingProduct);
		} catch (ProductAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}

	}

	@GetMapping("/{productName}")
	public ResponseEntity<?> getProduct(@PathVariable String productName) {
		try {
			Optional<Product> gettingProduct = productService.getProducts(productName);

			return ResponseEntity.status(HttpStatus.OK).body(gettingProduct);

		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("/{productName}")
	public ResponseEntity<?> updateProduct(@PathVariable String productName, @RequestBody Product product) {
		try {
			Product updatedProduct = productService.updateProduct(productName, product);

			return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	@DeleteMapping("/{productName}")
	public ResponseEntity<?> deleteProduct(@PathVariable String productName) {
	    try {
	        productService.deleteProduct(productName);  
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  
	    } catch (ProductNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  
	    }
	}
}
