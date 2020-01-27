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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.Address;
import com.sygneto.domain.CustomResponse;
import com.sygneto.repository.AddressRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.AddressService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing {@link com.sygneto.domain.PurchaseOrderOld}.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

	private static final String ENTITY_NAME = "address";
	private static final String partyId = null;
	private final Logger log = LoggerFactory.getLogger(AddressResource.class);

	private final AddressService addressService;

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	public AddressResource(AddressService addressService) {
		this.addressService = addressService;
	}
	@Autowired
	CustomResponse customeResponce;
	
	@Autowired
	AddressRepository addressRepository;
	
	@PostMapping("/address")
	@ApiOperation(value="Create New Address")
	public Object createAddress(@RequestBody Address address) throws URISyntaxException {
		log.debug("REST request to save purchaseOrders : {}", address);
	
		if (address.getAddressId() != null) {
			throw new BadRequestAlertException("A new address cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Address result = addressService.save(address);
		
		System.out.println("!!!!!!!!!!!!!!!!!!"+result);
		return customeResponce.success("Address created", result);
	}

	@PutMapping("/address")
	@ApiOperation(value="Update Existing Address")
	public Object updateAddress(@RequestBody Address address) throws URISyntaxException {
		log.debug("REST request to update purchaseOrders : {}", address);
	
		if (address.getAddressId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Address result = addressService.save(address);
		return customeResponce.success("Address updated", result);

	}

	/**
	 * {@code GET  /purchase-orders} : get all the address.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of purchaseOrders in body.
	 */
	
	@GetMapping("/address")
	@ApiOperation(value="Get Address of Party")
	public Object getAllAddress(@RequestParam(value = "partyId", required = false) Long partyId){
		if (partyId != null) {
			List<Address> result = addressService.findAllByPartyId(partyId);

			if (result != null && !result.isEmpty()) {
				return customeResponce.success("Address found", result);

			} else 
			{
				return customeResponce.failure(404, "Address Not found");

			}

		}
		List<Address> result = addressService.findAll();
		if (result != null&& !result.isEmpty()) {
			return customeResponce.success("Address found", result);

		} else {
			return customeResponce.failure(404, "Address Not found");

		}
	}

	/**
	 * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrder.
	 *
	 * @param id
	 *            the id of the address to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the purchaseOrder, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/addressByParty/{party}")
	@ApiOperation(value="Get Address of Organization/Party/Supllier/Customer/Employee")
	public Object getAddressByParty(@PathVariable Object party) {
		log.debug("REST request to get address : {}", partyId);
	
		Optional<Address> result = addressService.findOneByPartId(party);
		if (result != null && result.isPresent()) {
			return customeResponce.success("Address found", result);

		} else {
			return customeResponce.failure(404, "Address Not found");

		}

	}
	
	@GetMapping("/address/{id}")
	@ApiOperation(value="Get Address by Address id")
    public Object getAddress(@PathVariable Long id) {
		log.debug("REST request to get address : {}", id);
		Optional<Address> result = addressService.findOne(id);
		if (result != null && result.isPresent()) {
			return customeResponce.success("Address found", result);

		} else {
			return customeResponce.failure(404, "Address Not found");

		}

	}

/*	@GetMapping("/addressForSales/{id}")
	public Object getAddressForSales(@PathVariable Long id) {
		log.debug("REST request to get getAddressForSales : {}", id);
		StringBuilder builder=new StringBuilder();
		Optional<Address> result = addressRepository.findById(id);
	
		if (result != null && result.isPresent()) {
			if(result.get().getAddressLine1() !=null) {
			builder.append(result.get().getAddressLine1());}
			if(result.get().getAddressLine2()!=null) {
			builder.append(result.get().getAddressLine2());}
			if(result.get().getArea()!=null) {
			builder.append(result.get().getArea());}
			if(result.get().getCityName()!=null) {
			builder.append(result.get().getCityName());}
			if(result.get().getLandMark()!=null) {
			builder.append(result.get().getLandMark());}
			if(result.get().getState()!=null) {
			builder.append(result.get().getState());}
			if(result.get().getCountry()!=null) {
			builder.append(result.get().getCountry());}
			if(result.get().getPinCode()!=null) {
			builder.append(result.get().getPinCode());}
			if(result.get().getFlat()!=null) {
			builder.append(result.get().getFlat());}
			if(result.get().getAddressType()!=null) {
			builder.append(result.get().getAddressType());}
			return customeResponce.success("Address found", builder);
		}else {
			return customeResponce.failure(404, "Address Not found");
		}

	
	}*/
	@DeleteMapping("/address/{id}")
	@ApiOperation(value="Delete Address by Address id")
	public Object deleteAddress(@PathVariable Long id) {
		log.debug("REST request to delete item : {}", id);
	
		Optional<Address> result = addressService.findOne(id);
		if (result != null && result.isPresent()) {

			try {
				addressService.delete(id);
				return customeResponce.success("Address deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Address Not deleted");
			}

		} else {
			return customeResponce.failure(404, "Address Not found");

		}
	}
}
