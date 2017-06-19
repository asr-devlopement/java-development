package com.tgt.cs.myretail.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tgt.cs.myretail.main.exception.ProductException;
import com.tgt.cs.myretail.main.service.ProductService;
import com.tgt.cs.myretail.main.vo.Product;
import org.springframework.web.bind.annotation.RequestBody;



import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/products", produces = { APPLICATION_JSON_VALUE })
public class ProductServiceController {
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceController.class);

	@Autowired
    ProductService productService;
	
	
	
	@RequestMapping(value = "/{id}", produces = { APPLICATION_JSON_VALUE }, consumes = { APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	@SuppressWarnings(value = {"unchecked"})
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id" , required = true) String id) {
        logger.info("Fetching Product with id {}", id);
        Product product = new Product();
        try {
        	product.setId(id);
        	product = productService.getProduct(product);
        	product.setResponse("Success");
        } catch(ProductException exception) {
        	product.setId(id);
        	product.setResponse("Failed");
        	return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", produces = { APPLICATION_JSON_VALUE }, consumes = { APPLICATION_JSON_VALUE }, method = RequestMethod.PUT)
	@SuppressWarnings(value = {"unchecked"})
	public ResponseEntity<Product> putProduct(
			@RequestBody Product product,
			@PathVariable(value = "id" , required = true) String id) {
        logger.info("Updating Product details for id {}", id);
        try {
        	product.setId(id);
        	int updateCount = productService.putProduct(product);
        	logger.info("Successful update with count {}", updateCount);
        	if(updateCount == 0) {
        		product.setResponse("NoUpdates");
        	}
        } catch(ProductException exception) {
        	product.setResponse(exception.getMessage());
        	return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
	

}
