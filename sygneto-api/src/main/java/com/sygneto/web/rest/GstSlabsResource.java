package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.GstSlabs;
import com.sygneto.service.GstSlabsService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
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
 * REST controller for managing {@link com.sygneto.domain.GstSlabs}.
 */
@RestController
@RequestMapping("/api")
public class GstSlabsResource {

    private final Logger log = LoggerFactory.getLogger(GstSlabsResource.class);

    private static final String ENTITY_NAME = "gstSlabs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired 
    CustomResponse customResponse;
    private final GstSlabsService gstSlabsService;

    public GstSlabsResource(GstSlabsService gstSlabsService) {
        this.gstSlabsService = gstSlabsService;
    }

    /**
     * {@code POST  /gst-slabs} : Create a new gstSlabs.
     *
     * @param gstSlabs the gstSlabs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gstSlabs, or with status {@code 400 (Bad Request)} if the gstSlabs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
   @ApiIgnore
   @PostMapping("/gstSlabs")
    public Object createGstSlabs(@RequestBody GstSlabs gstSlabs) throws URISyntaxException {
        log.debug("REST request to save GstSlabs : {}", gstSlabs);
        if (gstSlabs.getGstSlabs()<0.0) {
            throw new BadRequestAlertException("A new gstSlabs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GstSlabs result = gstSlabsService.save(gstSlabs);
        return customResponse.success("gst slab added", result);

    }

    /**
     * {@code PUT  /gst-slabs} : Updates an existing gstSlabs.
     *
     * @param gstSlabs the gstSlabs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gstSlabs,
     * or with status {@code 400 (Bad Request)} if the gstSlabs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gstSlabs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
   @ApiIgnore
    @PutMapping("/gstSlabs")
    public Object updateGstSlabs(@RequestBody GstSlabs gstSlabs) throws URISyntaxException {
        log.debug("REST request to update GstSlabs : {}", gstSlabs);
        if (gstSlabs.getGstSlabs() <0.0) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GstSlabs result = gstSlabsService.save(gstSlabs);
        return customResponse.success("Gst slabs updated", result);
  }

    /**
     * {@code GET  /gst-slabs} : get all the gstSlabs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gstSlabs in body.
     */
   @ApiOperation(value="Get All Slab rate")
    @GetMapping("/gstSlabs")
    public List<GstSlabs> getAllGstSlabs() {
        log.debug("REST request to get all GstSlabs");
        return gstSlabsService.findAll();
    }

    /**
     * {@code GET  /gst-slabs/:id} : get the "id" gstSlabs.
     *
     * @param id the id of the gstSlabs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gstSlabs, or with status {@code 404 (Not Found)}.
     */
  @ApiIgnore
    @GetMapping("/gstSlabs/{id}")
    public ResponseEntity<GstSlabs> getGstSlabs(@PathVariable Long id) {
        log.debug("REST request to get GstSlabs : {}", id);
        Optional<GstSlabs> gstSlabs = gstSlabsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gstSlabs);
    }
    /**
     * {@code DELETE  /gst-slabs/:id} : delete the "id" gstSlabs.
     *
     * @param id the id of the gstSlabs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
  /*  @DeleteMapping("/gst-slabs/{id}")
    public ResponseEntity<Void> deleteGstSlabs(@PathVariable Long id) {
        log.debug("REST request to delete GstSlabs : {}", id);
        gstSlabsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }*/
}
