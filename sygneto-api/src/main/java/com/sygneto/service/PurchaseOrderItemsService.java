package com.sygneto.service;

import com.sygneto.domain.PurchaseOrderItems;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PurchaseOrderOld}.
 */
public interface PurchaseOrderItemsService {

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrder the entity to save.
     * @return the persisted entity.
     */
	PurchaseOrderItems save(PurchaseOrderItems purchaseOrder);

    /**
     * Get all the purchaseOrders.
     *
     * @return the list of entities.
     */
    List<PurchaseOrderItems> findAll();


    /**
     * Get the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrderItems> findOne(Long id);

    /**
     * Delete the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
