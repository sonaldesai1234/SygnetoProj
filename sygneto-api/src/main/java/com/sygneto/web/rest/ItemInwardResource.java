package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.checkerframework.checker.index.qual.GTENegativeOne;
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
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.BOMItem;
import com.sygneto.domain.BillOfMaterial;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.InverdItems;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemInward;
import com.sygneto.domain.ItemOutward;
import com.sygneto.domain.PurchaseOrder;
import com.sygneto.domain.PurchaseOrderItems;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.repository.InverdItemsRepository;
import com.sygneto.repository.PurchaseOrderRepository;
import com.sygneto.service.ItemInwardService;
import com.sygneto.service.ItemOutwardService;
import com.sygneto.service.ItemService;
import com.sygneto.service.PurchaseOrderService;
import com.sygneto.service.PurchaseRequestService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link com.sygneto.domain.ItemInward}.
 */
@RestController
@RequestMapping("/api")
public class ItemInwardResource {

	private final Logger log = LoggerFactory.getLogger(ItemInwardResource.class);

	private static final String ENTITY_NAME = "itemInward";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ItemInwardService itemInwardService;
	
	@Autowired
	PurchaseOrderRepository PurchaseOrderRepository;

	@Autowired
	PurchaseOrderService purchaseOrderService;
	
	@Autowired
	ItemOutwardService itemOutwardService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	PurchaseRequestService purchaseRequestService;
	
	@Autowired
	InverdItemsRepository inverdItemsRepository;

	public ItemInwardResource(ItemInwardService itemInwardService) {
		this.itemInwardService = itemInwardService;
	}
	@Autowired
	CustomResponse customeResponce;

