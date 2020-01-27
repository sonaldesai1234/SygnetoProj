package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.ItemCategory;
import com.sygneto.service.ItemCategoryService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link com.sygneto.domain.ItemCategory}.
 */
@RestController
@RequestMapping("/api")
public class ItemCategoryResource {

	private final Logger log = LoggerFactory.getLogger(ItemCategoryResource.class);

	private static final String ENTITY_NAME = "itemcategory";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ItemCategoryService itemCategoryService;

	public ItemCategoryResource(ItemCategoryService itemCategoryService) {
		this.itemCategoryService = itemCategoryService;
	}
	@Autowired
	CustomResponse customeResponce;
	  
	
	@ApiOperation(value="Create Item category")
	@PostMapping("/itemCategory")
	public Object createItemCategory(@RequestBody ItemCategory ItemCategory) throws URISyntaxException {
		log.debug("REST request to save ItemCategory : {}", ItemCategory);
		if (ItemCategory.getItemCategoryId() != null) {
			throw new BadRequestAlertException("A new ItemCategory cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ItemCategory result = itemCategoryService.save(ItemCategory);

		return customeResponce.success("Item category created ", result);

	}
	@ApiOperation(value="Update Item category")
	@PutMapping("/itemCategory")
	public Object updateItemCategory(@RequestBody ItemCategory ItemCategory) throws URISyntaxException {
		log.debug("REST request to update ItemCategory : {}", ItemCategory);
		if (ItemCategory.getItemCategoryId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ItemCategory result = itemCategoryService.save(ItemCategory);

		return customeResponce.success("Item category updated ", result);

	}
	@ApiOperation(value="Get all  Item category")
	@GetMapping("/itemCategory")
	public Object getAllItemCategory() {
		log.debug("REST request to get all itemCategory");
		List<ItemCategory> result = itemCategoryService.findAll();
		if (result != null&& !result.isEmpty()) {
			return customeResponce.success("Item category found ", result);

		} else {
			return customeResponce.failure(404, "Item category not found ");

		}
	}
	@ApiOperation(value="Get all  Item category by Item Category ID")
	@GetMapping("/itemCategory/{id}")
	public Object getItemCategory(@PathVariable Long id) {
		log.debug("REST request to get Employee : {}", id);
		Optional<ItemCategory> result = itemCategoryService.findOne(id);
		if (result != null && result.isPresent()) {
			return customeResponce.success("Item category found ", result);

		} else {
			return customeResponce.failure(404, "Item category not found ");

		}
	}
	@ApiOperation(value="Delete Item category by Item Category ID")
	@DeleteMapping("/itemCategory/{id}")
	public Object deleteItemCategory(@PathVariable Long id) {
		log.debug("REST request to delete Employee : {}", id);
		Optional<ItemCategory> result = itemCategoryService.findOne(id);
		if (result != null && result.isPresent()) {

			try {
				itemCategoryService.delete(id);
				return customeResponce.success("Item category deleted ", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Item category not deleted ");
			}

		} else {
			return customeResponce.failure(404, "Item category not found ");

		}
	}

}
