package com.sygneto.web.rest;

import com.sygneto.domain.PurchaseRequestItem;
import com.sygneto.service.PurchaseRequestItemService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import springfox.documentation.annotations.ApiIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sygneto.domain.PurchaseRequestItem}.
 */
@ApiIgnore
@RestController
@RequestMapping("/api")
public class PurchaseRequestItemResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseRequestItemResource.class);

    private static final String ENTITY_NAME = "purchaseRequestItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseRequestItemService purchaseRequestItemService;

    public PurchaseRequestItemResource(PurchaseRequestItemService purchaseRequestItemService) {
        this.purchaseRequestItemService = purchaseRequestItemService;
    }

    /**
     * {@code POST  /purchase-request-items} : Create a new purchaseRequestItem.
     *
     * @param purchaseRequestItem the purchaseRequestItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseRequestItem, or with status {@code 400 (Bad Request)} if the purchaseRequestItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-request-items")
    public ResponseEntity<PurchaseRequestItem> createPurchaseRequestItem(@RequestBody PurchaseRequestItem purchaseRequestItem) throws URISyntaxException {
        log.debug("REST request to save PurchaseRequestItem : {}", purchaseRequestItem);
        if (purchaseRequestItem.getPurchaseRequestItemId() != null) {
            throw new BadRequestAlertException("A new purchaseRequestItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseRequestItem result = purchaseRequestItemService.save(purchaseRequestItem);
        return ResponseEntity.created(new URI("/api/purchase-request-items/" + result.getPurchaseRequestItemId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getPurchaseRequestItemId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-request-items} : Updates an existing purchaseRequestItem.
     *
     * @param purchaseRequestItem the purchaseRequestItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseRequestItem,
     * or with status {@code 400 (Bad Request)} if the purchaseRequestItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseRequestItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-request-items")
    public ResponseEntity<PurchaseRequestItem> updatePurchaseRequestItem(@RequestBody PurchaseRequestItem purchaseRequestItem) throws URISyntaxException {
        log.debug("REST request to update PurchaseRequestItem : {}", purchaseRequestItem);
        if (purchaseRequestItem.getPurchaseRequestItemId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseRequestItem result = purchaseRequestItemService.save(purchaseRequestItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purchaseRequestItem.getPurchaseRequestItemId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-request-items} : get all the purchaseRequestItems.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseRequestItems in body.
     */
    @GetMapping("/purchase-request-items")
    public List<PurchaseRequestItem> getAllPurchaseRequestItems() {
        log.debug("REST request to get all PurchaseRequestItems");
        return purchaseRequestItemService.findAll();
    }

    /**
     * {@code GET  /purchase-request-items/:id} : get the "id" purchaseRequestItem.
     *
     * @param id the id of the purchaseRequestItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseRequestItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-request-items/{id}")
    public ResponseEntity<PurchaseRequestItem> getPurchaseRequestItem(@PathVariable Long id) {
        log.debug("REST request to get PurchaseRequestItem : {}", id);
        Optional<PurchaseRequestItem> purchaseRequestItem = purchaseRequestItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseRequestItem);
    }

    /**
     * {@code DELETE  /purchase-request-items/:id} : delete the "id" purchaseRequestItem.
     *
     * @param id the id of the purchaseRequestItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-request-items/{id}")
    public ResponseEntity<Void> deletePurchaseRequestItem(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseRequestItem : {}", id);
        purchaseRequestItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
