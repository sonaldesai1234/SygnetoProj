package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.InverdItems;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemInward;
import com.sygneto.domain.ItemOutward;
import com.sygneto.domain.PurchaseRequest;
import com.sygneto.repository.ItemOutwardRepository;
import com.sygneto.repository.PurchaseRequestRepository;
import com.sygneto.service.ItemInwardService;
import com.sygneto.service.ItemOutwardService;
import com.sygneto.service.ItemService;
import com.sygneto.service.PurchaseRequestService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link com.sygneto.domain.ItemOutward}.
 */
@RestController
@RequestMapping("/api")
public class ItemOutwardResource {

	private final Logger log = LoggerFactory.getLogger(ItemOutwardResource.class);

	private static final String ENTITY_NAME = "itemOutward";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	ItemService itemService;
	
	@Autowired
	private PurchaseRequestService purchaseRequestService;
	
	@Autowired
	private ItemOutwardRepository ItemOutwardRepository;
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;
	
	@Autowired
	private ItemInwardService itemInwardService;

	private final ItemOutwardService itemOutwardService;

	public ItemOutwardResource(ItemOutwardService itemOutwardService) {
		this.itemOutwardService = itemOutwardService;
	}
	@Autowired
	CustomResponse customeResponce;
	/**
	 * {@code POST  /item-outwards} : Create a new itemOutward.
	 *
	 * @param itemOutward
	 *            the itemOutward to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new itemOutward, or with status {@code 400 (Bad Request)} if
	 *         the itemOutward has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	/*@PostMapping("/itemOutward")
	public Object createItemOutward(@RequestBody ItemOutward itemOutward) throws URISyntaxException {
		log.debug("REST request to save ItemOutward : {}", itemOutward);
		if (itemOutward.getItemOutwardId() != null) {
			throw new BadRequestAlertException("A new itemOutward cannot already have an ID", ENTITY_NAME, "idexists");
		}
		
		Optional<PurchaseRequest> result = purchaseRequestService.findOne(itemOutward.getPurchaseRequestId());
		if(result.get().getQuantity() > 0L) {
			 ItemOutward itemOutwardObj = itemOutwardService.save(itemOutward);
			 
			 if(result.get().getQuantity()==itemOutwardObj.getItemQuantity()) {
				 result.get().setStatus("closed");
				  purchaseRequestService.save(result.get());
			}else {
				  Long availablwQty= Math.subtractExact(result.get().getQuantity() , itemOutwardObj.getItemQuantity());
				  result.get().setQuantity(availablwQty);
				  purchaseRequestService.save(result.get());
			}
		     return customeResponce.success("Item outword created", itemOutwardObj);
		}else {
			ItemInward ItemInward=new ItemInward();
			List<InverdItems> InverdItemsList=new ArrayList<InverdItems>();
			InverdItems inverdItems=new InverdItems();
			inverdItems.setNotes(itemOutward.getInstructions());
			inverdItems.setPurchaseRequestId(itemOutward.getPurchaseRequestId());
			inverdItems.setQuantityReceived(itemOutward.getItemQuantity());
			inverdItems.setItem(itemOutward.getItem());
			InverdItemsList.add(inverdItems);
			ItemInward.setInverdItems(InverdItemsList);
		    ItemInward resultObj = itemInwardService.save(ItemInward);
		    
		    result.get().setStatus("closed");
			purchaseRequestService.save(result.get());
		    return customeResponce.success("Item in inward added successfully", resultObj);
		}
	}*/
	
