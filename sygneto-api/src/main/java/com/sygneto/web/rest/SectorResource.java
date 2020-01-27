package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.PartyType;
import com.sygneto.domain.Sector;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.security.AuthoritiesConstants;
import com.sygneto.service.SectorService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.Sector}.
 */
@RestController
@RequestMapping("/api")
public class SectorResource {

    private final Logger log = LoggerFactory.getLogger(SectorResource.class);

    private static final String ENTITY_NAME = "sector";


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SectorService sectorService;

    public SectorResource(SectorService sectorService) {
        this.sectorService = sectorService;
    }
    @Autowired
	CustomResponse customeResponce;
	 


    /**
     * {@code POST  /sectors} : Create a new sector.
     *
     * @param sector the sector to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sector, or with status {@code 400 (Bad Request)} if the sector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sector")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object createSector(@RequestBody Sector sector) throws URISyntaxException {
        log.debug("REST request to save Sector : {}", sector);
      
        Sector result = sectorService.save(sector);
        return customeResponce.success("Sector created", result);
        
    }

    /**
     * {@code PUT  /sectors} : Updates an existing sector.
     *
     * @param sector the sector to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sector,
     * or with status {@code 400 (Bad Request)} if the sector is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sector couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sector")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object updateSector(@RequestBody Sector sector) throws URISyntaxException {
        log.debug("REST request to update Sector : {}", sector);
        CustomResponse customResponse=new CustomResponse();
        Sector result = sectorService.save(sector);
        return customeResponce.success("Sector updated", result);
        
       
    }

    /**
     * {@code GET  /sectors} : get all the sectors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sectors in body.
     */
    @GetMapping("/sector")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getAllSectors() {
        log.debug("REST request to get all Sectors");
        List<Sector>result= sectorService.findAll();
        if(result !=null && !result.isEmpty()) {
        	return customeResponce.success("Sector found", result);
        
        }else {
        	return customeResponce.failure(404, "Sector not found");
        
        }
		
    }

    /**
     * {@code GET  /sectors/:id} : get the "id" sector.
     *
     * @param id the id of the sector to retrieve.
     * @return 
     */
    @GetMapping("/sector/{sectorName}")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object getSector(@PathVariable String sectorName){
        log.debug("REST request to get Sector : {}", sectorName);
        Optional<Sector> result = sectorService.findOne(sectorName);
        if(result !=null && result.isPresent()) {
        	return customeResponce.success("Sector found", result);
        
        }else {
        	return customeResponce.failure(404, "Sector not found");
        
        }
	
    }


    /**
     * {@code DELETE  /sectors/:id} : delete the "id" sector.
     *
     * @param id the id of the sector to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sector/{sectorName}")
    @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public Object deleteSector(@PathVariable String sectorName) {
        log.debug("REST request to delete Sector : {}", sectorName);
        Optional<Sector> result = sectorService.findOne(sectorName);
        if(result !=null  && result.isPresent()) {
        	
        	
        	try {
        		sectorService.delete(sectorName);
				return customeResponce.success("Sector deleted", null);
			} catch (Exception e) {
				return customeResponce.failure(400, "Sector not deleted");
			}
        	
        	
       }else {
    	   return customeResponce.failure(404, "Sector not found");
       
       }
    }
}
