package com.sygneto.service;

import com.sygneto.domain.ItemQuotation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ItemQuotation}.
 */
public interface ItemQuotationService {

    /**
     * Save a itemQuotation.
     *
     * @param itemQuotation the entity to save.
     * @return the persisted entity.
     */
    ItemQuotation save(ItemQuotation itemQuotation);

    /**
     * Get all the itemQuotations.
     *
     * @return the list of entities.
     */
    List<ItemQuotation> findAll();


    /**
     * Get the "id" itemQuotation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemQuotation> findOne(Long id);

    /**
     * Delete the "id" itemQuotation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
