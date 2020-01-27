package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.ActivityMgt;
import com.sygneto.domain.Address;
import com.sygneto.domain.BOMItem;
import com.sygneto.domain.BillOfMaterial;
import com.sygneto.domain.CategoryGroup;
import com.sygneto.domain.CustomProduct;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Product;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.domain.PurchaseRequestItem;
import com.sygneto.domain.SOPayment;
import com.sygneto.domain.SalesOrder;
import com.sygneto.domain.SalesOrderBOM;
import com.sygneto.domain.SalesOrderBOMItem;
import com.sygneto.domain.SalesOrderHistory;
import com.sygneto.domain.SalesOrderProduct;
import com.sygneto.domain.ShippingDetails;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.BillOfMaterialRepository;
import com.sygneto.repository.InverdItemsRepository;
import com.sygneto.repository.ItemOutwardRepository;
import com.sygneto.repository.ProductRepository;
import com.sygneto.repository.SOPaymentRepository;
import com.sygneto.repository.SalesOrderProductRepository;
import com.sygneto.repository.SalesOrderRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.ActivityMgtService;
import com.sygneto.service.AddressService;
import com.sygneto.service.ProductService;
import com.sygneto.service.SalesBOMService;
import com.sygneto.service.SalesOrderService;
import com.sygneto.web.rest.errors.BadRequestAlertException;
import com.sygneto.web.rest.errors.NotFoundException;

