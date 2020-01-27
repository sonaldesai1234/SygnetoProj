package com.sygneto.service;

import com.sygneto.domain.ProductMRPHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProductMRPHistory}.
 */
public interface ProductMRPHistoryService {

    /**
     * Save a productMRPHistory.
     *
     * @param productMRPHistory the entity to save.
     * @return the persisted entity.
     */
    ProductMRPHistory save(ProductMRPHistory productMRPHistory);

    /**
     * Get all the productMRPHistories.
     *
     * @return the list of entities.
     */
    List<ProductMRPHistory> findAll();


    /**
     * Get the "id" productMRPHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductMRPHistory> findOne(Long id);

    /**
     * Delete the "id" productMRPHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	//Optional<ProductMRPHistory> findByProductId(Long productId);

	List<ProductMRPHistory> findAllByProductId(Long productId);

	

	
	
}
