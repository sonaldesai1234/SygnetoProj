package com.sygneto.web.rest;


import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;

import java.time.Instant;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemOutward;
import com.sygneto.domain.Party;
import com.sygneto.domain.PurchaseOrder;
import com.sygneto.domain.PurchaseOrderItems;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.InverdItemsRepository;
import com.sygneto.service.ItemService;
import com.sygneto.service.PurchaseOrderItemsService;
import com.sygneto.service.PurchaseOrderService;
import com.sygneto.service.PurchaseRequestService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing {@link com.sygneto.domain.PurchaseOrderOld}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderResource {

	private final Logger log = LoggerFactory.getLogger(PurchaseOrderResource.class);

	@Autowired
	PurchaseOrder purchaseOrder;

	
	@Autowired
	CustomResponse customeResponce;
	private static final String ENTITY_NAME = "purchaseOrder";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	 @Autowired
	    PurchaseRequestResource purchaseRequestResource;
	 
	 @Autowired
	    InverdItemsRepository inverdItemsRepository;
	    
	 @Autowired
	 ItemService itemService;
	 
	@Autowired
	private PurchaseRequestService purchaseRequestService;
	
	@Autowired
	PurchaseOrderItemsService purchaseOrderItemsService;
	
	private final PurchaseOrderService purchaseOrderService;

	public PurchaseOrderResource(PurchaseOrderService purchaseOrderService) {
		this.purchaseOrderService = purchaseOrderService;
	}

	/**
	 * {@code POST  /purchase-orders} : Create a new purchaseOrder.
	 *
	 * @param purchaseOrder
	 *            the purchaseOrder to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new purchaseOrder, or with status {@code 400 (Bad Request)}
	 *         if the purchaseOrder has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@PostMapping("/purchaseOrder")
	public Object createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) throws URISyntaxException {
		log.debug("REST request to save PurchaseOrder : {}", purchaseOrder);

	
		for (PurchaseOrderItems purchaseOrderItemsObj : purchaseOrder.getPurchaseOrderItems()) {
			if(purchaseOrderItemsObj.getPurchaseRequestId() ==null) {
				PurchaseRequest purchaseRequest=new PurchaseRequest();
				purchaseRequest.setItemId(purchaseOrderItemsObj.getItemId());
				purchaseRequest.setItemName(purchaseOrderItemsObj.getItemName());
				purchaseRequest.setQuantity(purchaseOrderItemsObj.getItemQuantity());
				purchaseRequest.setUnitOfMeasurement(purchaseOrderItemsObj.getUnitOfMeasurment());
				purchaseRequest.setSalesOrderId(0L);
				PurchaseRequest purchaseRequestObj=purchaseRequestResource.createPurchaseRequest(purchaseRequest);
				
				Optional<Item> result = itemService.findOne(purchaseOrderItemsObj.getItemId());		
				result.get().setStatus("PO Raised");		
				System.out.println("))))))))"+result);
				itemService.save(result.get());
				
				purchaseOrderItemsObj.setPurchaseRequestId(purchaseRequestObj.getPurchaseRequestId());
				purchaseOrderItemsObj.setSalesOrderId(0L);
			}	
		}
		PurchaseOrder result = purchaseOrderService.save(purchaseOrder);
		System.out.println("@@@@@@@"+result.toString());
		for (PurchaseOrderItems PurchaseOrderItemsObj : result.getPurchaseOrderItems()) {
			Optional<PurchaseRequest> purchaseRequest = purchaseRequestService.findOne(PurchaseOrderItemsObj.getPurchaseRequestId());
			 if(purchaseRequest.get().getQuantity() == PurchaseOrderItemsObj.getItemQuantity()) {
				  purchaseRequest.get().setStatus("Po Raised");
				  purchaseRequestService.save(purchaseRequest.get());
			  }else {
				  Long availablwQty= Math.subtractExact(purchaseRequest.get().getQuantity(),PurchaseOrderItemsObj.getItemQuantity());
				  purchaseRequest.get().setQuantity(availablwQty);
				  purchaseRequestService.save(purchaseRequest.get());
			  }
		}
		return customeResponce.success("purchaseOrder created", result);
	}

	/**
	 * {@code PUT  /purchase-orders} : Updates an existing purchaseOrder.
	 *
	 * @param purchaseOrder
	 *            the purchaseOrder to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated purchaseOrder, or with status {@code 400 (Bad Request)}
	 *         if the purchaseOrder is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the purchaseOrder couldn't be
	 *         updated.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@PutMapping("/purchaseOrder")
	public Object updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) throws URISyntaxException {
		log.debug("REST request to update PurchaseOrder : {}", purchaseOrder);
		if (purchaseOrder.getPurchaseOrderId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PurchaseOrder result = purchaseOrderService.save(purchaseOrder);
		return customeResponce.success("purchase order updated", result);
	}
	
	
	/**
	 * {@code GET  /purchase-orders} : get all the purchaseOrders.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of purchaseOrders in body.
	 */
	
	
	@GetMapping("/purchaseOrder")
	public Object getAllPurchaseOrders() {
		List<PurchaseOrder> result = purchaseOrderService.findAll();
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");
		}
	}
	

	/**
	 * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrder.
	 *
	 * @param id
	 *            the id of the purchaseOrder to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the purchaseOrder, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/purchaseOrder/{id}")
	public Object getPurchaseOrder(@PathVariable Long id) {
		log.debug("REST request to get PurchaseOrder : {}", id);
		Optional<PurchaseOrder> result = purchaseOrderService.findOne(id);
		for (PurchaseOrderItems purchaseOrderItems : result.get().getPurchaseOrderItems()) {
			Long inwardQty=inverdItemsRepository.findBypr(purchaseOrderItems.getPurchaseRequestId());
			purchaseOrderItems.setAlreadyReceived(inwardQty);
		}

		if (result != null && result.isPresent()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	
    @ApiOperation(value="Get purchase Orders of last month")
	@GetMapping("/purchaseOrderOfLastMonth")
	public Object getPurchaseOrderOfLastMonth() {
		log.debug("REST request to get getPurchaseOrderOfLastMonth : {}");
		List<PurchaseOrder> result = purchaseOrderService.findLastMonthPO();
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	
    @ApiOperation(value="Get total Purchase Orders created by party in every months ")
    @GetMapping("/purchaseOrderByPartyNMonthWiseCount")
	public Object getpurchaseOrderByPartyNMonthWiseCount() {
		log.debug("REST request to get purchaseOrderOFPartyNMonthWiseCount : {}");
		ArrayList<Map<String, Object>> result = purchaseOrderService.findByPartyNMonthWiseCount();
		//List<CustomQueryForSB> list=result.stream().map(obj->(CustomQueryForSB)obj).collect(Collectors.toList());
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}

	//find  avg(podate-inwardentrydate)
    @ApiIgnore
	@GetMapping("/purchaseOrderByPartyAvgDateDiff")
	public Object getpurchaseOrderByPartyAvgDateDiff() {
		log.debug("REST request to get purchaseOrderOFPartyNMonthWiseCount : {}");
		ArrayList<Map<String, Object>> result = purchaseOrderService.findByPartyAvgDateDiff();
		//List<CustomQueryForSB> list=result.stream().map(obj->(CustomQueryForSB)obj).collect(Collectors.toList());
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	
    @ApiOperation(value="Get total open Purchase Orders ")
	@GetMapping("/openPo")
	public Object getOpenPo() {
		log.debug("REST request to get openPo : {}");
		List<String> str=new ArrayList<>();
		str.add("Active");
		str.add("active");
		
		List<PurchaseOrder> result = purchaseOrderService.findByStatusIn(str);
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	
	
	//filter by po no
    @ApiOperation(value="Get Purchase Order by Purchase Orders number ")
	@GetMapping("/purchaseOrderNumber/{purchaseOrderNumber}")
	public Object getPurchaseOrderPONumber(@PathVariable String purchaseOrderNumber) {
		log.debug("REST request to get getPurchaseOrderOfLastMonth : {}",purchaseOrderNumber);
		List<PurchaseOrder> result = purchaseOrderService.findByPurchaseOrderNumber(purchaseOrderNumber);
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	
	
	//filter by so id
    @ApiOperation(value="Get all Purchase Orders as per Sales Order Id ")
	@GetMapping("/purchaseOrderBySalesOrderId/{salesOrderId}")
	public Object getPurchaseOrdersalesOrderId(@PathVariable Long salesOrderId) {
		log.debug("REST request to get getPurchaseOrderOfLastMonth : {}",salesOrderId);
		List<PurchaseOrder> result = purchaseOrderService.findByPurchaseOrderItemsSalesOrderId(salesOrderId);
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	
	
	//filter by company/supplier name
    @ApiOperation(value="Get all Purchase Orders as per Party Id")
	@GetMapping("/purchaseOrderPartyId/{partyId}")
	public Object getPurchaseOrderPartyId(@PathVariable Long partyId) {
		List<PurchaseOrder> result = purchaseOrderService.findByPartyId(partyId);
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	
	
	//filter by po id or and so id
    @ApiOperation(value="Get Purchase Orders as per Sales Order Id or Purchase Order Id")
	@GetMapping("/purchaseOrderAll")
	public Object getPurchaseOrderAll(@RequestParam(value="purchaseOrderId",required=false) Long purchaseOrderId,@RequestParam(value="salesOrderId",required=false) Long salesOrderId) {
		List<PurchaseOrder> result = purchaseOrderService.findByPurchaseOrderIdOrSalesOrderId(purchaseOrderId,salesOrderId);
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("purchase order found", result);

		} else {
			return customeResponce.failure(404, "purchase order not found");

		}
	}
	

	//filter by item name
    	@ApiOperation(value="Get Purchase Orders as per Item Id")
		@GetMapping("/purchaseOrderItemId/{itemId}")
		public Object getPurchaseOrderItemId(@PathVariable Long itemId) {
			log.debug("REST request to get getPurchaseOrderOfLastMonth : {}",itemId);
			List<PurchaseOrder> result = purchaseOrderService.findByPurchaseOrderItemsItemId(itemId);
			if (result != null && !result.isEmpty()) {
				return customeResponce.success("purchase order found", result);

			} else {
				return customeResponce.failure(404, "purchase order not found");

			}
		}

	/**
	 * {@code DELETE  /purchase-orders/:id} : delete the "id" purchaseOrder.
	 *
	 * @param id
	 *            the id of the purchaseOrder to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */    
    	@ApiOperation(value="Get all Purchase Orders created between two dates")
		@GetMapping("/purchaseOrderBetween/{startDate}/{endDate}")
		public Object getPurchaseOrderBet(@PathVariable Instant startDate,@PathVariable Instant endDate) {
			
			List<PurchaseOrder> result = purchaseOrderService.findAllByCreatedDateBetween(startDate,endDate);
			
			if (result != null && !result.isEmpty()) {
				return customeResponce.success("purchase order found", result);

			} else {
				return customeResponce.failure(404, "purchase order not found");

			}
		}
	
	
    @ApiOperation(value="Delete specific Purchase Orders as per purchase order Id")
	@DeleteMapping("/purchaseOrder/{id}")
	public Object deletePurchaseOrder(@PathVariable Long id) {
		Optional<PurchaseOrder> result = purchaseOrderService.findOne(id);
		if (result != null && result.isPresent()) {
			if (id != null) {
				purchaseOrderService.delete(id);
				return customeResponce.success("purchase order deleted", null);

			} else {
				return customeResponce.failure(404, "purchase order not found");

			}
		} else {
			return customeResponce.failure(404, "purchase order not deleted");

		}
	}

	  //Active po by month wise //Inactive Po By month wise
    @GetMapping("/openNClosedPoByMnthWise")
    @ApiOperation(value="Get all open Purchase Orders per month ")
    public Object getActivePoByMnthWise() {
    	Map<String, Object> toReturn = new HashMap<String, Object>();
    	Object result = purchaseOrderService.getActivePoByMnthWise();
    	toReturn.put("openPurchaseOrder", result);
    	
    	Object  result1 = purchaseOrderService.getClosedPoByMnthWise();
    	toReturn.put("closedPurchaseOrder", result1);
    	
        if (toReturn != null ) {
			return customeResponce.success("result  found", toReturn);

		} else {
			return customeResponce.failure(404, " result not found");

		}
    }
    @GetMapping("/statusWiseCountPO")
	public Object getStatusWisePoCount() 
	{
		
		  //ArrayList<Map<String, Object>>  result=salesOrderService.findByStatus();
		  //List<SalesOrder> result = salesOrderService.findByStatus();
		  Map<String, Object>result = purchaseOrderService.findByStatus();
	        if (result != null && !result.isEmpty()) {
				SygnetoResponse res = new SygnetoResponse();
				res.setStatusCode(404);
				res.setStatus("Purchase Order  found");
				res.setData(result);
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else {
				SygnetoResponse res = new SygnetoResponse();
				res.setStatusCode(404);
				res.setStatus("Purchase Order not found");
				res.setData(null);
				return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
			}
	}

}
