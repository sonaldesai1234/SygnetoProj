package com.sygneto.web.rest;

import com.sygneto.domain.CustomResponse;
import com.sygneto.domain.Item;
import com.sygneto.domain.TaskMgt;
import com.sygneto.service.TaskMgtService;
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
 * REST controller for managing {@link com.sygneto.domain.TaskMgt}.
 */
@RestController
@RequestMapping("/api")
public class TaskMgtResource {

    private final Logger log = LoggerFactory.getLogger(TaskMgtResource.class);

    private static final String ENTITY_NAME = "taskMgt";

    @Autowired
    CustomResponse customResponse;
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskMgtService taskMgtService;

    
   
    
    public TaskMgtResource(TaskMgtService itemQuotationService) {
        this.taskMgtService = itemQuotationService;
    }

    /**
     * {@code POST  /item-quotations} : Create a new taskMgt.
     *
     * @param taskMgt the taskMgt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemQuotation, or with status {@code 400 (Bad Request)} if the itemQuotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taskMgt")
    public Object createItemQuotation(@RequestBody TaskMgt taskMgt) throws URISyntaxException {
        log.debug("REST request to save taskMgt : {}", taskMgt);
        if (taskMgt.getTaskId() != null) {
            throw new BadRequestAlertException("A new taskMgt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        TaskMgt result = taskMgtService.save(taskMgt);
        return customResponse.success("task created", result);

    }

    /**
     * {@code PUT  /item-quotations} : Updates an existing taskMgt.
     *
     * @param itemQuotation the taskMgt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskMgt,
     * or with status {@code 400 (Bad Request)} if the itemQuotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskMgt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taskMgt")
    public ResponseEntity<TaskMgt> updateItemQuotation(@RequestBody TaskMgt taskMgt) throws URISyntaxException {
        log.debug("REST request to update ItemQuotation : {}", taskMgt);
        if (taskMgt.getTaskId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskMgt result = taskMgtService.save(taskMgt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskMgt.getTaskId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-quotations} : get all the taskMgt.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskMgt in body.
     */
    @GetMapping("/taskMgt")
    public List<TaskMgt> getAllItemQuotations() {
        log.debug("REST request to get all taskMgt");
    
      
        return taskMgtService.findAll();
    }

    /**
     * {@code GET  /item-quotations/:id} : get the "id" taskMgt.
     *
     * @param id the id of the taskMgt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskMgt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taskMgt/{id}")
    public ResponseEntity<TaskMgt> getItemQuotation(@PathVariable Long id) {
        log.debug("REST request to get taskMgt : {}", id);
        Optional<TaskMgt> taskMgt = taskMgtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskMgt);
    }

    /**
     * {@code DELETE  /item-quotations/:id} : delete the "id" taskMgt.
     *
     * @param id the id of the taskMgt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taskMgt/{id}")
    public ResponseEntity<Void> deleteItemQuotation(@PathVariable Long id) {
        log.debug("REST request to delete taskMgt : {}", id);
        taskMgtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
