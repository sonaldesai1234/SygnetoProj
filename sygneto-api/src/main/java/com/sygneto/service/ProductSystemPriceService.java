package com.sygneto.service;

import com.sygneto.domain.ProductMrp;
import com.sygneto.domain.ProductSystemPrice;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProductSystemPrice}.
 */
public interface ProductSystemPriceService {

    /**
     * Save a productSystemPrice.
     *
     * @param productSystemPrice the entity to save.
     * @return the persisted entity.
     */
    ProductSystemPrice save(ProductSystemPrice productSystemPrice);

    /**
     * Get all the productSystemPrices.
     *
     * @return the list of entities.
     */
    List<ProductSystemPrice> findAll();


    /**
     * Get the "id" productSystemPrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSystemPrice> findOne(Long id);

    /**
     * Delete the "id" productSystemPrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<ProductSystemPrice> findById(Long id);
}
