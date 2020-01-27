package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.time.Instant;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.Address;
import com.sygneto.domain.Contact;
import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Party;
import com.sygneto.repository.AddressRepository;
import com.sygneto.repository.ContactRepository;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.ContactService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link com.sygneto.domain.Contact}.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

	private final Logger log = LoggerFactory.getLogger(ContactResource.class);

	@Autowired
	CustomResponse customeResponce;

	private static final String ENTITY_NAME = "contact";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ContactService contactService;

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	public ContactResource(ContactService contactService) {
		this.contactService = contactService;
	}

	/**
	 * {@code POST  /contact} : Create a new contact.
	 *
	 * @param contact
	 *            the contact to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new contact, or with status {@code 400 (Bad Request)} if the
	 *         contact has already an ID.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@ApiOperation(value="Create Contact")
	@PostMapping("/contact")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object createContact(@RequestBody Contact contact) throws URISyntaxException {
		log.debug("REST request to save Contact : {}", contact);
		if (contact.getContactId() != null) {
			throw new BadRequestAlertException("A new contact cannot already have an ID", ENTITY_NAME, "idexists");
		}
		
		List<Address> address = new ArrayList<Address>();
		Contact contactObj = new Contact();
		contactObj.setFirstName(contact.getFirstName());
		contactObj.setMiddleName(contact.getMiddleName());
		contactObj.setLastName(contact.getLastName());
		contactObj.setEmailId(contact.getEmailId());
		contactObj.setMobileNumber(contact.getMobileNumber());
		contactObj.setDateOfBirth(Instant.now());
		contactObj.setGender(contact.getGender());
		contactObj.setStatus(contact.getStatus());
		contactObj.setAlternateEmailId(contact.getAlternateEmailId());
		contactObj.setAlternateMobileNumber(contact.getAlternateMobileNumber());
		contactObj.setFaxNumber(contact.getFaxNumber());
		contactObj.setMessage(contact.getMessage());
		contactObj.setDesignation(contact.getDesignation());
        contactObj.setContactType(contact.getContactType());
        contactObj.setDesignation(contact.getDesignation());
        contactObj.setRegionCode(contact.getRegionCode());
        contactObj.setPrivacyOption(contact.getPrivacyOption());
        contactObj.setParty(contact.getParty());
     
        
      
        for (Address addressObj: contact.getAddress()) {
	    	Address addressResult = new Address();
	    	addressResult.setArea(addressObj.getArea());
	    	addressResult.setCityName(addressObj.getCityName());
	    	addressResult.setAddressLine1(addressObj.getAddressLine1());
	    	addressResult.setAddressLine2(addressObj.getAddressLine2());
	    
	    	addressResult.setFlat(addressObj.getFlat());
	    	addressResult.setAddressType(addressObj.getAddressType());
	    	addressResult.setCountry(addressObj.getCountry());
	    	addressResult.setPinCode(addressObj.getPinCode());
	    	addressResult.setState(addressObj.getState());
	    	addressResult.setAddressId(addressObj.getAddressId());
	    	addressResult.setContact(contactObj);
	   
	    	addressResult.setEmployees(null);
	    	address.add(addressResult);
		}
        contactObj.setAddress(address);
      
	   System.out.println("@@@@@@@@@@@@@@"+contactObj);
		Contact result = contactService.save(contactObj);
		return customeResponce.success("Contact created", result);

	}

	/**
	 * {@code PUT  /contact} : Updates an existing contact.
	 *
	 * @param contact
	 *            the contact to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated contact, or with status {@code 400 (Bad Request)} if the
	 *         contact is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the contact couldn't be
	 *         updated.
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect.
	 */
	@ApiOperation(value="Update Contact Details")
	@PutMapping("/contact")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object updateContact(@RequestBody Contact contact) throws URISyntaxException {
		log.debug("REST request to update Contact : {}", contact);
		if (contact.getContactId() == null) {
			throw new BadRequestAlertException("invalid contactType ", ENTITY_NAME, "contactType cant be null");
		}
		
		List<Address> address = new ArrayList<Address>();
		Contact contactObj = new Contact();
		contactObj.setContactId(contact.getContactId());
		contactObj.setFirstName(contact.getFirstName());
		contactObj.setMiddleName(contact.getMiddleName());
		contactObj.setLastName(contact.getLastName());
		contactObj.setEmailId(contact.getEmailId());
		contactObj.setMobileNumber(contact.getMobileNumber());
		contactObj.setDateOfBirth(Instant.now());
		contactObj.setGender(contact.getGender());
		
		contactObj.setStatus(contact.getStatus());
		contactObj.setAlternateEmailId(contact.getAlternateEmailId());
		contactObj.setAlternateMobileNumber(contact.getAlternateMobileNumber());
		contactObj.setFaxNumber(contact.getFaxNumber());
		contactObj.setMessage(contact.getMessage());
		contactObj.setDesignation(contact.getDesignation());
        contactObj.setContactType(contact.getContactType());
        contactObj.setDesignation(contact.getDesignation());
        contactObj.setRegionCode(contact.getRegionCode());
        contactObj.setPrivacyOption(contact.getPrivacyOption());
        contactObj.setParty(contact.getParty());
        
	    for (Address addressObj: contact.getAddress()) {
	    	Address addressResult = new Address();
	    	
	    	addressResult.setArea(addressObj.getArea());
	    	addressResult.setCityName(addressObj.getCityName());
	    	addressResult.setAddressId(addressObj.getAddressId());
	    	addressResult.setAddressLine1(addressObj.getAddressLine1());
	    	addressResult.setAddressLine2(addressObj.getAddressLine2());
	    	addressResult.setAddressType(addressObj.getAddressType());
	    	addressResult.setCountry(addressObj.getCountry());
	    	addressResult.setPinCode(addressObj.getPinCode());
	    	addressResult.setState(addressObj.getState());
	    	addressResult.setFlat(addressObj.getFlat());
	       
	    	addressResult.setContact(contactObj);
	    	
	    	addressResult.setEmployees(null);
	    	address.add(addressResult);
		}
	    contactObj.setAddress(address);
		Contact result = contactService.save(contactObj);
		return customeResponce.success("Contact updated", result);

	}

	/**
	 * {@code GET  /contact} : get all the contact.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of contact in body.
	 */
	@ApiOperation(value="Get Contact Details")
	@GetMapping("/contact")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	//public Object getAllContacts(@RequestParam(value = "partyId", required = false) Long partyId) {
		public Object getAllContacts() {
		log.debug("REST request to get Contact : {}" );
		List<Contact> result = contactService.findAll();
		for (Contact contact : result) {
			List<Address> result1 = addressRepository.findByContactContactId(contact.getContactId());
			contact.setAddress(result1);
		}
		if (result != null && !result.isEmpty()) {
			return customeResponce.success("Contact  found", result);
		} else {
			return customeResponce.failure(404, "Contact not found");
			
		}
	}

	/**
	 * {@code GET  /contact/:id} : get the "id" contact.
	 *
	 * @param id
	 *            the id of the contact to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the contact, or with status {@code 404 (not Found)}.
	 */
	@ApiOperation(value="Get Contact Details as per Contact Id")
	@GetMapping("/contact/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object getContact(@PathVariable Long id) {
		log.debug("REST request to get Contact : {}", id);
		Optional<Contact> result = contactService.findOneByContactId(id);
		
		List<Address> result1 = addressRepository.findByContactContactId(result.get().getContactId());
		System.out.println("@@@@@@@@@@@@@@@@@"+result1);
		result.get().setAddress(result1);
		if (result != null && result.isPresent()) {
			return customeResponce.success("Contact found", result);

		} else {
			return customeResponce.failure(404, "Contact not found");

		}
	}

	/**
	 * {@code DELETE  /contact/:id} : delete the "id" contact.
	 *
	 * @param id
	 *            the id of the contact to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	
	@ApiOperation(value="Get Contact Details of Party as per Party Id")
	@GetMapping("/partyContact/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object partyContact(@PathVariable Long id) {
		log.debug("REST request to get Contact : {}", id);
		Optional<Contact> result = contactRepository.findByPartyPartyId(id);
		
		if (result != null && result.isPresent()) {
			return customeResponce.success("Contact found", result);

		} else {
			return customeResponce.failure(404, "Contact not found");

		}
	}
	@ApiOperation(value="Delete Contact Details as per Contact Id")
	@DeleteMapping("/contact/{id}")
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
	public Object deleteContact(@PathVariable Long id) {
		
		Optional<Contact> result = contactService.findOne(id);
		if (result != null && result.isPresent()) {

			try {
				contactService.delete(id);
				if(result.get().getAddress() !=null) {
					List<Address> result1 = addressRepository.findByContactContactId(result.get().getContactId());
					for (Address address : result1) {
						addressRepository.deleteById(address.getAddressId());
					}
				}
				return customeResponce.success("Contact deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Contact not deleted");
			}

		} else {
			return customeResponce.failure(404, "Contact not found");

		}
	}
}