	/**
	 * {@code POST  /item-inwards} : Create a new itemInward.
	 *
	 * @param itemInward
	 *            the itemInward to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new itemInward, or with status {@code 400 (Bad Request)} if
	 *         the itemInward has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@ApiOperation(value="Create Item Inward")
	@PostMapping("/itemInward")
	public Object createItemInward(@RequestBody ItemInward itemInward) throws URISyntaxException {
		log.debug("REST request to save ItemInward : {}", itemInward);
		if (itemInward.getItemInwardId() != null) {
			throw new BadRequestAlertException("A new itemInward cannot already have an ID", ENTITY_NAME, "idexists");
		}
          ItemInward result = itemInwardService.save(itemInward);
          List<Long> value2=new ArrayList<>();
          
          for (InverdItems inverdItemsObj : result.getInverdItems()) {
        	  Long value=itemInwardService.checkStatusOfPo(inverdItemsObj.getPurchaseRequestId());
        	  if(value == 0L ||  value == null) {
        		  Optional<PurchaseRequest> resultObj = purchaseRequestService.findOne(inverdItemsObj.getPurchaseRequestId());
        		  resultObj.get().setStatus("Closed");
        		  PurchaseRequest result1 = purchaseRequestService.save(resultObj.get());
        	  }

         Optional<PurchaseOrder> purchaseOrderObj = purchaseOrderService.findOne(result.getPurchaseOrder().getPurchaseOrderId());
         purchaseOrderObj.get().setStatus("closed");
         PurchaseOrder result1 = purchaseOrderService.save(purchaseOrderObj.get());
        	  
		}
          
		return customeResponce.success("Item in inward added successfully", result);
	}

	/**
	 * {@code PUT  /item-inwards} : Updates an existing itemInward.
	 *
	 * @param itemInward
	 *            the itemInward to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated itemInward, or with status {@code 400 (Bad Request)} if
	 *         the itemInward is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the itemInward couldn't be
	 *         updated.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@ApiOperation(value="Update Item Inward")
	@PutMapping("/itemInward")
	public Object updateItemInward(@RequestBody ItemInward itemInward) throws URISyntaxException {
		log.debug("REST request to update ItemInward : {}", itemInward);
		if (itemInward.getItemInwardId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		  //Optional<ItemInward> result = itemInwardService.findOne(itemInward.getItemInwardId());
		  
           ItemInward result1 = itemInwardService.save(itemInward);
          /* for (InverdItems inverdItems : result1.getInverdItems()) {
        	   for (InverdItems InverdItems1 : result.get().getInverdItems()) {
				if(inverdItems.getInverdItemid() == InverdItems1.getInverdItemid()) {
					inverdItems.setAlreadyReceived(inverdItems.getQuantityReceived() + InverdItems1.getAlreadyReceived());
		         	inverdItems.setQuantityReceived(0L);
				}else {
					inverdItems.setAlreadyReceived(inverdItems.getQuantityReceived());
		        	  inverdItems.setQuantityReceived(0L);
				}
			}
 		}*/
          // ItemInward result2 = itemInwardService.save(itemInward);
		return customeResponce.success("Item inward Updated Successfully", result1);
	}

	/**
	 * {@code GET  /item-inwards} : get all the itemInward.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of itemInward in body.
	 */
	@ApiOperation(value="Get all Item Inward")
	@GetMapping("/itemInward")
	public Object getAllItemInwards() {
		log.debug("REST request to get all ItemInwards");
		List<ItemInward> result = itemInwardService.findAll();
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("Item inward found", result);
		} else {
			return customeResponce.failure(404, "Item inward Not found");
		}
	}
	
	/*@GetMapping("/itemInward")
	public Object getAllItemInwards() {
		log.debug("REST request to get all ItemInwards");
		List<ItemInward> result = itemInwardService.findAllQuantityByItemId();
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("Item inward found", result);
		} else {
			return customeResponce.failure(404, "Item inward Not found");
		}
	}*/

	/**
	 * {@code GET  /item-inwards/:id} : get the "id" itemInward.
	 *
	 * @param id
	 *            the id of the itemInward to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the itemInward, or with status {@code 404 (Not Found)}.
	 */
	@ApiOperation(value="Get Item Inward by Item Inward Id")
	@GetMapping("/itemInward/{id}")
	public Object getItemInward(@PathVariable Long id) {
		log.debug("REST request to get ItemInward : {}", id);
		Optional<ItemInward> result = itemInwardService.findOne(id);
		if (result.isPresent()) {
			return customeResponce.success("Item inward  found", result);
		} else {
			return customeResponce.failure(404, "Item inward Not found");
		}
	}
	@ApiOperation(value="Create Item Inward as per Purchase Order ID")
	@GetMapping("/getItemInwardByPo/{poId}")
	public Object getitemInwardByPo(@PathVariable Long poId) {
		log.debug("REST request to get ItemInward : {}",  poId);
		List<ItemInward> result = itemInwardService.findByPurchaseOrderPurchaseOrderId(poId);
		System.out.println("@@@@@@@@@"+result);
		if (result != null && !result.isEmpty()) {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("ItemInward  found");
			res.setData(result);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			SygnetoResponse res = new SygnetoResponse();
			res.setStatusCode(404);
			res.setStatus("ItemInward not found");
			res.setData(null);
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * {@code DELETE  /item-inwards/:id} : delete the "id" itemInward.
	 *
	 * @param id
	 *            the id of the itemInward to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	
/*	@GetMapping("/QtyByItemId/{id}")
	public Object getQtyByItemId(@PathVariable Long id) {
		log.debug("REST request to get all getlQtyByItemId");
		ArrayList<Map<String, Object>> result = inverdItemsRepository.getSumReceivedByItemId(id);
		
		return result;
	}
*/
	@ApiOperation(value="Get total Quantity by Item Inward Id")
	@GetMapping("/qtyByItemId/{id}")
	public Object getQtyByItemId(@PathVariable Long id) {
		log.debug("REST request to get all getlQtyByItemId");
		//ArrayList<Map<String, Object>> result = inverdItemsRepository.getQtyByItemId(id);
		ArrayList<Map<String, Object>> result=inverdItemsRepository.getQtyByItemId(id);
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("Result  found", result);
		} else {
			return customeResponce.failure(404, "Result Not found");
		}
	}
	

	@ApiOperation(value="Get total Quantity by Item Inward Id")
	@GetMapping("/totalQtyByItemId/{id}")
	public Object getTotalQtyByItemId(@PathVariable Long id) {
		log.debug("REST request to get all getTotalQtyByItemId");
		Long result = inverdItemsRepository.getSumReceived(id);
		
		return result;
	
	}
	@ApiOperation(value="Delete Item Inward")
	@DeleteMapping("/itemInward/{id}")
	public Object deleteItemInward(@PathVariable Long id) {
		Optional<ItemInward> result = itemInwardService.findOne(id);
		if (result.isPresent()) {
			try {
				itemInwardService.delete(id);
				return customeResponce.success("Item inward deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Employee Not deleted");
			}

		} else {
			return customeResponce.failure(404, "Data Not found");
		}
	}
	

}
