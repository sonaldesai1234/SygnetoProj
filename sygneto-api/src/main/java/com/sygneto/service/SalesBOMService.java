package com.sygneto.service;

import com.sygneto.domain.SalesBOM;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SalesBOM}.
 */
public interface SalesBOMService {

    /**
     * Save a SalesBOM.
     *
     * @param SalesBOM the entity to save.
     * @return the persisted entity.
     */
	SalesBOM save(SalesBOM salesBom);

    /**
     * Get all the SalesBOM.
     *
     * @return the list of entities.
     */
    List<SalesBOM> findAll();


    /**
     * Get the "id" SalesBOM.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalesBOM> findOne(Long id);

    /**
     * Delete the "id" SalesBOM.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
