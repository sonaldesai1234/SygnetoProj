package com.sygneto.service;

import com.sygneto.domain.GstSlabs;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GstSlabs}.
 */
public interface GstSlabsService {

    /**
     * Save a gstSlabs.
     *
     * @param gstSlabs the entity to save.
     * @return the persisted entity.
     */
    GstSlabs save(GstSlabs gstSlabs);

    /**
     * Get all the gstSlabs.
     *
     * @return the list of entities.
     */
    List<GstSlabs> findAll();


    /**
     * Get the "id" gstSlabs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GstSlabs> findOne(Long id);

    /**
     * Delete the "id" gstSlabs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