	@ApiOperation(value="Create Item Outward")
	@PostMapping("/itemOutward")
	public Object createItemOutward(@RequestBody List<ItemOutward> itemOutward) throws URISyntaxException {
		log.debug("REST request to save ItemOutward : {}", itemOutward);
		if (itemOutward.get(0).getItemOutwardId() != null) {
			throw new BadRequestAlertException("A new itemOutward cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Map<Object,Object> fResult=new HashMap<>();
		List<ItemOutward> itemOutwardList= new ArrayList<>();
		List<ItemInward> ItemInwardList= new ArrayList<>();
		fResult.put("Item outword created",itemOutwardList );
		fResult.put("Item Inward created",ItemInwardList );
		
		for (ItemOutward itemOutwardOb : itemOutward) {
			Optional<PurchaseRequest> result = purchaseRequestService.findOne(itemOutwardOb.getPurchaseRequestId());
			if(result.get().getQuantity() > 0L) {
				
				 ItemOutward itemOutwardObj = itemOutwardService.save(itemOutwardOb);
				 
				 if(result.get().getQuantity()==itemOutwardObj.getItemQuantity()) {
					 result.get().setStatus("closed");
					  purchaseRequestService.save(result.get());
				}else {
					  Long availablwQty= Math.subtractExact(result.get().getQuantity() , itemOutwardObj.getItemQuantity());
					  result.get().setQuantity(availablwQty);
					  purchaseRequestService.save(result.get());
				}
			    // return customeResponce.success("Item outword created", itemOutwardObj);
				 itemOutwardList.add(itemOutwardObj);
			}else {
				ItemInward ItemInward=new ItemInward();
				List<InverdItems> InverdItemsList=new ArrayList<InverdItems>();
				InverdItems inverdItems=new InverdItems();
				inverdItems.setNotes(itemOutwardOb.getInstructions());
				inverdItems.setPurchaseRequestId(itemOutwardOb.getPurchaseRequestId());
				inverdItems.setQuantityReceived(itemOutwardOb.getItemQuantity());
				inverdItems.setItem(itemOutwardOb.getItem());
				InverdItemsList.add(inverdItems);
				ItemInward.setInverdItems(InverdItemsList);
			    ItemInward resultObj = itemInwardService.save(ItemInward);
			    
			    result.get().setStatus("closed");
				purchaseRequestService.save(result.get());
				
				ItemInwardList.add(resultObj);
			    //return customeResponce.success("Item in inward added successfully", resultObj);
			}
		}
		return fResult;
	}
	/**
	 * {@code PUT  /item-outwards} : Updates an existing itemOutward.
	 *
	 * @param itemOutward
	 *            the itemOutward to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated itemOutward, or with status {@code 400 (Bad Request)} if
	 *         the itemOutward is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the itemOutward couldn't be
	 *         updated.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@ApiOperation(value="Update Item Outward")
	@PutMapping("/itemOutward")
	public Object updateItemOutward(@RequestBody ItemOutward itemOutward) throws URISyntaxException {
		log.debug("REST request to update ItemOutward : {}", itemOutward);
		if (itemOutward.getItemOutwardId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		Long itemOutwardQnty = itemOutward.getItemQuantity();

		Optional<ItemOutward> itemOutwardPrevious = itemOutwardService.findOne(itemOutward.getItemOutwardId());

		if (itemOutwardPrevious.isPresent() && itemOutwardQnty != itemOutwardPrevious.get().getItemQuantity()) {

			Optional<Item> item = itemService.findOne(itemOutward.getItem().getItemId());
			if (item.isPresent()) {
				int diff=itemOutwardQnty.compareTo(itemOutwardPrevious.get().getItemQuantity());
				
				if (diff>0) {
					Long itemDiff = itemOutwardQnty - itemOutwardPrevious.get().getItemQuantity();
					Long newItemInStock = item.get().getTotalQtyInStock() + itemDiff;

					item.get().setTotalQtyInStock(newItemInStock);
				} else if (diff<0) {
					Long itemDiff = itemOutwardPrevious.get().getItemQuantity()-itemOutwardQnty;
					Long newItemInStock = item.get().getTotalQtyInStock() - itemDiff;

					item.get().setTotalQtyInStock(newItemInStock);
				}

				itemService.save(item.get());

			}

		}

		ItemOutward result = itemOutwardService.save(itemOutward);

		return customeResponce.success("Item outword updated", result);

	}

	/**
	 * {@code GET  /item-outwards} : get all the itemOutwards.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of itemOutwards in body.
	 */
	@ApiOperation(value="Get all Item Outward")
	@GetMapping("/itemOutward")
	public Object getAllItemOutwards() {
		log.debug("REST request to get all ItemOutwards");
		List<ItemOutward> result = itemOutwardService.findAll();
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("Item outword found", result);

		} else {
			return customeResponce.failure(404, "Item outword Not found");

		}
	}
	
	/**
	 * {@code GET  /item-outwards/:id} : get the "id" itemOutward.
	 *
	 * @param id
	 *            the id of the itemOutward to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the itemOutward, or with status {@code 404 (Not Found)}.
	 */
	@ApiOperation(value="Get Item Outward as per ItemOutwardId")
	@GetMapping("/itemOutward/{itemOutwardId}")
	public Object getItemOutward(@PathVariable Long itemOutwardId) {
		log.debug("REST request to get ItemOutward : {}", itemOutwardId);
		Optional<ItemOutward> result = itemOutwardService.findOne(itemOutwardId);
		if (result != null && result.isPresent()) {
			return customeResponce.success("Item outword found", result);

		} else {
			return customeResponce.failure(404, "Item outword Not found");

		}
	}

	/**
	 * {@code DELETE  /item-outwards/:id} : delete the "id" itemOutward.
	 *
	 * @param id
	 *            the id of the itemOutward to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@ApiOperation(value="Delete Item Outward as per ItemOutwardId")
	@DeleteMapping("/itemOutward/{id}")
	public Object deleteItemOutward(@PathVariable Long id) {
		Optional<ItemOutward> result = itemOutwardService.findOne(id);
		if (result.isPresent()) {

			try {
				itemOutwardService.delete(id);
				return customeResponce.success("Item outword deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Item outword not deleted");
			}

		} else {
			return customeResponce.failure(404, "Item outword not found");

		}
	}
}

/*if (result.getItem() != null) {

	Optional<Item> item = itemService.findOne(result.getItem().getItemId());
	
	if (item.isPresent()) {
		Long itemOutwordQnt = result.getItemQuantity();
		Long itemInStock = item.get().getTotalQtyInStock();
		Long newItemInStock = itemInStock - itemOutwordQnt;

		item.get().setTotalQtyInStock(newItemInStock);

		itemService.save(item.get());

	}
}*/

// return customeResponce.success("Item outword created", result1);


/*	  Optional<PurchaseRequest> purchaseRequest = purchaseRequestService.findOne(result.getPurchaseRequestId());
if(purchaseRequest.get().getQuantity()==result.getItemQuantity()) {
	  purchaseRequest.get().setStatus("closed");
	  purchaseRequestService.save(purchaseRequest.get());
}else {
	  Long availablwQty= Math.subtractExact(purchaseRequest.get().getQuantity() , result.getItemQuantity());
	  purchaseRequest.get().setQuantity(availablwQty);
	  purchaseRequestService.save(purchaseRequest.get());
}*/

/*ItemOutward result1=null;
float sum=0L;

//manual lock
	List<ItemOutward> result=itemOutwardService.pessimisticLock(itemOutward.getPurchaseRequestId());
	//Optional<PurchaseRequest> result = purchaseRequestRepository.pessimisticLock(itemOutward.getPurchaseRequestId());
	Long availableQty=itemOutwardService.findAllItemQty(itemOutward.getPurchaseRequestId());
	if(availableQty !=null) {
		sum=availableQty + itemOutward.getItemQuantity();
	}else {
		sum=itemOutward.getItemQuantity();
	}
    Optional<PurchaseRequest> purchaseRequest = purchaseRequestService.findOne(itemOutward.getPurchaseRequestId());
    if(purchaseRequest.get().getQuantity() >= sum) {
    result1 = itemOutwardService.save(itemOutward);
    ItemOutwardRepository.commit();
    //  Optional<ItemOutward> result2 = itemOutwardService.findOne(result1.getItemOutwardId());
      return customeResponce.success("Item outword created", "Successfully created");
    	
    }else {
    	ItemOutwardRepository.rollback();
   return customeResponce.success("Item outword not created","NotCreated" );
    }
*/