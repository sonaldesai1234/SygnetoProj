package com.sygneto.web.rest;

import com.sygneto.domain.SalesOrderHistory;
import com.sygneto.service.SalesOrderHistoryService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.sygneto.domain.SalesOrderHistory}.
 */
@RestController
@RequestMapping("/api")
public class SalesOrderHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SalesOrderHistoryResource.class);

    private static final String ENTITY_NAME = "salesOrderHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesOrderHistoryService salesOrderHistoryService;

    public SalesOrderHistoryResource(SalesOrderHistoryService salesOrderHistoryService) {
        this.salesOrderHistoryService = salesOrderHistoryService;
    }

    /**
     * {@code POST  /sales-order-histories} : Create a new salesOrderHistory.
     *
     * @param salesOrderHistory the salesOrderHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesOrderHistory, or with status {@code 400 (Bad Request)} if the salesOrderHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salesOrderHistories")
    public ResponseEntity<SalesOrderHistory> createSalesOrderHistory(@RequestBody SalesOrderHistory salesOrderHistory) throws URISyntaxException {
        log.debug("REST request to save SalesOrderHistory : {}", salesOrderHistory);
        if (salesOrderHistory.getId() != null) {
            throw new BadRequestAlertException("A new salesOrderHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesOrderHistory result = salesOrderHistoryService.save(salesOrderHistory);
        return ResponseEntity.created(new URI("/api/sales-order-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales-order-histories} : Updates an existing salesOrderHistory.
     *
     * @param salesOrderHistory the salesOrderHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesOrderHistory,
     * or with status {@code 400 (Bad Request)} if the salesOrderHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesOrderHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salesOrderHistories")
    public ResponseEntity<SalesOrderHistory> updateSalesOrderHistory(@RequestBody SalesOrderHistory salesOrderHistory) throws URISyntaxException {
        log.debug("REST request to update SalesOrderHistory : {}", salesOrderHistory);
        if (salesOrderHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesOrderHistory result = salesOrderHistoryService.save(salesOrderHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesOrderHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sales-order-histories} : get all the salesOrderHistories.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesOrderHistories in body.
     */
    @GetMapping("/salesOrderHistories")
    public List<SalesOrderHistory> getAllSalesOrderHistories() {
        log.debug("REST request to get all SalesOrderHistories");
        return salesOrderHistoryService.findAll();
    }

    /**
     * {@code GET  /sales-order-histories/:id} : get the "id" salesOrderHistory.
     *
     * @param id the id of the salesOrderHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesOrderHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salesOrderHistories/{id}")
    public ResponseEntity<SalesOrderHistory> getSalesOrderHistory(@PathVariable Long id) {
        log.debug("REST request to get SalesOrderHistory : {}", id);
        Optional<SalesOrderHistory> salesOrderHistory = salesOrderHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesOrderHistory);
    }

    /**
     * {@code DELETE  /sales-order-histories/:id} : delete the "id" salesOrderHistory.
     *
     * @param id the id of the salesOrderHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salesOrderHistories/{id}")
    public ResponseEntity<Void> deleteSalesOrderHistory(@PathVariable Long id) {
        log.debug("REST request to delete SalesOrderHistory : {}", id);
        salesOrderHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
