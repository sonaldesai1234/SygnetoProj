package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.MandatoryAddress;
import com.sygneto.service.MandatoryAddressService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import springfox.documentation.annotations.ApiIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.MandatoryAddress}.
 */
@RestController
@RequestMapping("/api")
@ApiIgnore
public class MandatoryAddressResource {

    private final Logger log = LoggerFactory.getLogger(MandatoryAddressResource.class);

    private static final String ENTITY_NAME = "mandatoryAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
	CustomResponse customeResponce;
    private final MandatoryAddressService mandatoryAddressService;

    public MandatoryAddressResource(MandatoryAddressService mandatoryAddressService) {
        this.mandatoryAddressService = mandatoryAddressService;
    }

    /**
     * {@code POST  /mandatory-addresses} : Create a new mandatoryAddress.
     *
     * @param mandatoryAddress the mandatoryAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mandatoryAddress, or with status {@code 400 (Bad Request)} if the mandatoryAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mandatory-addresses")
    public ResponseEntity<MandatoryAddress> createMandatoryAddress(@RequestBody MandatoryAddress mandatoryAddress) throws URISyntaxException {
        log.debug("REST request to save MandatoryAddress : {}", mandatoryAddress);
        if (mandatoryAddress.getMandatoryAddressId() != null) {
            throw new BadRequestAlertException("A new mandatoryAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MandatoryAddress result = mandatoryAddressService.save(mandatoryAddress);
        return ResponseEntity.created(new URI("/api/mandatory-addresses/" + result.getMandatoryAddressId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getMandatoryAddressId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mandatory-addresses} : Updates an existing mandatoryAddress.
     *
     * @param mandatoryAddress the mandatoryAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandatoryAddress,
     * or with status {@code 400 (Bad Request)} if the mandatoryAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mandatoryAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mandatory-addresses")
    public ResponseEntity<MandatoryAddress> updateMandatoryAddress(@RequestBody MandatoryAddress mandatoryAddress) throws URISyntaxException {
        log.debug("REST request to update MandatoryAddress : {}", mandatoryAddress);
        if (mandatoryAddress.getMandatoryAddressId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MandatoryAddress result = mandatoryAddressService.save(mandatoryAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mandatoryAddress.getMandatoryAddressId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mandatory-addresses} : get all the mandatoryAddresses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mandatoryAddresses in body.
     */
    @GetMapping("/mandatory-addresses")
    public List<MandatoryAddress> getAllMandatoryAddresses() {
        log.debug("REST request to get all MandatoryAddresses");
        return mandatoryAddressService.findAll();
    }

    /**
     * {@code GET  /mandatory-addresses/:id} : get the "id" mandatoryAddress.
     *
     * @param id the id of the mandatoryAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mandatoryAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mandatory-addresses/{id}")
    public ResponseEntity<MandatoryAddress> getMandatoryAddress(@PathVariable Long id) {
        log.debug("REST request to get MandatoryAddress : {}", id);
        Optional<MandatoryAddress> mandatoryAddress = mandatoryAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mandatoryAddress);
    }

    /**
     * {@code DELETE  /mandatory-addresses/:id} : delete the "id" mandatoryAddress.
     *
     * @param id the id of the mandatoryAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mandatory-addresses/{id}")
    public ResponseEntity<Void> deleteMandatoryAddress(@PathVariable Long id) {
        log.debug("REST request to delete MandatoryAddress : {}", id);
        mandatoryAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
