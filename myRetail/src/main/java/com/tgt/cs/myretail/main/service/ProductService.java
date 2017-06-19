package com.tgt.cs.myretail.main.service;

import com.tgt.cs.myretail.main.exception.ProductException;
import com.tgt.cs.myretail.main.vo.Product;


public interface ProductService {
	
	public Product getProduct(Product product) throws ProductException;
	public int putProduct(Product product) throws ProductException;

}
