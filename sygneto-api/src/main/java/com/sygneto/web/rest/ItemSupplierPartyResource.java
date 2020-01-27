package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.ItemPriceHistory;
import com.sygneto.domain.ItemSupplierParty;
import com.sygneto.domain.PurchaseRequestItem;
import com.sygneto.repository.ItemSupplierPartyRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.ItemPriceHistoryService;
import com.sygneto.service.ItemSupplierPartyService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link com.sygneto.domain.itemSupplierParty}.
 */
@RestController
@RequestMapping("/api")
public class ItemSupplierPartyResource {

	private final Logger log = LoggerFactory.getLogger(ItemSupplierPartyResource.class);

	private static final String ENTITY_NAME = "item";


	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	@Autowired
	CustomResponse customeResponce;
	private final ItemSupplierPartyService itemSupplierPartyService;

	public ItemSupplierPartyResource(ItemSupplierPartyService itemCategoryService) {
		this.itemSupplierPartyService = itemCategoryService;
	}
	
	@Autowired
	ItemSupplierPartyRepository itemSupplierPartyRepository;
	
	@Autowired
	ItemPriceHistoryService itemPriceHistoryService;
	
	@PostMapping("/itemSupplier")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createItem(@RequestBody List<ItemSupplierParty> itemSupplierParty) throws URISyntaxException {
		log.debug("REST request to save item : {}", itemSupplierParty);
		for (ItemSupplierParty itemSupplierPartyObj : itemSupplierParty) {
			if (itemSupplierPartyObj.getItemSupplierPartyId() != null) {
				throw new BadRequestAlertException("A new itemSupplierParty cannot already have an ID", ENTITY_NAME,
						"idexists");
			}
		}
		List<ItemSupplierParty> result = itemSupplierPartyRepository.saveAll(itemSupplierParty);
		return customeResponce.success("Item supplier created", result);
	}

	@PutMapping("/itemSupplier")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object updateItem(@RequestBody ItemSupplierParty itemSupplierParty) throws URISyntaxException {
		log.debug("REST request to update item : {}", itemSupplierParty);
		if (itemSupplierParty.getItemSupplierPartyId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		
		Optional<ItemSupplierParty> resultObj = itemSupplierPartyService.findOne(itemSupplierParty.getItemSupplierPartyId());
		
		if(resultObj.get().getItemUnitPrice() != itemSupplierParty.getItemUnitPrice() || 
				resultObj.get().getValidFrom() != itemSupplierParty.getValidFrom() ||
						resultObj.get().getValidTill() != itemSupplierParty.getValidTill()) {
			
			ItemPriceHistory itemPriceHistory=new ItemPriceHistory();
			itemPriceHistory.setItemId(resultObj.get().getItem().getItemId());
			itemPriceHistory.setItemUnitPrice(resultObj.get().getItemUnitPrice());
			itemPriceHistory.setPartyId(resultObj.get().getParty().getPartyId());
			itemPriceHistory.setValidFrom(resultObj.get().getValidFrom());
			
			if(itemSupplierParty.getValidFrom() !=null) {
				Instant  instantstart = itemSupplierParty.getValidFrom().minus(Duration.ofDays(1));
				itemPriceHistory.setValidTill(instantstart);
			}
			ItemPriceHistory itemPriceHistoryResult = itemPriceHistoryService.save(itemPriceHistory);
		}
		
		ItemSupplierParty result = itemSupplierPartyService.save(itemSupplierParty);
		return customeResponce.success("Item supplier updated", result);

	}

	@GetMapping("/itemSupplier")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllIteams() {
		log.debug("REST request to get all item");
		List<ItemSupplierParty> result = itemSupplierPartyService.findAll();
		if (result != null&& !result.isEmpty()) {
			return customeResponce.success("Item supplier found", result);

		} else {
			return customeResponce.failure(404, "Item supplier not found");

		}
	}

	@GetMapping("/itemSupplier/{id}")
	public Object getItem(@PathVariable Long id) {
		log.debug("REST request to get item : {}", id);
		Optional<ItemSupplierParty> result = itemSupplierPartyService.findOne(id);
		if (result != null && result.isPresent()) {
			return customeResponce.success("Item supplier found", result);

		} else {
			return customeResponce.failure(404, "Item supplier not found");

		}

	}

	
	
	@PostMapping("/itemSupplierByItemIdForPR")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createItemSupplierByItemId(@RequestBody List<PurchaseRequestItem> purchaseRequestItem) throws URISyntaxException {
		log.debug("REST request to get purchaseRequestItem : {}", purchaseRequestItem);
		HashSet<Long> str = new HashSet<>();
		HashSet<Long> partyId = new HashSet<>();
		
		for (PurchaseRequestItem purchaseRequestItemObj : purchaseRequestItem) {
			Long i=purchaseRequestItemObj.getItemId();
			str.add(i);
		}
		List<ItemSupplierParty> result = itemSupplierPartyRepository.findByItemItemIdIn(str);
		
		/*for (ItemSupplierParty itemSupplierParty : result) {
			Long j=itemSupplierParty.getParty().getPartyId();
			partyId.add(j);
		}
		
		List<ItemSupplierParty> itemSupplierPartyResult = itemSupplierPartyRepository.findByPartyPartyIdIn(partyId);*/
		
		return customeResponce.success("Item supplier created", result);

	}
	
	
	
	@GetMapping("/itemSupplierByPartId/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getItemSupplierByPartId(@PathVariable Long id) {
		log.debug("REST request to get itemSupplierByPartId : {}", id);
		List<ItemSupplierParty> result = itemSupplierPartyRepository.findByPartyPartyId(id);
		//Optional<ItemSupplierParty> result1 = itemSupplierPartyService.findOne(result.get().getItemSupplierPartyId());
		if (result != null && ! result.isEmpty()) {
			return customeResponce.success("Item supplier found", result);

		} else {
			return customeResponce.failure(404, "Item supplier not found");

		}

	}
	
	@GetMapping("/supplierByItemId/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getSupplierByItemId(@PathVariable Long id) {
		log.debug("REST request to get supplierByItemId : {}", id);
		List<ItemSupplierParty> result = itemSupplierPartyRepository.findByItemItemId(id);
		//Optional<ItemSupplierParty> result1 = itemSupplierPartyService.findOne(result.get().getItemSupplierPartyId());
		if (result != null && ! result.isEmpty()) {
			return customeResponce.success("Item supplier found", result);

		} else {
			return customeResponce.failure(404, "Item supplier not found");

		}

	}
	
	
	@DeleteMapping("/itemSupplier/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteItem(@PathVariable Long id) {
			try {
				Optional<ItemSupplierParty> result = itemSupplierPartyService.findOne(id);
				System.out.println("@@@@@@@@@@@@@@@@@"+result.get());
				result.get().setParty(null);
				result.get().setItem(null);
				ItemSupplierParty result1 = itemSupplierPartyRepository.save(result.get());
				System.out.println("@@@@@@@@@@@@@@@@@!!!!!!!!!!!!"+result1);
				itemSupplierPartyService.delete(id);
				
				return customeResponce.success("Item supplier deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Item supplier not deleted");
			}

		}
	
	
	
}
