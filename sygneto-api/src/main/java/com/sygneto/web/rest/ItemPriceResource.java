package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.ItemPrice;
import com.sygneto.service.ItemPriceService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.itemPrice}.
 */
@RestController
@RequestMapping("/api")
public class ItemPriceResource {

	private final Logger log = LoggerFactory.getLogger(ItemPriceResource.class);

	private static final String ENTITY_NAME = "ItemPrice";

	@Autowired
	CustomResponse customeResponce;
	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ItemPriceService itemPriceService;

	public ItemPriceResource(ItemPriceService itemPriceService) {
		this.itemPriceService = itemPriceService;
	}
	 @ApiOperation(value="Assign price to Item ")
	@PostMapping("/itemPrice")
	public Object createItem(@RequestBody ItemPrice itemPrice) throws URISyntaxException {
		log.debug("REST request to save item : {}", itemPrice);
		if (itemPrice.getItemPriceId() != null) {
			throw new BadRequestAlertException("A new itemPrice cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ItemPrice result = itemPriceService.save(itemPrice);
		return customeResponce.success("item price created", result);

	}

	 @ApiOperation(value="Modify price of Item ")
	@PutMapping("/itemPrice")
	public Object updateItem(@RequestBody ItemPrice itemPrice) throws URISyntaxException {
		log.debug("REST request to update ItemPrice : {}", itemPrice);
		if (itemPrice.getItemPriceId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ItemPrice result = itemPriceService.save(itemPrice);

		return customeResponce.success("item price updated", result);

	}
	@ApiOperation(value="Get price of all Item ")
	@GetMapping("/itemPrice")
	public Object getAllIteams() {
		log.debug("REST request to get all item");
		List<ItemPrice> result = itemPriceService.findAll();
		if (result != null&& !result.isEmpty()) {
			return customeResponce.success("item price found", result);

		} else {
			return customeResponce.failure(404, "item price not found");

		}
	}
	@ApiOperation(value="Get Specific Item Price based on Item Price Id")
	@GetMapping("/itemPrice/{id}")
	public Object getItem(@PathVariable Long id) {
		log.debug("REST request to get item : {}", id);
		Optional<ItemPrice> result = itemPriceService.findOne(id);
		if (result != null && result.isPresent()) {
			return customeResponce.success("item price found", result);

		} else {
			return customeResponce.failure(404, "item price not found");

		}

	}
	@ApiOperation(value="Delete price of specific Item based on ItemPrice Id")
	@DeleteMapping("/itemPrice/{id}")
	public Object deleteItem(@PathVariable Long id) {
		log.debug("REST request to delete item : {}", id);
		Optional<ItemPrice> result = itemPriceService.findOne(id);
		if (result.isPresent()) {

			try {
				itemPriceService.delete(id);
				return customeResponce.success("item price deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "item price not deleted");
			}

		} else {
			return customeResponce.failure(404, "item price not found");

		}
	}
}
