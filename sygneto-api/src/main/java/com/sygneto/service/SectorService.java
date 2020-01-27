package com.sygneto.service;

import com.sygneto.domain.Sector;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Sector}.
 */
public interface SectorService {

    /**
     * Save a sector.
     *
     * @param sector the entity to save.
     * @return the persisted entity.
     */
    Sector save(Sector sector);

    /**
     * Get all the sectors.
     *
     * @return the list of entities.
     */
    List<Sector> findAll();


    /**
     * Get the "id" sector.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sector> findOne(String id);

    /**
     * Delete the "id" sector.
     *
     * @param id the id of the entity.
     */
    void delete(String id);


}
