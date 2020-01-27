package com.sygneto.service;

import com.sygneto.domain.NodeLabel;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing NodeLabel.
 */
public interface NodeLabelService {

    /**
     * Save a nodeLabel.
     *
     * @param nodeLabel the entity to save
     * @return the persisted entity
     */
    NodeLabel save(NodeLabel nodeLabel);

    /**
     * Get all the nodeLabels.
     *
     * @return the list of entities
     */
    List<NodeLabel> findAll();


    /**
     * Get the "id" nodeLabel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NodeLabel> findOne(Long id);

    /**
     * Delete the "id" nodeLabel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
