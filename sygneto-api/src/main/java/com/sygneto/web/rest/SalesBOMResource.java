package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.sygneto.repository.SalesBOMRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.repository.SalesBOMItemRepository;
import com.sygneto.domain.SalesBOM;
import com.sygneto.domain.SalesBOMItem;
import com.sygneto.service.SalesBOMService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link com.sygneto.domain.SalesBOM}.
 */
@RestController
@RequestMapping("/api")
public class SalesBOMResource {

	private final Logger log = LoggerFactory.getLogger(SalesBOMResource.class);

	
	private static final String ENTITY_NAME = "SalesBOM";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final SalesBOMService salesBOMService;
	
	@Autowired
	SalesBOMRepository salesBOMRepository;
	
	@Autowired
	CustomResponse customeResponce;
	@Autowired
	SalesBOMItemRepository SalesBOMItemRepository;

	public SalesBOMResource(SalesBOMService salesBOMService) {
		this.salesBOMService = salesBOMService;
	}

	/**
	 * {@code POST  /BOM} : Create a new salesBom.
	 *
	 * @param BOM
	 *            the product to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new product, or with status {@code 400 (Bad Request)} if the
	 *         product has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@PostMapping("/salesbom")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createSalesBom(@RequestBody SalesBOM salesBom) throws URISyntaxException {
		log.debug("REST request to save salesBom : {}", salesBom);
		 List<SalesBOMItem> salesBOMItemList=new ArrayList<SalesBOMItem>();
		     SalesBOM salesBOMObj = new SalesBOM();
		    
		    salesBOMObj.setSalesOrderId(salesBom.getSalesOrderId());
		    salesBOMObj.setPartyName(salesBom.getPartyName());
		    SalesBOM salesBomResult = salesBOMRepository.save(salesBOMObj);
		    
		   for (SalesBOMItem salesBOMItem : salesBom.getSalesBOMItem()) {
			  SalesBOMItem SalesBOMItemObj=new SalesBOMItem();
			  SalesBOMItemObj.setItemId(salesBOMItem.getItemId());
			  SalesBOMItemObj.setItemName(salesBOMItem.getItemName());
			  SalesBOMItemObj.setQuantity(salesBOMItem.getQuantity());
			  SalesBOMItemObj.setItemCode(salesBOMItem.getItemCode());
			  SalesBOMItemObj.setUnitOfMeasurement(salesBOMItem.getUnitOfMeasurement());
			  SalesBOMItemObj.setSalesBOM(salesBomResult);
			  SalesBOMItem SalesBOMItem1=SalesBOMItemRepository.save(SalesBOMItemObj);
			  //salesBOMItemList.add(SalesBOMItem1);
		  }
		  //salesBomResult.setSalesBOMItem(salesBOMItemList);
  	    System.out.println("@@@@@@@@@@@@@@!!!!!!!!!!!"+salesBomResult);
		 
		return customeResponce.success("salesBom created", salesBomResult);

	}

	/**
	 * {@code PUT  /products} : Updates an existing salesBom.
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
	@PutMapping("/salesbom")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object updateBOM(@RequestBody SalesBOM salesBom) throws URISyntaxException {
		log.debug("REST request to update salesBom : {}", salesBom);
		
		if (salesBom.getSalesBomId()== null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		
        SalesBOM resultObj = salesBOMService.save(salesBom);
		//List<SalesBOM> result=bomItemRepository.saveAll(bOMItem);
		return customeResponce.success("salesBom updated", resultObj);
	}

	/**
	 * {@code GET  /products} : get all the salesBom.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of products in body.
	 */
	@GetMapping("/salesbom")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllBOMs() {
		log.debug("REST request to get all salesBom");
	
		List<SalesBOM> result = salesBOMService.findAll();
		
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("salesBom found", result);

		} else {
			return customeResponce.failure(404, "salesBom not found");

		}
	}

	/**
	 * {@code GET  /BOM/:id} : get the "id" salesBom.
	 *
	 * @param id
	 *            the id of the BOM to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the product, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/salesbom/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getBOM(@PathVariable Long id) {
		log.debug("REST request to get salesBom : {}", id);

		Optional<SalesBOM> result = salesBOMService.findOne(id);
		if (result != null && result.isPresent()) {
			return customeResponce.success("salesBom found", result);

		} else {
			return customeResponce.failure(404, "salesBom not found");

		}
	}
	
	/**
	 * {@code DELETE  /BOM/:id} : delete the "id" salesBom.
	 *
	 * @param id
	 *            the id of the BOM to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/salesbom/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteBOM(@PathVariable Long id) {
		log.debug("REST request to delete salesBom : {}", id);
	
		Optional<SalesBOM> result = salesBOMService.findOne(id);
		if (result != null && result.isPresent()) {

			try {
				salesBOMService.delete(id);
				return customeResponce.success("salesBom deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "salesBom not deleted");
			}

		} else {
			return customeResponce.failure(404, "salesBom not found");

		}
	}
}
