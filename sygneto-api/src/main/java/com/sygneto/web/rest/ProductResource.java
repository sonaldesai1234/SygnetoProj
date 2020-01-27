package com.sygneto.web.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.sygneto.domain.ErrorCode;
import com.sygneto.domain.GstSlabs;
import com.sygneto.domain.Item;
import com.sygneto.domain.Media;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Product;

import com.sygneto.domain.ProductMRPHistory;
import com.sygneto.domain.ProductMrp;
import com.sygneto.repository.MediaRepository;
import com.sygneto.repository.ProductMRPHistoryRepository;
import com.sygneto.repository.ProductMrpRepository;
import com.sygneto.service.ProductMRPHistoryService;
import com.sygneto.service.ProductMrpService;

import com.sygneto.domain.SygnetoResponse;

import com.sygneto.service.ProductService;
import com.sygneto.web.rest.errors.BadRequestAlertException;
import com.sygneto.web.rest.errors.NotFoundException;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link com.sygneto.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

	private final Logger log = LoggerFactory.getLogger(ProductResource.class);


	private static final String ENTITY_NAME = "product";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ProductService productService;

	public ProductResource(ProductService productService) {
		this.productService = productService;
	}
	@Autowired
	CustomResponse customeResponce;
	@Autowired
	ProductMrpRepository productMrpRepository;
	@Autowired
	ProductMrpService productMrpService;
    @Autowired
    ProductMRPHistoryRepository productMRPHistoryRepository;
    
    @Autowired
    ProductMRPHistoryService productMRPHistoryService;
    @Autowired
    MediaRepository mediaRepository;
    
	/**
	 * {@code POST  /products} : Create a new product.
	 *
	 * @param product
	 *            the product to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new product, or with status {@code 400 (Bad Request)} if the
	 *         product has already an ID.
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	
    @ApiOperation(value="Add new product")
	@PostMapping("/product")
	public Object createProduct(@RequestBody Product product) throws URISyntaxException {
		log.debug("REST request to save product : {}", product);
		if (product.getProductId() != null) {
			throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
		}
		// set product version as static field
		float ver = (float) 1.0;
		product.setProductVersion(ver);
		
		Product productObj=new Product();
		ProductMrp productMrpObj=new ProductMrp();
		
        productMrpObj.setProductMrpPrice(product.getProductMrp().getProductMrpPrice());
		productMrpObj.setTotalProductPrice(product.getProductMrp().getTotalProductPrice());
		productMrpObj.setValidFrom(product.getProductMrp().getValidFrom());
		productMrpObj.setValidTill(product.getProductMrp().getValidTill());
		productMrpObj.setGstSlabs(product.getProductMrp().getGstSlabs());
		ProductMrp productMrp = productMrpService.save(productMrpObj);
	    productObj.setProductName(product.getProductName());
	    productObj.setUnitOfMeasurment(product.getUnitOfMeasurment());
	    productObj.setProductCode(product.getProductCode());
	    productObj.setSac(product.getSac());
	    productObj.setBaseImageUrl(product.getBaseImageUrl());
	    productObj.setCreatedBy(product.getCreatedBy());
	    productObj.setCreatedDate(product.getCreatedDate());
	    productObj.setLastModifiedBy(product.getLastModifiedBy());
	    productObj.setLastModifiedDate(product.getLastModifiedDate());
	    productObj.setProductCategory(product.getProductCategory());
	    productObj.setProductInformation(product.getProductInformation());
	    productObj.setProductNumber(product.getProductNumber());
	    productObj.setProductSpecification(product.getProductSpecification());
	    productObj.setSku(product.getSku());
	    productObj.setTotalQtyInStock(product.getTotalQtyInStock());
	    productObj.setMedia(product.getMedia());
	   
	    productObj.setProductMrp(productMrp);

	    Product result = productService.save(productObj);
	
		return customeResponce.success("Product created", result);
	}

	/**
	 * {@code PUT  /products} : Updates an existing product.
	 *
	 * @param product
	 *            the product to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated product, or with status {@code 400 (Bad Request)} if the
	 *         product is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the product couldn't be
	 *         updated.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
    @ApiOperation(value="Update existing product details")
	@PutMapping("/product")
	public Object updateProduct(@RequestBody Product product) throws URISyntaxException {
		log.debug("REST request to update product : {}", product);
		if (product.getProductId() == null){
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Optional<Product> result = productService.findById(product.getProductId());
		
		Float floatObj;
		floatObj = new Float(result.get().getProductVersion());
		float incrver = floatObj;
		float f = incrver + 1;
		ProductMrp productMrp=new ProductMrp();
		productMrp.setProductMrpId(product.getProductMrp().getProductMrpId());
		productMrp.setProductMrpPrice(product.getProductMrp().getProductMrpPrice());
		productMrp.setValidFrom(product.getProductMrp().getValidFrom());
		productMrp.setValidTill(product.getProductMrp().getValidTill());
		productMrp.setGstSlabs(product.getProductMrp().getGstSlabs());
	   //float slab=product.getProductMrp().getGstSlabs().getGstSlabs();
	   float slab=productMrp.getGstSlabs().getGstSlabs();
	    
		float price=product.getProductMrp().getProductMrpPrice();
	 
		System.out.println("*********price"+price);
		//float discountedPrice=product.getProductMrp().getDiscount();
		productMrp.setDiscountUnit(product.getProductMrp().getDiscountUnit());
		if(product.getProductMrp().getDiscountUnit().equalsIgnoreCase("PERCENTAGE") )
		{
			float discountedPrice=product.getProductMrp().getDiscount();
			System.out.println("*********disprice"+discountedPrice);

	    	float discount= (price/100 * discountedPrice);
	    	
			System.out.println("*********disapply"+discount);
	    	
	    	productMrp.setDiscount(discount);
	    	
	    	float total=price - discount;
	        
			System.out.println("*********totalasperdis"+total);

		    float igst= (total*slab)/100;
			System.out.println("*********igst"+igst);

		    productMrp.setIgst(igst);	    
		    productMrp.setSgst((float)igst/2);
		    productMrp.setCgst((float)igst/2); 
			productMrp.setTotalProductPrice(igst+total);
			
		}
		else if(product.getProductMrp().getDiscountUnit().equalsIgnoreCase("CURRENCY"))
		{
			float discountedPrice=product.getProductMrp().getDiscount();
			System.out.println("*********disprice"+discountedPrice);
	    	productMrp.setDiscount(product.getProductMrp().getDiscount());
	    	float total= price - discountedPrice;
			System.out.println("*********totalasperdis"+total);
		    float igst= (total*slab)/100;
			System.out.println("*********igst"+igst);
		    productMrp.setIgst(igst);	    
		    productMrp.setSgst((float)igst/2);
		    productMrp.setCgst((float)igst/2);    
			productMrp.setTotalProductPrice(igst+total);
			System.out.println("*********totalasperdis"+productMrp.getTotalProductPrice());		
		}
		else 
		{
			
			customeResponce.failure(404, "DiscountType not found");
		}		
	    ProductMrp productMrpObj = productMrpRepository.save(productMrp);
		product.setProductVersion(f);
	    product.setProductMrp(productMrpObj);;
	  
	   ProductMRPHistory productMRPHistory=new ProductMRPHistory();
	   Product prod = productService.save(product);
	  
      if(product.getProductMrp().getProductMrpPrice() !=result.get().getProductMrp().getProductMrpPrice() ||
    		  !product.getProductMrp().getValidFrom().equals(result.get().getProductMrp().getValidFrom())){
    	productMRPHistory.setProductId(result.get().getProductId());	
    	productMRPHistory.setProductMrpPrice(productMrp.getProductMrpPrice());
    	productMRPHistory.setTotalProductPrice(productMrp.getTotalProductPrice());
  		productMRPHistory.setValidFrom(productMrp.getValidFrom());
  	    productMRPHistory.setValidTill(productMrp.getValidTill());
  	    
  	    Instant  instantstart = prod.getProductMrp().getValidFrom().minus(Duration.ofDays(1));
  	    productMRPHistory.setValidTill(instantstart);
  	    productMRPHistoryRepository.save(productMRPHistory);
      }
        return customeResponce.success("product mrp updated", prod);
	  }

	/**
	 * {@code GET  /products} : get all the products.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of products in body.
	 */
    @ApiOperation(value="Get all product details")
	@GetMapping("/product")
	public Object getAllProducts() {
		log.debug("REST request to get all product");
		List<Product> result = productService.findAll();
		if (result != null&& !result.isEmpty()) {
			return customeResponce.success("product found", result);

		} else {
			/*return customeResponce.failure(404, "product not found");*/
			throw new NotFoundException();

		}
	}
    @ApiOperation(value="find total products avaible")
	@GetMapping("/productCount")
	public Object getAllProductsCount() {
		log.debug("REST request to get all product");
		long result = productService.findCount();
		
		return result;
	}

	/**
	 * {@code GET  /products/:id} : get the "id" product.
	 *
	 * @param id
	 *            the id of the product to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the product, or with status {@code 404 (Not Found)}.
	 */
    @ApiOperation(value="find  product details as per product id")

	@GetMapping("/product/{id}")
	public Object getProduct(@PathVariable Long id) {
		log.debug("REST request to get Employee : {}", id);
		Optional<Product> result = productService.findOne(id);
		
		if (result != null) {
			return customeResponce.success("product found", result);

		} else {
			return customeResponce.failure(404, "product not found");

		}
	}
	/*@GetMapping("/product/{productId}")
	public Object getProductHistory(@PathVariable Long productId) {
		log.debug("REST request to get Employee : {}", productId);
		Optional<Product> result = productService.findProductproductMRPHistoryId(productId);
		if (result != null && result.isPresent()) {
			return customeResponce.success("Product found", result);

		} else {
			return customeResponce.failure(404, "Product not found");

		}
	}*/

	/**
	 * {@code DELETE  /products/:id} : delete the "id" product.
	 *
	 * @param id
	 *            the id of the product to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
    @ApiOperation(value="Delete specific product as per product id")
	@DeleteMapping("/product/{id}")
	public Object deleteProduct(@PathVariable Long id) {
		log.debug("REST request to delete Employee : {}", id);
		Optional<Product> result = productService.findOne(id);
		if (result != null && result.isPresent()) {
			try {
				productService.delete(id);
				return customeResponce.success("Product deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Product not deleted");
			}
		} else {
			return customeResponce.failure(404, "Product not found");
		}
	}
}
