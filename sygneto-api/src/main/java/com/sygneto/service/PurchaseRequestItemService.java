package com.sygneto.service;

import com.sygneto.domain.PurchaseRequestItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PurchaseRequestItem}.
 */
public interface PurchaseRequestItemService {

    /**
     * Save a purchaseRequestItem.
     *
     * @param purchaseRequestItem the entity to save.
     * @return the persisted entity.
     */
    PurchaseRequestItem save(PurchaseRequestItem purchaseRequestItem);

    /**
     * Get all the purchaseRequestItems.
     *
     * @return the list of entities.
     */
    List<PurchaseRequestItem> findAll();


    /**
     * Get the "id" purchaseRequestItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseRequestItem> findOne(Long id);

    /**
     * Delete the "id" purchaseRequestItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
