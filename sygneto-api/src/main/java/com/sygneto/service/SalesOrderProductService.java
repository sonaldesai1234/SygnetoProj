package com.sygneto.service;

import com.sygneto.domain.SalesOrderProduct;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SalesOrderProduct}.
 */
public interface SalesOrderProductService {

    /**
     * Save a salesOrderProduct.
     *
     * @param salesOrderProduct the entity to save.
     * @return the persisted entity.
     */
    SalesOrderProduct save(SalesOrderProduct salesOrderProduct);

    /**
     * Get all the salesOrderProducts.
     *
     * @return the list of entities.
     */
    List<SalesOrderProduct> findAll();


    /**
     * Get the "id" salesOrderProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalesOrderProduct> findOne(Long id);

    /**
     * Delete the "id" salesOrderProduct.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
