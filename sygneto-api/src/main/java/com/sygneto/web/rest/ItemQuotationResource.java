package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemQuotation;
import com.sygneto.service.ItemQuotationService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.sygneto.domain.ItemQuotation}.
 */
@RestController
@RequestMapping("/api")
public class ItemQuotationResource {

    private final Logger log = LoggerFactory.getLogger(ItemQuotationResource.class);

    private static final String ENTITY_NAME = "itemQuotation";

    @Autowired
    CustomResponse customResponse;
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemQuotationService itemQuotationService;

    
   
    
    public ItemQuotationResource(ItemQuotationService itemQuotationService) {
        this.itemQuotationService = itemQuotationService;
    }

    /**
     * {@code POST  /item-quotations} : Create a new itemQuotation.
     *
     * @param itemQuotation the itemQuotation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemQuotation, or with status {@code 400 (Bad Request)} if the itemQuotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/itemQuotations")
    public Object createItemQuotation(@RequestBody ItemQuotation itemQuotation) throws URISyntaxException {
        log.debug("REST request to save ItemQuotation : {}", itemQuotation);
        if (itemQuotation.getItemQuotationId() != null) {
            throw new BadRequestAlertException("A new itemQuotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
     
        ItemQuotation result = itemQuotationService.save(itemQuotation);
        return customResponse.success("Party type created", result);

    }

    /**
     * {@code PUT  /item-quotations} : Updates an existing itemQuotation.
     *
     * @param itemQuotation the itemQuotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemQuotation,
     * or with status {@code 400 (Bad Request)} if the itemQuotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemQuotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/itemQuotations")
    public ResponseEntity<ItemQuotation> updateItemQuotation(@RequestBody ItemQuotation itemQuotation) throws URISyntaxException {
        log.debug("REST request to update ItemQuotation : {}", itemQuotation);
        if (itemQuotation.getItemQuotationId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemQuotation result = itemQuotationService.save(itemQuotation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemQuotation.getItemQuotationId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-quotations} : get all the itemQuotations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemQuotations in body.
     */
    @GetMapping("/itemQuotations")
    public List<ItemQuotation> getAllItemQuotations() {
        log.debug("REST request to get all ItemQuotations");
    
      
        return itemQuotationService.findAll();
    }

    /**
     * {@code GET  /item-quotations/:id} : get the "id" itemQuotation.
     *
     * @param id the id of the itemQuotation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemQuotation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/itemQuotations/{id}")
    public ResponseEntity<ItemQuotation> getItemQuotation(@PathVariable Long id) {
        log.debug("REST request to get ItemQuotation : {}", id);
        Optional<ItemQuotation> itemQuotation = itemQuotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemQuotation);
    }

    /**
     * {@code DELETE  /item-quotations/:id} : delete the "id" itemQuotation.
     *
     * @param id the id of the itemQuotation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/itemQuotations/{id}")
    public ResponseEntity<Void> deleteItemQuotation(@PathVariable Long id) {
        log.debug("REST request to delete ItemQuotation : {}", id);
        itemQuotationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
