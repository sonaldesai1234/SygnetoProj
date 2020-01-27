package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import com.sygneto.domain.Product;
import com.sygneto.domain.PurchaseOrderItems;
import com.sygneto.repository.BomItemRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.domain.BOMItem;
import com.sygneto.service.BomItemService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link com.sygneto.domain.BillOfMaterial}.
 */
@RestController
@RequestMapping("/api")
public class BomItemResource {

	private final Logger log = LoggerFactory.getLogger(BomItemResource.class);

	
	private static final String ENTITY_NAME = "bom";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BomItemService billOfMaterialService;
	
	@Autowired
	BomItemRepository bomItemRepository;
	
	@Autowired
	CustomResponse customeResponce;

	public BomItemResource(BomItemService billOfMaterialService) {
		this.billOfMaterialService = billOfMaterialService;
	}

	/**
	 * {@code POST  /BOM} : Create a new BillOfMaterialService.
	 *
	 * @param BOM
	 *            the product to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new product, or with status {@code 400 (Bad Request)} if the
	 *         product has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@ApiOperation(value="Create Item Bill of material ")
	@PostMapping("/bomItem")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createBOM(@RequestBody List<BOMItem> bOMItem) throws URISyntaxException {
		log.debug("REST request to save bOMItem : {}", bOMItem);
		
		for (BOMItem bomItem2 : bOMItem) {
			if (bomItem2.getBomItemid()!= null) {
				throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
			}
		}
		//BOMItem result = billOfMaterialService.save(bOMItem);
		List<BOMItem> result=bomItemRepository.saveAll(bOMItem);
		return customeResponce.success("BOM created", result);

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
	@ApiOperation(value="Update Item Bill of material ")
	@PutMapping("/bomItem")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object updateBOM(@RequestBody List<BOMItem> bOMItem) throws URISyntaxException {
		log.debug("REST request to update Employee : {}", bOMItem);
	
		for (BOMItem bomItem2 : bOMItem) {
		if (bomItem2.getBomItemid() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		}
        //BOMItem resultObj = billOfMaterialService.save(bOMItem);
		List<BOMItem> result=bomItemRepository.saveAll(bOMItem);
		return customeResponce.success("BOM updated", result);
	}

	/**
	 * {@code GET  /products} : get all the bom.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of products in body.
	 */
	@ApiOperation(value="Get all Item Bill of material ")
	@GetMapping("/bomItem")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllBOMs() {
		log.debug("REST request to get all BOM");
	
		List<BOMItem> result = billOfMaterialService.findAll();
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("BOM found", result);

		} else {
			return customeResponce.failure(404, "BOM not found");

		}
	}

	/**
	 * {@code GET  /BOM/:id} : get the "id" BOM.
	 *
	 * @param id
	 *            the id of the BOM to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the product, or with status {@code 404 (Not Found)}.
	 */
	@ApiOperation(value="Get all Item Bill of material as per Bom Item Id ")
	@GetMapping("/bomItem/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getBOM(@PathVariable Long id) {
		log.debug("REST request to get BOM : {}", id);

		Optional<BOMItem> result = billOfMaterialService.findOne(id);
		if (result != null && result.isPresent()) {
			return customeResponce.success("BOM found", result);

		} else {
			return customeResponce.failure(404, "BOM not found");

		}
	}
	
	

	/**
	 * {@code DELETE  /BOM/:id} : delete the "id" BOM.
	 *
	 * @param id
	 *            the id of the BOM to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@ApiOperation(value="Delete Item Bill of material as per BOM Item id ")
	@DeleteMapping("/bomItem/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteBOM(@PathVariable Long id) {
		log.debug("REST request to delete bom : {}", id);
	
		Optional<BOMItem> result = billOfMaterialService.findOne(id);
		if (result != null && result.isPresent()) {

			try {
				billOfMaterialService.delete(id);
				return customeResponce.success("BOM deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "BOM not deleted");
			}

		} else {
			return customeResponce.failure(404, "BOM not found");

		}
	}
}
