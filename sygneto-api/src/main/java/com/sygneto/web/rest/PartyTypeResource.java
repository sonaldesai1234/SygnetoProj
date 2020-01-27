package com.sygneto.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.PartyType;
import com.sygneto.service.PartyTypeService;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing {@link com.sygneto.domain.PartyType}.
 */
@RestController
@RequestMapping("/api")
public class PartyTypeResource {

    private final Logger log = LoggerFactory.getLogger(PartyTypeResource.class);

    private static final String ENTITY_NAME = "partyType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
	CustomResponse customResponse;
    private final PartyTypeService partyTypeService;

    public PartyTypeResource(PartyTypeService partyTypeService) {
        this.partyTypeService = partyTypeService;
    }

    /**
     * {@code POST  /party-types} : Create a new partyType.
     *
     * @param partyType the partyType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partyType, or with status {@code 400 (Bad Request)} if the partyType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @ApiOperation(value="Create different types of party like Public,Private etc.")
    @PostMapping("/partyType")
    public Object createPartyType(@RequestBody PartyType partyType) throws URISyntaxException {
        log.debug("REST request to save PartyType : {}", partyType);
        
        PartyType result = partyTypeService.save(partyType);
       return customResponse.success("Party type created", result);
        
       
    }

    /**
     * {@code PUT  /party-types} : Updates an existing partyType.
     *
     * @param partyType the partyType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partyType,
     * or with status {@code 400 (Bad Request)} if the partyType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partyType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @ApiOperation(value="Update party types.")
    @PutMapping("/partyType")
    public Object updatePartyType(@RequestBody PartyType partyType) throws URISyntaxException {
        log.debug("REST request to update PartyType : {}", partyType);
        PartyType result = partyTypeService.save(partyType);
       return customResponse.success("Party type updated", result);
        
       
    }

    /**
     * {@code GET  /party-types} : get all the partyTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partyTypes in body.
     */
    @ApiOperation(value="Get all party types")
    @GetMapping("/partyType")
     public Object  getAllPartyTypes() {
        log.debug("REST request to get all PartyTypes");
        List<PartyType> result=partyTypeService.findAll();
        if(result !=null&& !result.isEmpty()) {
        	return customResponse.success("Party type found", result);
        	
        }else 
        {
        	return customResponse.failure(404, "Party type found");
        	
        }
    }
   @ApiOperation(value="Get/Count Total party types exists.")
   @GetMapping("/partyTypeCount")
    public Object  getAllPartyTypesCount() {
       log.debug("REST request to get all PartyTypes");
       Long  result=partyTypeService.countByPartyType();
     
       return result;
   }
   

    /**
     * {@code GET  /party-types/:id} : get the "id" partyType.
     *
     * @param id the id of the partyType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partyType, or with status {@code 404 (Not Found)}.
     */
   @ApiOperation(value="Get specific party types as per Party Type.")
    @GetMapping("/partyType/{partyType}")
    public Object getPartyType(@PathVariable String  partyType) {
        log.debug("REST request to get PartyType : {}", partyType);
        
        Optional<PartyType> result = partyTypeService.findOneByPartyType(partyType);
        if(result !=null && result.isPresent()) {
        	return customResponse.success("Party type found", result);
        	
        }else 
        {
        	return customResponse.failure(404, "Party type not found");
        	
        }
        
    }

    /**
     * {@code DELETE  /party-types/:id} : delete the "id" partyType.
     *
     * @param id the id of the partyType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
   @ApiOperation(value="Delete specific party types as per Party Type.")
    @DeleteMapping("/partyType/{partyType}")
    public Object  deletePartyType(@PathVariable String partyType) {
        log.debug("REST request to delete PartyType : {}", partyType);
        Optional<PartyType> result = partyTypeService.findOneByPartyType(partyType);
        if(result.isPresent()) {
        	
        	
        	try {
        		partyTypeService.delete(partyType);
				return customResponse.success("party type deleted", null);
			} catch (Exception e) {
				return customResponse.failure(400, "party type not deleted");
			}
        	
        	
        	 
       }else {
    	  return customResponse.failure(404, "party type not found");
       	
       }
    }
           
          
}
