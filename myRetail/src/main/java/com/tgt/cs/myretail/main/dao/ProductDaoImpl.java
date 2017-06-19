package com.tgt.cs.myretail.main.dao;

import java.util.Iterator;

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tgt.cs.myretail.main.exception.ProductException;
import com.tgt.cs.myretail.main.vo.Product;
import com.tgt.cs.myretail.main.vo.ProductPrice;


@Repository
public class ProductDaoImpl implements ProductDAO {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

	@Autowired
	Nitrite nitrite;
	
	
	public void insertProductPrice(Nitrite db) {	
				
		ProductPrice productPrice1 =  new ProductPrice();
//		productPrice1.setProductId("16696652");
		productPrice1.setValue("5.00");
		productPrice1.setCurrencyCode("USD");
		
		ProductPrice productPrice2 =  new ProductPrice();
//		productPrice2.setProductId("16752456");
		productPrice2.setValue("9.00");
		productPrice2.setCurrencyCode("USD");
		
		ProductPrice productPrice3 =  new ProductPrice();
//		productPrice3.setProductId("15643793");
		productPrice3.setValue("150.00");
		productPrice3.setCurrencyCode("INR");
		
		ProductPrice productPrice4 =  new ProductPrice();
//		productPrice4.setProductId("13860428");
		productPrice4.setValue("13.49");
		productPrice4.setCurrencyCode("USD");
		
		ProductPrice productPrice5 =  new ProductPrice();
//		productPrice5.setProductId("15117729");
		productPrice5.setValue("5.50");
		productPrice5.setCurrencyCode("USD");
		
		ProductPrice productPrice6 =  new ProductPrice();
//		productPrice6.setProductId("16483589");
		productPrice6.setValue("9.20");
		productPrice6.setCurrencyCode("USD");
		
		Document doc1 = Document.createDocument("16696652", productPrice1);
		Document doc2 = Document.createDocument("16752456", productPrice2);
		Document doc3 = Document.createDocument("15643793", productPrice3);
		Document doc4 = Document.createDocument("13860428", productPrice4);
		Document doc5 = Document.createDocument("15117729", productPrice5);
		Document doc6 = Document.createDocument("16483589", productPrice6);
		
		// create an object repository 
		// create/open a collection named - test
		NitriteCollection collection = db.getCollection("myretail");
		collection.insert(doc1, doc2, doc3, doc4, doc5, doc6);
	}
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	public ProductPrice getProductPrice(String productId) throws ProductException {
		
		logger.info("Entering getProductPrice for product id: {}", productId);
		ProductPrice productPrice = null;
		try {
			if(null != productId) {
				Iterator<Document> iterator = nitrite.getCollection("myretail").find().iterator();
				while(iterator.hasNext()) {
					Document document = iterator.next();
					if(document.containsKey(productId)) {
						return (ProductPrice) document.get(productId);
					}
				}
			}
		} catch(Exception exception) {
			logger.error("Exception thrown: {}",exception.getMessage());
			ProductException productException = new ProductException("Product Price Nitrite retrieve failed");
			throw productException;
		}
		
		return productPrice;
	}
	
	/**
	 * 
	 * @param productPrice
	 * @return
	 */
	public int putProductPrice(Product product) throws ProductException {
		int updateCount = 0;
		logger.info("Entering putProductPrice for product id: {}", product.getId());
		try {
			if(null != product.getProductPrice()) {
				Iterator<Document> iterator = nitrite.getCollection("myretail").find().iterator();
				while(iterator.hasNext()) {
					Document document = iterator.next();
					if(document.containsKey(product.getId())) {
						//ProductPrice productPrice = (ProductPrice) document.get(product.getId());
						document.put(product.getId(), product.getProductPrice());
						updateCount = 1;
						break;
					}
				}
			}
		} catch(Exception exception) {
			logger.error("Exception thrown: {}",exception.getMessage());
			ProductException productException = new ProductException("Product Price Nitrite update failed");
			throw productException;
		}
		
		return updateCount;
	}
	
	/**
	
	public static void main(String args[]) {
		
		ProductDaoImpl productDAO = new ProductDaoImpl();
		Nitrite db = Nitrite.builder()
		        .compressed()
		        .filePath("C:\\Arun\\software\\nitrite\\myretail.db")
		        .openOrCreate("user", "password");
		productDAO.insertProductPrice(db);
		System.out.println("Product Price Details inserted");
		db.close();
		
	} **/
}
