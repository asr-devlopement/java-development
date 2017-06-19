/**
 * 
 */
package com.tgt.cs.myretail.main.dao;

import com.tgt.cs.myretail.main.exception.ProductException;
import com.tgt.cs.myretail.main.vo.Product;
import com.tgt.cs.myretail.main.vo.ProductPrice;

public interface ProductDAO {
	public ProductPrice getProductPrice(String productId) throws ProductException;
	public int putProductPrice(Product product) throws ProductException;
}
