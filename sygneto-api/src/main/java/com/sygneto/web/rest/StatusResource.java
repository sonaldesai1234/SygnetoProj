package com.sygneto.web.rest;

import com.sygneto.domain.Status;
import com.sygneto.service.StatusService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
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
 * REST controller for managing {@link com.sygneto.domain.Status}.
 */
@RestController
@RequestMapping("/api")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    private static final String ENTITY_NAME = "status";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusService statusService;

    public StatusResource(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * {@code POST  /statuses} : Create a new status.
     *
     * @param status the status to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new status, or with status {@code 400 (Bad Request)} if the status has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @ApiIgnore
    @PostMapping("/statuses")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) throws URISyntaxException {
        log.debug("REST request to save Status : {}", status);
      /*  if (status.getStatus() != null) {
            throw new BadRequestAlertException("A new status cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        Status result = statusService.save(status);
        return ResponseEntity.created(new URI("/api/statuses/" + result.getStatus()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getStatus().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statuses} : Updates an existing status.
     *
     * @param status the status to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated status,
     * or with status {@code 400 (Bad Request)} if the status is not valid,
     * or with status {@code 500 (Internal Server Error)} if the status couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @ApiIgnore
    @PutMapping("/statuses")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status) throws URISyntaxException {
        log.debug("REST request to update Status : {}", status);
        if (status.getStatus() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Status result = statusService.save(status);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, status.getStatus().toString()))
            .body(result);
    }
	@ApiOperation(value="Get all sales order status")
    @GetMapping("/statuses")
    public List<Status> getAllStatuses() {
        log.debug("REST request to get all Statuses");
        return statusService.findAll();
    }

    /**
     * {@code GET  /statuses/:id} : get the "id" status.
     *
     * @param id the id of the status to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the status, or with status {@code 404 (Not Found)}.
     */
	@ApiOperation(value="Get all sales order status as per status id")
    @GetMapping("/statuses/{id}")
    public ResponseEntity<Status> getStatus(@PathVariable Long id) {
        log.debug("REST request to get Status : {}", id);
        Optional<Status> status = statusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(status);
    }

    /**
     * {@code DELETE  /statuses/:id} : delete the "id" status.
     *
     * @param id the id of the status to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
   /* @DeleteMapping("/statuses/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        log.debug("REST request to delete Status : {}", id);
        statusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }*/
}
