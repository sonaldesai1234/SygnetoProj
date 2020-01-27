package com.sygneto.service;

import com.sygneto.domain.BOMItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BOMItem}.
 */
public interface BomItemService {

    /**
     * Save a BOMItem.
     *
     * @param BillOfMaterial the entity to save.
     * @return the persisted entity.
     */
	BOMItem save(BOMItem employee);

    /**
     * Get all the BOMItem.
     *
     * @return the list of entities.
     */
    List<BOMItem> findAll();


    /**
     * Get the "id" BOMItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BOMItem> findOne(Long id);

    /**
     * Delete the "id" BOMItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
