package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.sygneto.repository.BillOfMaterialRepository;
import com.sygneto.repository.BomItemRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.domain.BOMItem;
import com.sygneto.domain.BillOfMaterial;
import com.sygneto.domain.CustomQueryForBOM;
import com.sygneto.service.BillOfMaterialService;

import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing {@link com.sygneto.domain.BillOfMaterial}.
 */
@RestController
@RequestMapping("/api")
public class BillOfMaterialResource {

	private final Logger log = LoggerFactory.getLogger(BillOfMaterialResource.class);

	
	private static final String ENTITY_NAME = "bom";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	BillOfMaterialRepository billOfMaterialRepository;
	@Autowired
	BomItemRepository bomItemRepository;
	@Autowired
	CustomResponse customeResponce;
	private final BillOfMaterialService billOfMaterialService;

	public BillOfMaterialResource(BillOfMaterialService billOfMaterialService) {
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
	
	@PostMapping("/bom")
	@ApiOperation(value="Create Bill Of Material")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createBOM(@RequestBody BillOfMaterial billOfMaterial) throws URISyntaxException {
		log.debug("REST request to save BillOfMaterial : {}", billOfMaterial);
		if (billOfMaterial.getProductBomId()!= null) {
			throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
		}
		// set product version as static field
		float ver = (float) 1.0;
		billOfMaterial.setVersion(ver);
		// Calculate bom totalPrice amount based on its quantity and unit price
		BillOfMaterial result = billOfMaterialService.save(billOfMaterial);
	
		return customeResponce.success("BOM created", result);
	     
	}

	
	/*@PostMapping("/bom")
	public Object createBOM(@RequestBody BomDTO bomDTO) throws URISyntaxException {
		log.debug("REST request to save BillOfMaterial : {}", bomDTO);
		if (bomDTO.getProductBomId()!= null) {
			throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
		}
		// set product version as static field
		float ver = (float) 1.0;
		billOfMaterial.setVersion(ver);
		// Calculate bom totalPrice amount based on its quantity and unit price
	     if(billOfMaterial.getUnitPrice()>0.0)
	        {
	        	float totalAmount=billOfMaterial.getUnitPrice()*billOfMaterial.getQuantity();
	        	billOfMaterial.setTotalPrice(totalAmount);
	        }
		BillOfMaterial result = billOfMaterialService.save(billOfMaterial);
		return customeResponce.success("BOM created", result);

	}*/
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
	@PutMapping("/bom")
	@ApiOperation(value="Update existing Bill Of Material")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object updateBOM(@RequestBody BillOfMaterial billOfMaterial) throws URISyntaxException {
		log.debug("REST request to update bom : {}", billOfMaterial);
		if (billOfMaterial.getProductBomId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Optional<BillOfMaterial> result = billOfMaterialService.findOne(billOfMaterial.getProductBomId());
		Float floatObj;
		floatObj = new Float(result.get().getVersion());
		float incrver = floatObj;
		float f = incrver + 1;
		//billOfMaterial.setVersion(f);
		
        List<BOMItem> bomItemList = new ArrayList<BOMItem>();
	     
	     BillOfMaterial billOfMaterialObj = new BillOfMaterial();
	     billOfMaterialObj.setVersion(f);
	     billOfMaterialObj.setProduct(billOfMaterial.getProduct());
	    // billOfMaterialObj.setProductBomId(billOfMaterial.getProductBomId());
	    for (BOMItem bomItemObj: billOfMaterial.getBomItem()) {
	    	BOMItem bomItem = new BOMItem();
	    	bomItem.setBomItemid(bomItemObj.getBomItemid());
	    	bomItem.setItemId(bomItemObj.getItemId());
	    	bomItem.setQuantity(bomItemObj.getQuantity());
	    	bomItem.setItemName(bomItemObj.getItemName());
	    	bomItemList.add(bomItem);
		}
	    
	    billOfMaterialObj.setBomItem(bomItemList);
	    
		BillOfMaterial resultObj = billOfMaterialService.save(billOfMaterialObj);
		
		return customeResponce.success("BOM updated", resultObj);
	}

	/**
	 * {@code GET  /products} : get all the bom.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of products in body.
	 */
	
	@ApiOperation(value="Get all Bill Of Material")
	@GetMapping("/bom")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllBOMs() {
		log.debug("REST request to get all BOM");
	
		List<BillOfMaterial> result = billOfMaterialService.findAll();
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
	@ApiOperation(value="Get Bill Of Material as per Bill of material Id")
	@GetMapping("/bom/{id}")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getBOM(@PathVariable Long id) {
		log.debug("REST request to get BOM : {}", id);
	
		Optional<BillOfMaterial> result = billOfMaterialService.findOne(id);
		if (result != null && result.isPresent()) {
			return customeResponce.success("BOM found", result);

		} else {
			return customeResponce.failure(404, "BOM not found");

		}
	}
	@ApiOperation(value="Get Latest Bill Of Material")
	@GetMapping("/letestBom")
	// @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllLetestBOMs() {
		log.debug("REST request to get all letestBom");
		List<BillOfMaterial> billOfMaterialList = new ArrayList<>();
		List<CustomQueryForBOM> result = billOfMaterialRepository.findAllByversion();

		for (CustomQueryForBOM customQueryForBOM : result) {
			BillOfMaterial result1 = billOfMaterialService.findByProductNversion(customQueryForBOM.getProductId(),
					customQueryForBOM.getVersion());
			billOfMaterialList.add(result1);
		}
		if (billOfMaterialList != null) {
			return customeResponce.success("BOM found", billOfMaterialList);
		} else {
			return customeResponce.failure(404, "BOM not found");

		}

	}
	
	@ApiOperation(value="Get Bill of material Product Id wise")
	@GetMapping("/bomByProduct/{productId}")
	// @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getbomByProduct(@PathVariable Long productId) {
		log.debug("REST request to get all bomByProduct", productId);
		List<BillOfMaterial> billOfMaterialList=billOfMaterialService.findByProductProductId(productId);
		if (billOfMaterialList != null) {
			return customeResponce.success("BOM found", billOfMaterialList);
		} else {
			return customeResponce.failure(404, "BOM not found");
		}
	}
	@ApiOperation(value="Get Bill of material Item Id wise")
	@GetMapping("/bomByItemId/{id}")
	//@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getAllBOMByItemId(@PathVariable Long id) {
		log.debug("REST request to get  BOM by id");
		
		List<BillOfMaterial> result=billOfMaterialRepository.findByBomItemBomItemid(id);
		if (result != null && !result.isEmpty()) {
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
	@ApiOperation(value="Delete Bill of material By Bill of Matrial ID")
	@DeleteMapping("/bom/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteBOM(@PathVariable Long id) {
		log.debug("REST request to delete bom : {}", id);
	
		Optional<BillOfMaterial> result = billOfMaterialService.findOne(id);
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
