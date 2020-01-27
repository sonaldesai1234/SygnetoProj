package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.PurchaseOrder;
import com.sygneto.domain.PurchaseOrderItems;
import com.sygneto.service.PurchaseOrderItemsService;
import com.sygneto.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.PurchaseOrderOld}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderItemsResource {

	 private static final String ENTITY_NAME = "purchaseOrdersItems";
    private final Logger log = LoggerFactory.getLogger(PurchaseOrderItemsResource.class);

    private final PurchaseOrderItemsService purchaseOrderService;
    @Autowired
	CustomResponse customeResponce;

    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    public PurchaseOrderItemsResource(PurchaseOrderItemsService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("/purchaseOrderItem")
    public Object createItem(@RequestBody PurchaseOrderItems purchaseOrderItems) throws URISyntaxException {
        log.debug("REST request to save purchaseOrders : {}", purchaseOrderItems);
        if (purchaseOrderItems.getPurchaseOrderItemId() != null) {
            throw new BadRequestAlertException("A new purchaseOrders cannot already have an ID", ENTITY_NAME, "idexists");
        }
     // Calculate Purchase Order Item total amount based on its quantity and unit price
     if(purchaseOrderItems.getItemUnitPrice()>0.0)
        {
        	float totalAmount=purchaseOrderItems.getItemUnitPrice()*purchaseOrderItems.getItemQuantity();
        	purchaseOrderItems.setTotalAmount(totalAmount);
        }
        PurchaseOrderItems result = purchaseOrderService.save(purchaseOrderItems);
        return customeResponce.success("Purchase Order Item created", result);
    }

   
    @PutMapping("/purchaseOrderItem")
    public Object updateItem(@RequestBody PurchaseOrderItems purchaseOrderItems) throws URISyntaxException {
        log.debug("REST request to update purchaseOrders : {}", purchaseOrderItems);
        if (purchaseOrderItems.getPurchaseOrderItemId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
       
        	float totalAmount=purchaseOrderItems.getItemUnitPrice()*purchaseOrderItems.getItemQuantity();
        	purchaseOrderItems.setTotalAmount(totalAmount);	
        
         PurchaseOrderItems result = purchaseOrderService.save(purchaseOrderItems);
        return customeResponce.success("Purchase Order Item Updated", result);
        
    }
    
    /**
     * {@code GET  /purchase-orders} : get all the purchaseOrders.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseOrders in body.
     */
    @GetMapping("/purchaseOrderItem")
    public Object getAllPurchaseOrders() {
        log.debug("REST request to get all PurchaseOrders");
        List<PurchaseOrderItems> result= purchaseOrderService.findAll();
        if(result !=null && !result.isEmpty()) {
        	return customeResponce.success("Purchase Order Item found", result);
        	
        }else {
        	return customeResponce.failure(404, "Purchase Order Item  not found");
        	 
        }
    }
    
    /**
     * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrder.
     *
     * @param id the id of the purchaseOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchaseOrderItem/{id}")
    public Object getPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrder : {}", id);
        Optional<PurchaseOrderItems> result = purchaseOrderService.findOne(id);
        if(result !=null && result.isPresent()) {
        	return customeResponce.success("Purchase Order Item found", result);
        	
        }else 
        {
        	return customeResponce.failure(404, "Purchase Order Item  not found");
        }
     }
    
    @DeleteMapping("/purchaseOrderItem/{id}")
    public Object deleteItem(@PathVariable Long id) {
        log.debug("REST request to delete item : {}", id);
        Optional<PurchaseOrderItems> result = purchaseOrderService.findOne(id);
        if(result !=null  && result.isPresent()) {
        	 if(id !=null) {
        		 purchaseOrderService.delete(id);
        	     return customeResponce.success("Purchase Order Item deleted", null);
            	
              }else {
            	  return customeResponce.failure(404, "Purchase Order Item not found");
              	
              }
       }else
       {
    	  return customeResponce.failure(404, "Purchase Order Item not deleted");
       	
       }
        
    }
}
