package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Item;
import com.sygneto.domain.ActivityMgt;
import com.sygneto.service.ActivityMgtService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;

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
 * REST controller for managing {@link com.sygneto.domain.ActivityMgt}.
 */
@RestController
@RequestMapping("/api")
public class ActivityMgtResource {

    private final Logger log = LoggerFactory.getLogger(ActivityMgtResource.class);

    private static final String ENTITY_NAME = "ActivityMgt";

    @Autowired
    CustomResponse customResponse;
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityMgtService activityMgtService;

    
    public ActivityMgtResource(ActivityMgtService activityMgtService) {
        this.activityMgtService = activityMgtService;
    }

    /**
     * {@code POST  /-activityMgt} : Create a new activityMgt.
     *
     * @param taskMgt the activityMgt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityMgt, or with status {@code 400 (Bad Request)} if the itemQuotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activityMgt")
    @ApiOperation(value="Create Activity")
    public Object createItemQuotation(@RequestBody ActivityMgt activityMgt) throws URISyntaxException {
        log.debug("REST request to save activityMgt : {}", activityMgt);
        if (activityMgt.getActivityId() != null) {
            throw new BadRequestAlertException("A new activityMgt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        ActivityMgt result = activityMgtService.save(activityMgt);
        return customResponse.success("activity created", result);

    }

    /**
     * {@code PUT  /-activityMgt} : Updates an existing activityMgt.
     *
     * @param itemQuotation the activityMgt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityMgt,
     * or with status {@code 400 (Bad Request)} if the activityMgt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityMgt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activityMgt")
    @ApiOperation(value="Update Activity")
    public ResponseEntity<ActivityMgt> updateItemQuotation(@RequestBody ActivityMgt activityMgt) throws URISyntaxException {
        log.debug("REST request to update activityMgt : {}", activityMgt);
        if (activityMgt.getActivityId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityMgt result = activityMgtService.save(activityMgt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activityMgt.getActivityId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /-activityMgt} : get all the activityMgt.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityMgt in body.
     */
    @GetMapping("/activityMgt")
    @ApiOperation(value="Get All Activities")

    public List<ActivityMgt> getAllItemQuotations() {
        log.debug("REST request to get all activityMgt");
    
        return activityMgtService.findAll();
    }

    /**
     * {@code GET  /activityMgt-/:id} : get the "id" activityMgt.
     *
     * @param id the id of the activityMgt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskMgt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activityMgt/{id}")
    @ApiOperation(value="Get Specific Activities/")
    public ResponseEntity<ActivityMgt> getItemQuotation(@PathVariable Long id) {
        log.debug("REST request to get activityMgt : {}", id);
        Optional<ActivityMgt> taskMgt = activityMgtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskMgt);
    }

    @GetMapping("/getActivityBySO/{id}")
    @ApiOperation(value="Get Specific Activities for Sales Order")
    public ResponseEntity<List<ActivityMgt>> getActivityBySO(@PathVariable Long id) {
        log.debug("REST request to get all activityMgt", id);
        List<ActivityMgt> result= activityMgtService.findByReferenceId(id);
        System.out.println("@@@@@@@@"+result);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,true, "", "")).body(result);
    }
    
    /**
     * {@code DELETE  /-activityMgt/:id} : delete the "id" activityMgt.
     *
     * @param id the id of the activityMgt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activityMgt/{id}")
    @ApiOperation(value="Delete Specific Activities")
    public ResponseEntity<Void> deleteItemQuotation(@PathVariable Long id) {
        log.debug("REST request to delete activityMgt : {}", id);
        activityMgtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
