package com.sygneto.web.rest;

import com.sygneto.domain.BOMItem;
import com.sygneto.domain.BillOfMaterial;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemInward;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.domain.PurchaseRequestItem;
import com.sygneto.domain.SalesOrder;
import com.sygneto.domain.SalesOrderProduct;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.InverdItemsRepository;
import com.sygneto.repository.ItemOutwardRepository;
import com.sygneto.service.PurchaseRequestService;
import com.sygneto.service.SalesOrderService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.sygneto.domain.PurchaseRequest}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseRequestResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseRequestResource.class);

    private static final String ENTITY_NAME = "purchaseRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    CustomResponse customResponse;
    
    @Autowired
    InverdItemsRepository inverdItemsRepository;
    
    @Autowired
    ItemOutwardRepository itemOutwardRepository;
    
    private final PurchaseRequestService purchaseRequestService;

    @Autowired
    SalesOrderService salesOrderService;
    
    public PurchaseRequestResource(PurchaseRequestService purchaseRequestService) {
        this.purchaseRequestService = purchaseRequestService;
    }

    /**
     * {@code POST  /purchase-requests} : Create a new purchaseRequest.
     *
     * @param purchaseRequest the purchaseRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseRequest, or with status {@code 400 (Bad Request)} if the purchaseRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    
    @PostMapping("/purchaseRequest")
    public PurchaseRequest createPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) throws URISyntaxException {
        log.debug("REST request to save PurchaseRequest : {}", purchaseRequest);
        if (purchaseRequest.getPurchaseRequestId() != null){
            throw new BadRequestAlertException("A new purchaseRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }

	    PurchaseRequest result = purchaseRequestService.save(purchaseRequest);
	   /* Optional<SalesOrder> soResult=salesOrderService.findOne(result.getSalesOrderId());
	    soResult.get().setIsDelivered("InProcess");
	    SalesOrder so = salesOrderService.save(soResult.get());*/
        //return customResponse.success("Purchase Request created", result);
	    return result;
    }
    
    @PostMapping("/purchaseRequestAll")
    public Object createPurchaseRequestList(@RequestBody List<PurchaseRequest> purchaseRequest) throws URISyntaxException {
        log.debug("REST request to save PurchaseRequest : {}", purchaseRequest);
        for (PurchaseRequest purchaseRequest2 : purchaseRequest) {
        	 if (purchaseRequest2.getPurchaseRequestId() != null)
             {
                 throw new BadRequestAlertException("A new purchaseRequest cannot already have an ID", ENTITY_NAME, "idexists");
             }
		}
	    List<PurchaseRequest> result = purchaseRequestService.saveAll(purchaseRequest);
	    Optional<SalesOrder> soResult=salesOrderService.findOne(result.get(0).getSalesOrderId());
	    soResult.get().setStatus("InProcess");
	    SalesOrder so = salesOrderService.save(soResult.get());
        return customResponse.success("Purchase Request created", result);
    }

    /**
     * {@code PUT  /purchase-requests} : Updates an existing purchaseRequest.
     *
     * @param purchaseRequest the purchaseRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseRequest,
     * or with status {@code 400 (Bad Request)} if the purchaseRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchaseRequest")
    public Object updatePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) throws URISyntaxException {
        log.debug("REST request to update PurchaseRequest : {}", purchaseRequest);
        if (purchaseRequest.getPurchaseRequestId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
	    PurchaseRequest result = purchaseRequestService.save(purchaseRequest);
        return customResponse.success("Purchase Request updated", result);
    }

    /**
     * {@code GET  /purchase-requests} : get all the purchaseRequests.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseRequests in body.
     */
    @ApiOperation(value="Get all purchase requests")
    @GetMapping("/purchaseRequest")
    public Object getAllPurchaseRequests() {
        log.debug("REST request to get all PurchaseRequests");
        String status="active";
        List<PurchaseRequest> result=purchaseRequestService.findAllByStatus(status);

        for (PurchaseRequest purchaseRequest : result) {
        		 Long inwardQty = inverdItemsRepository.getSumReceived(purchaseRequest.getItemId());
	    		 Long outwardQty =itemOutwardRepository.getSumReceived(purchaseRequest.getItemId());
	    		   if(inwardQty != null && outwardQty != null) {
	    		   // Long availablwQty= (Long)qty - (Long)inwardQty;
	    		    Long availablwQty= Math.subtractExact(inwardQty , outwardQty);
	    		    purchaseRequest.setItemInStock(availablwQty);
	    		   }else {
	    			   purchaseRequest.setItemInStock(inwardQty);
	    		   }
	    		  if(inwardQty == null && outwardQty == null) {
	    			  purchaseRequest.setItemInStock(0L);
	    		  }
		}
        if(result !=null&& !result.isEmpty()) {
        	return customResponse.success("Purchase Request found", result);
        }else 
        {
        	return customResponse.failure(404, "Purchase Request not found");
        }
        
    }
  

    /**
     * {@code GET  /purchase-requests/:id} : get the "id" purchaseRequest.
     *
     * @param id the id of the purchaseRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseRequest, or with status {@code 404 (Not Found)}.
     */
    @ApiOperation(value="Get all purchase requests as per purchase requests id")
    @GetMapping("/purchaseRequest/{id}")
    public Object getPurchaseRequest(@PathVariable Long id) {
        log.debug("REST request to get PurchaseRequest : {}", id);
        Optional<PurchaseRequest> result = purchaseRequestService.findOne(id);
        if (result != null && result.isPresent()) {
			return customResponse.success("Purchase Request found", result);

		} else {
			return customResponse.failure(404, "Purchase Request not found");

		}
    }
    @ApiOperation(value="Get all purchase requests exists")
    @GetMapping("/purchaseRequestCount")
    public Object getPurchaseRequestCount() {
        ArrayList<Map<String, Object>>  result=purchaseRequestService.countOpenClosePR();
        if (result != null && !result.isEmpty()) {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request  found");
			res.setData(result);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request not found");
			res.setData(null);
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
    }
    
//filter by soid for so admin
    @ApiOperation(value="Get all purchase requests as per sales order id")
    @GetMapping("/purchaseRequestSalesOrder/{salesOrderId}")
    public Object getPurchaseRequestSalesOrderId(@PathVariable Long salesOrderId) {
        log.debug("REST request to get PurchaseRequest : {}", salesOrderId);
        List<PurchaseRequest> result = purchaseRequestService.findBySalesOrderId(salesOrderId);
        if (result != null && !result.isEmpty()) {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request  found");
			res.setData(result);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request not found");
			res.setData(null);
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
    }
    //PR Open for how many days
    @ApiOperation(value="Get for how many days purchase requests open")
    @GetMapping("/purchaseRequestDays")
    public Object getAllPurchaseRequestsDays() {
        log.debug("REST request to get all PurchaseRequests");
       
        ArrayList<Map<String, Object>>  result=purchaseRequestService.findTotalDaysPROpen();

        if (result != null && !result.isEmpty()) {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request  found");
			res.setData(result);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request not found");
			res.setData(null);
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
    }
    // filter by itemname for inventory admin
    @ApiOperation(value="Get all purchase requests as per item name")

    @GetMapping("/purchaseRequestItemName/{itemName}")
    public Object getPurchaseRequestItemName(@PathVariable String itemName) {
        log.debug("REST request to get PurchaseRequest : {}", itemName);
        List<PurchaseRequest> result = purchaseRequestService.findByItemName(itemName);
        if (result != null && !result.isEmpty()) {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request  found");
			res.setData(result);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("Purchase Request not found");
			res.setData(null);
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
    }
    
    @GetMapping("/purchaseRequestAll")
    public Object getPurchaseRequestSearch(@RequestParam(value="salesOrderId",required=false) Long salesOrderId,@RequestParam(value="itemId",required=false) Long itemId)
    {
    	log.debug("REST request to get PurchaseRequest : {}", itemId);
        List<PurchaseRequest> result = purchaseRequestService.findBySalesOrderIdOrItemId(salesOrderId,itemId);
      
       if (result != null && !result.isEmpty()) {
			return customResponse.success("purchase order found", result);

		} else {
			return customResponse.failure(404, "purchase order not found");

		}
       
    }
    /**
     * {@code DELETE  /purchase-requests/:id} : delete the "id" purchaseRequest.
     *
     * @param id the id of the purchaseRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
   
    
    @DeleteMapping("/purchaseRequest/{id}")
    public Object deletePurchaseRequest(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseRequest : {}", id);
        Optional<PurchaseRequest> result = purchaseRequestService.findOne(id);
        
        if(result.isPresent()) {
        	try {
        		 purchaseRequestService.delete(id);
				return customResponse.success("Purchase Request deleted", null);
			} catch (Exception e) {
				return customResponse.failure(400, "Purchase Request not deleted");
			}
        	 
       }else {
    	   
    	  return customResponse.failure(404, "Purchase Request not found");
       	
       }
       
    }
    
    //Active po by month wise //Inactive Po By month wise
    @ApiOperation(value="Get all purchase requests per month")
    @GetMapping("/openNClosedPRByMnthWise")
    public Object getActivePoByMnthWise() {
    	Map<String, Object> toReturn = new HashMap<String, Object>();
    	Object result = purchaseRequestService.getActivePRByMnthWise();
    	toReturn.put("openPurchaseRequest", result);
    	Object result1 = purchaseRequestService.getClosedPRByMnthWise();
    	toReturn.put("closedPurchaseRequest", result1);
    	
        if (toReturn != null ) {
			return customResponse.success("result  found", toReturn);
        }else {
			return customResponse.failure(404, " result not found");

		}
    
}
}
