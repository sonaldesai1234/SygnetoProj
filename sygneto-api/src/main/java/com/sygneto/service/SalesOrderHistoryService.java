package com.sygneto.service;

import com.sygneto.domain.SalesOrderHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SalesOrderHistory}.
 */
public interface SalesOrderHistoryService {

    /**
     * Save a salesOrderHistory.
     *
     * @param salesOrderHistory the entity to save.
     * @return the persisted entity.
     */
    SalesOrderHistory save(SalesOrderHistory salesOrderHistory);

    /**
     * Get all the salesOrderHistories.
     *
     * @return the list of entities.
     */
    List<SalesOrderHistory> findAll();


    /**
     * Get the "id" salesOrderHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalesOrderHistory> findOne(Long id);

    /**
     * Delete the "id" salesOrderHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
