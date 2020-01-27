package com.sygneto.service.impl;

import com.sygneto.service.NodeLabelService;
import com.sygneto.domain.NodeLabel;
import com.sygneto.repository.NodeLabelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing NodeLabel.
 */
@Service
@Transactional
public class NodeLabelServiceImpl implements NodeLabelService {

    private final Logger log = LoggerFactory.getLogger(NodeLabelServiceImpl.class);

    private final NodeLabelRepository nodeLabelRepository;

    public NodeLabelServiceImpl(NodeLabelRepository nodeLabelRepository) {
        this.nodeLabelRepository = nodeLabelRepository;
    }

    /**
     * Save a nodeLabel.
     *
     * @param nodeLabel the entity to save
     * @return the persisted entity
     */
    @Override
    public NodeLabel save(NodeLabel nodeLabel) {
        log.debug("Request to save NodeLabel : {}", nodeLabel);
        return nodeLabelRepository.save(nodeLabel);
    }

    /**
     * Get all the nodeLabels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NodeLabel> findAll() {
        log.debug("Request to get all NodeLabels");
        return nodeLabelRepository.findAll();
    }


    /**
     * Get one nodeLabel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NodeLabel> findOne(Long id) {
        log.debug("Request to get NodeLabel : {}", id);
        return nodeLabelRepository.findById(id);
    }

    /**
     * Delete the nodeLabel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NodeLabel : {}", id);
        nodeLabelRepository.deleteById(id);
    }
}