/**
 * REST controller for managing {@link com.sygneto.domain.SalesOrder}.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderResource.class);

    private static final String ENTITY_NAME = "salesOrder";
    
    @Autowired
    SalesBOMService salesBOMService;
    
    @Autowired
    CustomResponse customResponse;

    @Autowired
    AddressService addressService;

    @Autowired
    SOPaymentRepository soPaymentRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesOrderService salesOrderService;

    public SalesOrderResource(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }
    @Autowired
    SalesBOMResource salesBOMResource;
    
    @Autowired
    InverdItemsRepository inverdItemsRepository;
    
    @Autowired
    ItemOutwardRepository itemOutwardRepository;
    
    @Autowired
	BillOfMaterialRepository billOfMaterialRepository;
    
    @Autowired
    ActivityMgtService activityMgtService;
    
    @Autowired
    PurchaseRequestResource purchaseRequestResource;
    
    @Autowired
    SalesOrderProductRepository salesOrderProductRepository;
    
    @Autowired
    SalesOrderRepository salesOrderRepository;
    /**
     * {@code POST  /sales-orders} : Create a new salesOrder.
     *
     * @param salesOrder the salesOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesOrder, or with status {@code 400 (Bad Request)} if the salesOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
 
    @PostMapping("/salesOrder")
    //@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createSalesOrder(@RequestBody SalesOrder salesOrder) throws URISyntaxException {
		log.debug("REST request to save SalesOrder : {}", salesOrder);
		if (salesOrder.getSalesOrderId() != null) {
			throw new BadRequestAlertException("A new salesOrder cannot already have an ID", ENTITY_NAME, "idexists");
		}
		float value = (float) 1.0;
		salesOrder.setSalesVersion(value);
		Double randomValue = Math.random() * 100;
		salesOrder.setSalesOrderNumber(randomValue.toString());
		float totalPrice = 0;
		float sgst = 0;
		float cgst = 0;
		float igst = 0;
		float dis = 0;
		
		for (SalesOrderProduct salesOrderProductObj : salesOrder.getSalesOrderProduct()) {
			float price = salesOrderProductObj.getTotalProductPrice() * salesOrderProductObj.getQuantity();
			salesOrderProductObj.setTotalWithQty(price);
			totalPrice += price;

			float discount = salesOrderProductObj.getDiscount() * salesOrderProductObj.getQuantity();
			dis += discount;
			
			
			salesOrderProductObj.setDiscount(discount);
			if (salesOrder.getShipplingAddressId() != null) {

				Optional<Address> result = addressService.findOne(salesOrder.getShipplingAddressId());

				if (result.get().getState().equalsIgnoreCase("Maharashtra")) {
					sgst += salesOrderProductObj.getSgst() * salesOrderProductObj.getQuantity();
					cgst += salesOrderProductObj.getCgst() * salesOrderProductObj.getQuantity();
					salesOrderProductObj.setIgst(0L);
				} else {
					salesOrderProductObj.setSgst(0L);
					salesOrderProductObj.setCgst(0L);
					igst += salesOrderProductObj.getIgst() * salesOrderProductObj.getQuantity();

				}
			}
		if(salesOrder.getPromoCode()<=1000)
			{
				int prmo=salesOrder.getPromoCode();
				totalPrice-=prmo ;
			}
		else
		{
			
			salesOrder.setPromoCode(0);
		}
		}
		if (salesOrder.getShippingDetails() != null) {
			float amt = salesOrder.getShippingDetails().getShippingAmount();
			totalPrice += amt;
		}
		System.out.println("))))))"+totalPrice);
		salesOrder.setTotal(totalPrice);
		salesOrder.setSgst(sgst);
		salesOrder.setCgst(cgst);
		salesOrder.setIgst(igst);

		salesOrder.setTotalTax(sgst + cgst + igst);
		//salesOrder.setDiscount(salesOrder.getPromoCode());
		SalesOrder result = salesOrderService.save(salesOrder);

		ActivityMgt activityMgt = new ActivityMgt();
		// activityMgt.setNotes("Sales Order is created");
		activityMgt.setStatus(result.getStatus());
		activityMgt.setTitle("Sales Order is created");
		activityMgt.setPriority("Medium");
		activityMgt.setReferenceId(result.getSalesOrderId());
		ActivityMgt resultObj = activityMgtService.save(activityMgt);

		return customResponse.success("Sales order created", result);
	}

       @PostMapping("/salesOrderBom")
      //@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
      public Object createSalesOrderBom(@RequestBody SalesOrder salesOrder) throws URISyntaxException {
        log.debug("REST request to save SalesOrderBom : {}", salesOrder);
        
        SalesOrderBOM salesOrderBOM=new SalesOrderBOM();
        salesOrderBOM.setSalesOrderId(salesOrder.getSalesOrderId());
        salesOrderBOM.setPartyName(salesOrder.getPartyName());
       
        List<CustomProduct>  CustomProductList=new ArrayList<CustomProduct>();
        
        for (SalesOrderProduct salesOrderProduct : salesOrder.getSalesOrderProduct()) {
        	CustomProduct CustomProduct=new CustomProduct();
        	CustomProduct.setProductId(salesOrderProduct.getProductId());
        	CustomProduct.setProductName(salesOrderProduct.getProductName());
        	CustomProduct.setQuantity(salesOrderProduct.getQuantity());
        	List<SalesOrderBOMItem> salesOrderBOMItemList=new ArrayList<SalesOrderBOMItem>();
	    	BillOfMaterial billOfMaterial=billOfMaterialRepository.findByProductProductId(salesOrderProduct.getProductId(), salesOrderProduct.getBomVersion());
		    	for (BOMItem bOMItem : billOfMaterial.getBomItem()) {
		    		  Long qty=bOMItem.getQuantity()*salesOrderProduct.getQuantity();
		    		  SalesOrderBOMItem salesOrderBOMItem=new SalesOrderBOMItem();
		    		  salesOrderBOMItem.setItemId(bOMItem.getItemId());
		    		  salesOrderBOMItem.setItemName(bOMItem.getItemName());
		    		  salesOrderBOMItem.setQuantity(qty);
		    		  salesOrderBOMItem.setItemCode(bOMItem.getItemCode());
		    		  salesOrderBOMItemList.add(salesOrderBOMItem);
				}
		    	 CustomProduct.setSalesOrderBOMItem(salesOrderBOMItemList);
	    		  CustomProductList.add(CustomProduct);
		}
        salesOrderBOM.setProduct(CustomProductList);
        
        ActivityMgt activityMgt=new ActivityMgt();
	    //activityMgt.setNotes("Sales Order is created");
	    activityMgt.setStatus(salesOrder.getStatus());
	    activityMgt.setTitle("Sales BOM is created");
	    activityMgt.setPriority("Medium");
	    activityMgt.setReferenceId(salesOrder.getSalesOrderId());
        ActivityMgt resultObj = activityMgtService.save(activityMgt);
        
		return salesOrderBOM; 
        	
        }
      
      /*@PostMapping("/purchaseReq")
      //@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
      public Object createPurchaseReq(@RequestBody SalesOrder salesOrder) throws URISyntaxException {
        log.debug("REST request to save SalesOrderBom : {}", salesOrder);
        synchronized (salesOrder) {
        	
       Optional<SalesOrder> salesOrderObj=salesOrderService.findOne(salesOrder.getSalesOrderId());
        if(salesOrderObj.get().getPrStatus() == "No" || "No".equals(salesOrderObj.get().getPrStatus())) {
        	
            List<PurchaseRequest> purchaseRequestList= new ArrayList<PurchaseRequest>();
   	    
   	     for (SalesOrderProduct salesOrderProduct : salesOrder.getSalesOrderProduct()) {
   	    	BillOfMaterial billOfMaterial=billOfMaterialRepository.findByProductProductId(salesOrderProduct.getProductId(),salesOrderProduct.getBomVersion());
   	    	if(billOfMaterial !=null ) {
   	    	  if(billOfMaterial.getBomItem() !=null && !billOfMaterial.getBomItem().isEmpty()) {
   	    		  
   		    	   for (BOMItem bOMItem : billOfMaterial.getBomItem()) {
   		    		   PurchaseRequest purchaseRequest = new PurchaseRequest();
   		    		    Long qty=bOMItem.getQuantity()*salesOrderProduct.getQuantity();
   		    	
   		    		purchaseRequest.setQuantity(qty);
   		    		purchaseRequest.setItemId(bOMItem.getItemId());
   		    		purchaseRequest.setItemName(bOMItem.getItemName());
   		    		purchaseRequest.setSalesOrderId(salesOrder.getSalesOrderId());
   		    		purchaseRequest.setProductName(salesOrderProduct.getProductName());
   		    		purchaseRequest.setUnitOfMeasurement(bOMItem.getUnitOfMeasurement());
   		    		purchaseRequest.setItemCode(bOMItem.getItemCode());
   		    		purchaseRequestList.add(purchaseRequest);
   		    		    //Long alreadyReceivedQty=inverdItemsRepository.getSumReceived(bOMItem.getItemId());
   		    	   }
   	    	  }
   	    	}else {
   	    		throw new BadRequestAlertException("This product is not available in BOM", ENTITY_NAME, "idexists");
   	    	}
   	    	  
   	    } 
   	    Object result=purchaseRequestResource.createPurchaseRequestList(purchaseRequestList);
   	    salesOrderObj.get().setPrStatus("Yes");
   	    salesOrderService.save(salesOrderObj.get());
   		return result; 
        }else {
        	
        	throw new BadRequestAlertException("Purchase request is already created", ENTITY_NAME, "idexists");
        }
        } 
      }*/
      @PostMapping("/purchaseReq")
      //@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
      public Object createPurchaseReq(@RequestBody SalesOrder salesOrder) throws URISyntaxException {
        log.debug("REST request to save SalesOrderBom : {}", salesOrder);
        synchronized (salesOrder) {
        	float availablwQty1;
        	if(salesOrder.getSalesVersion() > 1 ) {
        	float value=1f;
        	 availablwQty1= salesOrder.getSalesVersion()-value;
        	}else {
        		availablwQty1=salesOrder.getSalesVersion();
        	}
        	SalesOrder salesOrderObj1=salesOrderService.FindBySalesOrderNumber(salesOrder.getSalesOrderNumber(),availablwQty1);
        
        	if(!salesOrderObj1.getPrStatus().equals("No") && salesOrderObj1.getPrStatus() != "No" ) {
    			for (SalesOrderProduct salesOrderProductFinal : salesOrder.getSalesOrderProduct()) {
    				for (SalesOrderProduct salesOrderProduct : salesOrderObj1.getSalesOrderProduct()) {
    					if(salesOrderProductFinal.getProductId() == salesOrderProduct.getProductId()) {
    						if(salesOrderProductFinal.getQuantity() > salesOrderProduct.getQuantity()) {
    						    Long availablwQty= Math.subtractExact(salesOrderProductFinal.getQuantity() , salesOrderProduct.getQuantity());
    						    salesOrderProductFinal.setQuantity(availablwQty);
    						   
    						}
    						if(salesOrderProductFinal.getQuantity() < salesOrderProduct.getQuantity()) {
    						    Long availablwQty= Math.subtractExact(salesOrderProductFinal.getQuantity() , salesOrderProduct.getQuantity());
    						    salesOrderProductFinal.setQuantity(availablwQty);
    						}
    					}
    				}
    		  }
        	}
       Optional<SalesOrder> salesOrderObj=salesOrderService.findOne(salesOrder.getSalesOrderId());
        if(salesOrderObj.get().getPrStatus() == "No" || "No".equals(salesOrderObj.get().getPrStatus())) {
        	
            List<PurchaseRequest> purchaseRequestList= new ArrayList<PurchaseRequest>();
   	    
   	     for (SalesOrderProduct salesOrderProduct : salesOrder.getSalesOrderProduct()) {
   	    	BillOfMaterial billOfMaterial=billOfMaterialRepository.findByProductProductId(salesOrderProduct.getProductId(),salesOrderProduct.getBomVersion());
   	    	if(billOfMaterial !=null ) {
   	    	  if(billOfMaterial.getBomItem() !=null && !billOfMaterial.getBomItem().isEmpty()) {
   	    		  
   		    	   for (BOMItem bOMItem : billOfMaterial.getBomItem()) {
   		    		   PurchaseRequest purchaseRequest = new PurchaseRequest();
   		    		    Long qty=bOMItem.getQuantity()*salesOrderProduct.getQuantity();
   		    	
   		    		purchaseRequest.setQuantity(qty);
   		    		purchaseRequest.setItemId(bOMItem.getItemId());
   		    		purchaseRequest.setItemName(bOMItem.getItemName());
   		    		purchaseRequest.setSalesOrderId(salesOrder.getSalesOrderId());
   		    		purchaseRequest.setProductName(salesOrderProduct.getProductName());
   		    		purchaseRequest.setUnitOfMeasurement(bOMItem.getUnitOfMeasurement());
   		    		purchaseRequest.setItemCode(bOMItem.getItemCode());
   		    		purchaseRequestList.add(purchaseRequest);
   		    		    //Long alreadyReceivedQty=inverdItemsRepository.getSumReceived(bOMItem.getItemId());
   		    	   }
   	    	  }
   	    	}else {
   	    		throw new BadRequestAlertException("This product is not available in BOM", ENTITY_NAME, "idexists");
   	    	}
   	    	  
   	    } 
   	    Object result=purchaseRequestResource.createPurchaseRequestList(purchaseRequestList);
   	    salesOrderObj.get().setPrStatus("Yes");
   	    salesOrderService.save(salesOrderObj.get());
   	    
   	    ActivityMgt activityMgt=new ActivityMgt();
	    //activityMgt.setNotes("Sales Order is created");
	    activityMgt.setStatus(salesOrder.getStatus());
	    activityMgt.setTitle("Purchase Request is created");
	    activityMgt.setPriority("Medium");
	    activityMgt.setReferenceId(salesOrder.getSalesOrderId());

       ActivityMgt resultObj = activityMgtService.save(activityMgt);
   	    
   		return result; 
        }else {
        	throw new BadRequestAlertException("Purchase request is already created", ENTITY_NAME, "idexists");
        }
        } 
      }
    /**
     * {@code PUT  /sales-orders} : Updates an existing salesOrder.
     *
     * @param salesOrder the salesOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrder,
     * or with status {@code 400 (Bad Request)} if the salesOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salesOrder")
   // @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object updateSalesOrder(@RequestBody SalesOrder salesOrder) throws URISyntaxException {
        log.debug("REST request to update SalesOrder : {}", salesOrder);
        if (salesOrder.getSalesOrderId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
       
        Optional<SalesOrder> result=salesOrderService.findOne(salesOrder.getSalesOrderId());
        Float floatObj;
		floatObj = new Float(result.get().getSalesVersion());
		float incrver = floatObj;
		float f = incrver + 1;
		salesOrder.setSalesVersion(f);
		salesOrder.setSalesOrderId(null);
		salesOrder.setSalesOrderNumber(salesOrder.getSalesOrderNumber());
		for (SalesOrderProduct salesOrderProduct : salesOrder.getSalesOrderProduct()) 
		{
			salesOrderProduct.setSalesOrderProductId(null);
		}
		salesOrder.getShippingDetails().setShippingDetailsId(null);
		SalesOrder finalResult = salesOrderService.save(salesOrder);
		result.get().setStatus("Closed");
    	salesOrderService.save(result.get());
    	
    	 ActivityMgt activityMgt=new ActivityMgt();
 	    //activityMgt.setNotes("Sales Order is created");
 	    activityMgt.setStatus(salesOrder.getStatus());
 	    activityMgt.setTitle("Sales Order is Updated");
 	    activityMgt.setPriority("Medium");
 	    activityMgt.setReferenceId(salesOrder.getSalesOrderId());
        ActivityMgt resultObj = activityMgtService.save(activityMgt);
        
        
		/*if(result.get().getPrStatus().equals("No")){
	    	result.get().setPrStatus("Yes");
	    	result.get().setIsDelivered("Closed");
	    	salesOrderService.save(result.get());
	    	return customResponse.success("Sales order updated", finalResult);
	    }
		if(!result.get().getPrStatus().equals("No") && result.get().getPrStatus() != "No" ) {
			for (SalesOrderProduct salesOrderProductFinal : finalResult.getSalesOrderProduct()) {
				for (SalesOrderProduct salesOrderProduct : result.get().getSalesOrderProduct()) {
					if(salesOrderProductFinal.getProductId() == salesOrderProduct.getProductId()) {
						if(salesOrderProductFinal.getQuantity() > salesOrderProduct.getQuantity()) {
						    Long availablwQty= Math.subtractExact(salesOrderProductFinal.getQuantity() , salesOrderProduct.getQuantity());
						    salesOrderProductFinal.setQuantity(availablwQty);
						    salesOrderProductRepository.save(salesOrderProductFinal);
						    result.get().setIsDelivered("Closed");
					    	salesOrderService.save(result.get());
						}
						if(salesOrderProductFinal.getQuantity() < salesOrderProduct.getQuantity()) {
						    Long availablwQty= Math.subtractExact(salesOrderProductFinal.getQuantity() , salesOrderProduct.getQuantity());
						    salesOrderProductFinal.setQuantity(availablwQty);
						    salesOrderProductRepository.save(salesOrderProductFinal);
						    result.get().setIsDelivered("Closed");
					    	salesOrderService.save(result.get());
						}
					}
				}
		  }
			return customResponse.success("Sales order updated", finalResult);
		}*/
		
        return customResponse.success("Sales order updated", finalResult);

    }
 
    
    	public void createPr(SalesOrderProduct salesOrderProduct, SalesOrder salesOrder, Long availablwQty) throws URISyntaxException {
    		log.debug("REST request to get createPr : {}");
    		
    		BillOfMaterial billOfMaterial=billOfMaterialRepository.findByProductProductId(salesOrderProduct.getProductId(),salesOrderProduct.getBomVersion());
   	    	if(billOfMaterial !=null ) {
   	    	  if(billOfMaterial.getBomItem() !=null && !billOfMaterial.getBomItem().isEmpty()) {
   	    		 Long qty;
   		    	   for (BOMItem bOMItem : billOfMaterial.getBomItem()) {
   		    		   PurchaseRequest purchaseRequest = new PurchaseRequest();
   		    		   	if(availablwQty !=null) {
   		    		   		qty=bOMItem.getQuantity()*availablwQty;
   		    		    }else {
   		    		    	qty=bOMItem.getQuantity()*salesOrderProduct.getQuantity();
   		    		   }
   		    		purchaseRequest.setQuantity(qty);
   		    		purchaseRequest.setItemId(bOMItem.getItemId());
   		    		purchaseRequest.setItemName(bOMItem.getItemName());
   		    		purchaseRequest.setSalesOrderId(salesOrder.getSalesOrderId());
   		    		purchaseRequest.setProductName(salesOrderProduct.getProductName());
   		    		purchaseRequest.setUnitOfMeasurement(bOMItem.getUnitOfMeasurement());
   		    		purchaseRequest.setItemCode(bOMItem.getItemCode());
   		    		Object result1=purchaseRequestResource.createPurchaseRequest(purchaseRequest);	 
   		    		 
   		    	   }
   	    	  }
   	    	}
    	}
    
    	
    	  /* if(result.get().getPrStatus().equals("No")){
    	result.get().setPrStatus("Yes");
    	salesOrderService.save(result.get());
    }
    if(!result.get().getPrStatus().equals("No")) {
    	for (SalesOrderProduct salesOrderProductFinal : finalResult.getSalesOrderProduct()) {
			for (SalesOrderProduct salesOrderProduct : result.get().getSalesOrderProduct()) {
				System.out.println("@@@@@@@@@@@@#####"+salesOrderProduct.getSalesOrderProductId());
				System.out.println("@@@@@@@@@@@@@#@#"+salesOrderProductFinal.getSalesOrderProductId());
				if(salesOrderProductFinal.getSalesOrderProductId() == salesOrderProduct.getSalesOrderProductId()) {
					if(salesOrderProductFinal.getQuantity() > salesOrderProduct.getQuantity()) {
						System.out.println("@@@@@@@@@@@@"+salesOrderProductFinal.getQuantity());
						System.out.println("@@@@@@@@@@@@"+salesOrderProduct.getQuantity());
					    Long availablwQty= Math.subtractExact(salesOrderProductFinal.getQuantity() , salesOrderProduct.getQuantity());
					    salesOrderProductFinal.setQuantity(availablwQty);
					    System.out.println("@@@@@@@@@@@@"+availablwQty);
					    createPr(salesOrderProductFinal, finalResult,availablwQty );
					}
					if(salesOrderProductFinal.getQuantity() < salesOrderProduct.getQuantity()) {
						System.out.println("@@@@@@@@@@@@!!!!!!!!!!!!"+salesOrderProductFinal.getQuantity());
					    Long availablwQty= Math.subtractExact(salesOrderProductFinal.getQuantity() , salesOrderProduct.getQuantity());
					    salesOrderProductFinal.setQuantity(availablwQty);
					    System.out.println("@@@@@@@@@@@@!!!!!!!!!!"+availablwQty);
					    createPrForLess(salesOrderProductFinal, finalResult, availablwQty);
					}
				}else {
					 System.out.println("@@@@@@@@@@@@!!!!!!!!!!@!@!");
					 createPr(salesOrderProductFinal, finalResult, null);
				}
				
			}
		}
    }*/
    	public void createPrForLess(SalesOrderProduct salesOrderProduct, SalesOrder salesOrder, Long availablwQty) throws URISyntaxException {
    		log.debug("REST request to get createPr : {}");
    		
    		BillOfMaterial billOfMaterial=billOfMaterialRepository.findByProductProductId(salesOrderProduct.getProductId(),salesOrderProduct.getBomVersion());
   	    	if(billOfMaterial !=null ) {
   	    	  if(billOfMaterial.getBomItem() !=null && !billOfMaterial.getBomItem().isEmpty()) {
   	    		  
   		    	   for (BOMItem bOMItem : billOfMaterial.getBomItem()) {
   		    		   PurchaseRequest purchaseRequest = new PurchaseRequest();
   		    		    Long qty=bOMItem.getQuantity()*availablwQty;
   		    	
   		    		purchaseRequest.setQuantity(-qty);
   		    		purchaseRequest.setItemId(bOMItem.getItemId());
   		    		purchaseRequest.setItemName(bOMItem.getItemName());
   		    		purchaseRequest.setSalesOrderId(salesOrder.getSalesOrderId());
   		    		purchaseRequest.setProductName(salesOrderProduct.getProductName());
   		    		purchaseRequest.setUnitOfMeasurement(bOMItem.getUnitOfMeasurement());
   		    		purchaseRequest.setItemCode(bOMItem.getItemCode());
   		    		Object result1=purchaseRequestResource.createPurchaseRequest(purchaseRequest);	 
   		    		 
   		    	   }
   	    	  }
   	    	}
    	}
    /**
     * {@code GET  /sales-orders} : get all the salesOrders.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesOrders in body.
     */
	@GetMapping("/salesOrder")
	// @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllSalesOrders() {
		List<SalesOrder> result = salesOrderService.findAll();

		for (SalesOrder salesOrder : result) {
			List<SOPayment> sOPaymentList = soPaymentRepository.findBySalesOrderId(salesOrder.getSalesOrderId());
			salesOrder.setSoPayment(sOPaymentList);
		}

		if (result != null && !result.isEmpty()) {
			return customResponse.success("sales order found", result);
		} else {
			return customResponse.failure(404, "sales order not found");

		}
	}

    /**
     * {@code GET  /sales-orders/:id} : get the "id" salesOrder.
     *
     * @param id the id of the salesOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesOrder, or with status {@code 404 (Not Found)}.
     */
  

    @GetMapping("/salesOrder/{id}")
   // @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getSalesOrder(@PathVariable Long id) {
        log.debug("REST request to get SalesOrder : {}", id);
        Optional<SalesOrder> result=salesOrderService.findOne(id);
        
        List<SOPayment> sOPaymentList=soPaymentRepository.findBySalesOrderId(result.get().getSalesOrderId());
        result.get().setSoPayment(sOPaymentList);
        
        if(result !=null && result.isPresent()) {
        	return customResponse.success("sales order found", result);
        }else 
        {
        	return customResponse.failure(404, "sales order not found");
        }
    }
    
    
	@GetMapping("/salesOrderByPartyWise/{id}")
	// @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getSalesOrderByPartyWise(@PathVariable Long id) {
		log.debug("REST request to get SalesOrder by party Wise : {}", id);
		List<SalesOrder> result = salesOrderService.findByPartyId(id);

		if (result != null) {
			return customResponse.success("sales order found", result);
		} else {
			return customResponse.failure(404, "sales order not found");
		}
	}
    
	@PutMapping("/updateBomVersion")
	// @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object UpateSalesOrder(@RequestBody SalesOrder salesOrder) throws URISyntaxException {
		log.debug("REST request to save updateBomVersion : {}", salesOrder);
		if (salesOrder.getSalesOrderId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		SalesOrder result = salesOrderService.save(salesOrder);
		
		ActivityMgt activityMgt = new ActivityMgt();
		// activityMgt.setNotes("Sales Order is created");
		activityMgt.setStatus(result.getStatus());
		activityMgt.setTitle("Sales Order BOM Version is Updated");
		activityMgt.setPriority("Medium");
		activityMgt.setReferenceId(result.getSalesOrderId());
		ActivityMgt resultObj = activityMgtService.save(activityMgt);

		return customResponse.success("Sales order updated", result);
	}
	
	    @PatchMapping("/salesOrder/{Id}")
	    //@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	    public Object updateSO(@RequestBody List<SOPayment> sOPayment, @PathVariable Long Id) throws URISyntaxException {
	        log.debug("REST request to save SalesOrder : {}", sOPayment, Id);
	        Optional<SalesOrder> result=salesOrderService.findOne(Id);
	        if (result.get().getSalesOrderId() == null){
	        	throw new NotFoundException();
	        }
	        List<SOPayment> sOPaymentResult=soPaymentRepository.saveAll(sOPayment);
	        List<SOPayment> sOPaymentList=soPaymentRepository.findBySalesOrderId(result.get().getSalesOrderId());
	        sOPaymentList.addAll(sOPaymentResult);
	        result.get().setSoPayment(sOPaymentList);
		    SalesOrder finalResult = salesOrderService.save(result.get());
		    
			ActivityMgt activityMgt = new ActivityMgt();
			// activityMgt.setNotes("Sales Order is created");
			activityMgt.setStatus(finalResult.getStatus());
			activityMgt.setTitle("Sales Order BOM Version is Updated");
			activityMgt.setPriority("Medium");
			activityMgt.setReferenceId(finalResult.getSalesOrderId());
			ActivityMgt resultObj = activityMgtService.save(activityMgt);
		    
	        return customResponse.success("Sales order created", finalResult);
	    }

	
	    
 /*   @GetMapping("/salesOdrBom/{id}")
    public Object getSalesOrderBOM(@PathVariable Long id) {
        log.debug("REST request to get salesOdrBom : {}", id);
       
        List<CategoryGroup> result=salesOrderRepository.jointmultipleTable(id);
		return result;
        
    }*/
    /**
     * {@code DELETE  /sales-orders/:id} : delete the "id" salesOrder.
     *
     * @param id the id of the salesOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
 
  
@PatchMapping("/checkStatus/{salesOrderId}")
public Object status(@PathVariable Long salesOrderId,@RequestParam("isDelivered") String isDelivered){	
	  SalesOrder result=salesOrderService.findOneBySalesOrderId(salesOrderId);
	  if (result.getSalesOrderId() == null){
		  throw new NotFoundException();
      }
	  if(result.getStatus()==null){
		  throw new NotFoundException();
	  }
	 result.setStatus(isDelivered);
	 SalesOrder finalResult= salesOrderService.save(result);
	 
		ActivityMgt activityMgt = new ActivityMgt();
		// activityMgt.setNotes("Sales Order is created");
		activityMgt.setStatus(finalResult.getStatus());
		activityMgt.setTitle("Sales Order status changed to"+ ""+finalResult.getStatus());
		activityMgt.setPriority("Medium");
		activityMgt.setReferenceId(finalResult.getSalesOrderId());
		ActivityMgt resultObj = activityMgtService.save(activityMgt);
		
	return finalResult;
  
}

	// Active so month wise //Inactive so month wise
	@GetMapping("/openNClosedSoMnthWise")
	public Object getActiveSoByMnthWise() 
	{
		Map<String, Object> toReturn = new HashMap<String, Object>();
		Object result = salesOrderService.getOpenSoByMnthWise();
		toReturn.put("openSalesOrder", result);

		Object result1 = salesOrderService.getClosedSoByMnthWise();
		toReturn.put("closedSalesOrder", result1);

		if (toReturn != null) {
			return customResponse.success("result  found", toReturn);

		} else {
			return customResponse.failure(404, " result not found");

		}
	}
	//Get count of active,shipped etc .so
		@GetMapping("/statusWiseCountSO")
		public Object getStatusWiseSoCount() 
		{
			
			  //ArrayList<Map<String, Object>>  result=salesOrderService.findByStatus();
			  //List<SalesOrder> result = salesOrderService.findByStatus();
			  Map<String, Object>result = salesOrderService.findByStatus();
		        if (result != null && !result.isEmpty()) {
					SygnetoResponse res = new SygnetoResponse();
					res.setStatusCode(404);
					res.setStatus("Sales Order  found");
					res.setData(result);
					return new ResponseEntity<>(res, HttpStatus.OK);
				} else {
					SygnetoResponse res = new SygnetoResponse();
					res.setStatusCode(404);
					res.setStatus("Sales Order not found");
					res.setData(null);
					return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
				}
		}
	
@DeleteMapping("/salesOrder/{id}")
// @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
   public Object deleteSalesOrder(@PathVariable Long id) {
   	 Optional<SalesOrder> result = salesOrderService.findOne(id);
        if(result!=null) {
        	try {
               salesOrderService.delete(id);
				return customResponse.success("sales order deleted", null);
			} catch (Exception e) {
				return customResponse.failure(400, "sales order not found");
			}
        	 
       }else {
    	  return customResponse.failure(404, "sales order not deleted");
       	
       }
 }

/*
@GetMapping("/salesOrderMonth")
// @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
public Object getSalesOrderByPartyWise() {
	String status="Closed";

	ArrayList<Map<String, Object>> result = salesOrderService.findByIsDeliveredOpen(status);
	//List<SalesOrder> result = salesOrderService.findByIsDeliveredClose(a);
	if (result != null) {
		return customResponse.success("sales order found", result);
	} else {
		return customResponse.failure(404, "sales order not found");
	}
}*/

}
