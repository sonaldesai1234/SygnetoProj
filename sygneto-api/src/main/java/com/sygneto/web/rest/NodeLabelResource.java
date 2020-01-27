package com.sygneto.web.rest;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sygneto.domain.NodeLabel;
import com.sygneto.service.NodeLabelService;
import com.sygneto.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.ResponseUtil;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing NodeLabel.
 */
@ApiIgnore
@RestController
@RequestMapping("/api")
public class NodeLabelResource {

    private final Logger log = LoggerFactory.getLogger(NodeLabelResource.class);

    private static final String ENTITY_NAME = "nodeLabel";

    private final NodeLabelService nodeLabelService;

    public NodeLabelResource(NodeLabelService nodeLabelService) {
        this.nodeLabelService = nodeLabelService;
    }

    /**
     * POST  /node-labels : Create a new nodeLabel.
     *
     * @param nodeLabel the nodeLabel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nodeLabel, or with status 400 (Bad Request) if the nodeLabel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/node-labels")
    public NodeLabel createNodeLabel(@RequestBody NodeLabel nodeLabel) throws URISyntaxException {
        log.debug("REST request to save NodeLabel : {}", nodeLabel);
        if (nodeLabel.getId() != null) {
            throw new BadRequestAlertException("A new nodeLabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NodeLabel result = nodeLabelService.save(nodeLabel);
        return result;
    }

    /**
     * PUT  /node-labels : Updates an existing nodeLabel.
     *
     * @param nodeLabel the nodeLabel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nodeLabel,
     * or with status 400 (Bad Request) if the nodeLabel is not valid,
     * or with status 500 (Internal Server Error) if the nodeLabel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/node-labels")
    public NodeLabel updateNodeLabel(@RequestBody NodeLabel nodeLabel) throws URISyntaxException {
        log.debug("REST request to update NodeLabel : {}", nodeLabel);
        if (nodeLabel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NodeLabel result = nodeLabelService.save(nodeLabel);
        return result;
    }

    /**
     * GET  /node-labels : get all the nodeLabels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nodeLabels in body
     */
    @GetMapping("/node-labels")
    public List<NodeLabel> getAllNodeLabels() {
        log.debug("REST request to get all NodeLabels");
        return nodeLabelService.findAll();
    }

    /**
     * GET  /node-labels/:id : get the "id" nodeLabel.
     *
     * @param id the id of the nodeLabel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nodeLabel, or with status 404 (Not Found)
     */
    @GetMapping("/node-labels/{id}")
    public ResponseEntity<NodeLabel> getNodeLabel(@PathVariable Long id) {
        log.debug("REST request to get NodeLabel : {}", id);
        Optional<NodeLabel> nodeLabel = nodeLabelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nodeLabel);
    }

    /**
     * DELETE  /node-labels/:id : delete the "id" nodeLabel.
     *
     * @param id the id of the nodeLabel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/node-labels/{id}")
    public ResponseEntity<Void> deleteNodeLabel(@PathVariable Long id) {
        log.debug("REST request to delete NodeLabel : {}", id);
        nodeLabelService.delete(id);
        return null;
    }
}
