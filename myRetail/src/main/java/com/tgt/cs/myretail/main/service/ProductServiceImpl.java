package com.tgt.cs.myretail.main.service;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tgt.cs.myretail.main.dao.ProductDAO;
import com.tgt.cs.myretail.main.exception.ProductException;
import com.tgt.cs.myretail.main.vo.Product;
import com.tgt.cs.myretail.main.vo.ProductPrice;



@Service
public class ProductServiceImpl implements ProductService {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Value("${redsky.url}")
	private String redskyUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ProductDAO productDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.tgt.cs.myretail.main.service.ProductService#getProduct(com.tgt.cs.myretail.main.vo.Product)
	 */
	public Product getProduct(Product product) throws ProductException {
		 
		logger.info("Entering ProductServiceImpl.getProduct()");
		try {
			product.setName(getProductNameById(product.getId()));
			ProductPrice productPrice = productDAO.getProductPrice(product.getId());
			product.setProductPrice(productPrice);		
		} catch(ProductException productException) {
			throw productException;
		} catch(Exception exception) {
			logger.error("Unknown Exception thrown: {}", exception.getMessage());
			ProductException productException = new ProductException(exception.getMessage());
			throw productException;
		}
		logger.info("Successfully retrieved product details");
		return product;
	}
	
	/**
	 * 
	 * @param product
	 * @throws ProductException
	 */
	public int putProduct(Product product) throws ProductException {
		 
		int updateCount = 0;
		logger.info("Entering ProductServiceImpl.putProduct()");
		try {
			updateCount = productDAO.putProductPrice(product);
		} catch(ProductException productException) {
			throw productException;
		} catch(Exception exception) {
			logger.error("Unknown Exception thrown: {}", exception.getMessage());
			ProductException productException = new ProductException(exception.getMessage());
			throw productException;
		}
		
		logger.info("Successfully updated count: {}", updateCount);	
		return updateCount;
	}
	
	/*
	 * Call RedSky service to get product name details
	 */
	@SuppressWarnings(value = {"unchecked", "rawtypes"})
	public String getProductNameById(String productId) throws ProductException {
		logger.info("Calling RedSky Service for product");
		String productName = null;
		try {
			String url = redskyUrl.replace("PRODUCT_ID", productId);		
			Map map = restTemplate.getForObject(url, Map.class);
			productName = ((Map)((Map)((Map)map.get("product")).get("item")).get("product_description")).get("title").toString();
			productName =  (productName != null) ? productName : "No Product Description";
		} catch(HttpClientErrorException clientErrorException) {
			logger.info("Empty Response: {}", clientErrorException.getMessage());
			if(clientErrorException.getMessage().contains("404")) {
				productName = "No Product Description";
			}
			
		} catch(Exception exception) {
			logger.info("Calling RedSky Service exception: {}", exception.getMessage());
			ProductException productException = new ProductException(exception.getMessage());
			throw productException;
		}		
		return productName;
	}
	
	/**
	public static void main(String args[])  {
		logger.info("Calling RedSky Service for product");
		
		try {
			String url = "http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";		
			RestTemplate restTestTemplate = new RestTemplate();
			Map map = restTestTemplate.getForObject(url, Map.class);
//			productName = ((HashMap<String, String>)((HashMap<String, HashMap>)((HashMap<String, HashMap>) hashMap.get("product")).get("item")).get("product_description")).get("title");
			String productName = ((Map)((Map)((Map)map.get("product")).get("item")).get("product_description")).get("title").toString();
			System.out.println(productName);
		} catch(Exception exception) {
			System.out.println("Exception");
			
		}		
		
	} **/
	
}
