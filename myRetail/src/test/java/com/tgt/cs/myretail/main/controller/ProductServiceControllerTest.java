package com.tgt.cs.myretail.main.controller;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;



import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tgt.cs.myretail.main.ProductServiceMain;
import com.tgt.cs.myretail.main.config.MyRetailConfig;
import com.tgt.cs.myretail.main.exception.ProductException;
import com.tgt.cs.myretail.main.service.ProductService;
import com.tgt.cs.myretail.main.vo.Product;
import com.tgt.cs.myretail.main.vo.ProductPrice;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;



@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProductServiceController.class, ProductService.class })
@ContextConfiguration(classes = { ProductServiceMain.class })
@WebAppConfiguration
public class ProductServiceControllerTest {

	private MockMvc mockMvc;

	@Mock
	MyRetailConfig myRetailConfig;
	
	@InjectMocks
	ProductServiceController productServiceController = new ProductServiceController();

	@Mock
	ProductService productService;

	File jsonFile;
	
	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(productServiceController).build();
		jsonFile = FileUtils.toFile(getClass().getResource("/product.json"));

	}

	@Test
	public void product_get_invalid_path_give_resource_not_found_response() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/products")
				 .accept(APPLICATION_JSON_VALUE))
				 .andExpect(status().isNotFound())
				 .andDo(print()).andReturn();			
	}
	
	@Test
	public void product_get_valid_data_give_success_response() throws Exception {
		
		Product product = new Product();
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrencyCode("USD");
		productPrice.setValue("10.00");
		product.setId("1234");
		//product.setResponse("Success");
		product.setName("ProductDesc");
		product.setProductPrice(productPrice);
		
		when(productService.getProduct(isA(Product.class)))
		.thenReturn(product);
		
		MvcResult result = mockMvc.perform(get("/products/{id}", "1234").contentType(APPLICATION_JSON_VALUE))
				 .andExpect(status().isOk())
				 .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
				 .andExpect(jsonPath("$.id", is("1234")))
				 .andExpect(jsonPath("$.service_response", is("Success")))
				 .andExpect(jsonPath("$.name", is("ProductDesc"))).andReturn();
		
		verify(productService, times(1)).getProduct(isA(Product.class));
	    verifyNoMoreInteractions(productService);

	}

	@Test
	public void product_put_invalid_data_give_bad_request_response() throws Exception {

		File jsonFileInValidData = FileUtils.toFile(getClass().getResource("/product_invalid.json"));
		
		@SuppressWarnings("deprecation")
		MvcResult mvcResult = mockMvc
				.perform(put("/products/{id}", "15117729").contentType(APPLICATION_JSON_VALUE)
						.content(FileUtils.readFileToString(jsonFileInValidData)))
				.andExpect(status().isBadRequest())
				.andDo(print()).andReturn();

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void product_put_valid_data_give_success_response() throws Exception {

		File jsonFileValidData = FileUtils.toFile(getClass().getResource("/product.json"));
		Integer updateCount = new Integer(1);
		
	    doReturn(updateCount).when(productService).putProduct(isA(Product.class));
	    mockMvc.perform(
	            put("/products/{id}", "15117729")
	                    .contentType(APPLICATION_JSON_VALUE)
	                    .content(FileUtils.readFileToString(jsonFileValidData)))
	             .andExpect(status().isOk())
	             .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
				 .andExpect(jsonPath("$.id", is("15117729")))
				 .andExpect(jsonPath("$.service_response", is("Success")))
				 .andExpect(jsonPath("$.name", is("ProductDesc"))).andReturn();
	    verify(productService, times(1)).putProduct(isA(Product.class));
	    verifyNoMoreInteractions(productService);

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void product_put_valid_data_give_response_for_no_update() throws Exception {

		File jsonFileValidData = FileUtils.toFile(getClass().getResource("/product.json"));
		Integer updateCount = new Integer(0);
		
	    doReturn(updateCount).when(productService).putProduct(isA(Product.class));
	    mockMvc.perform(
	            put("/products/{id}", "15117729")
	                    .contentType(APPLICATION_JSON_VALUE)
	                    .content(FileUtils.readFileToString(jsonFileValidData)))
	             .andExpect(status().isOk())
	             .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
				 .andExpect(jsonPath("$.id", is("15117729")))
				 .andExpect(jsonPath("$.service_response", is("NoUpdates")))
				 .andExpect(jsonPath("$.name", is("ProductDesc"))).andReturn();
	    verify(productService, times(1)).putProduct(isA(Product.class));
	    verifyNoMoreInteractions(productService);

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void product_put_exception_thrown_give_response() throws Exception {

		File jsonFileValidData = FileUtils.toFile(getClass().getResource("/product.json"));
		Integer updateCount = new Integer(0);
		ProductException productException = new ProductException("Failure");
		
	    doThrow(productException).when(productService).putProduct(isA(Product.class));
	    mockMvc.perform(
	            put("/products/{id}", "15117729")
	                    .contentType(APPLICATION_JSON_VALUE)
	                    .content(FileUtils.readFileToString(jsonFileValidData)))
	             .andExpect(status().isNotFound())
	             .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
				 .andExpect(jsonPath("$.id", is("15117729")))
				 .andExpect(jsonPath("$.service_response", is("Failure")))
				 .andExpect(jsonPath("$.name", is("ProductDesc"))).andReturn();
	    verify(productService, times(1)).putProduct(isA(Product.class));
	    verifyNoMoreInteractions(productService);

	}
}
