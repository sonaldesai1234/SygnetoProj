package com.sygneto.service;

import com.sygneto.domain.Product;
import com.sygneto.domain.ProductMrp;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Product}.
 */
public interface ProductService {

    /**
     * Save a Product.
     *
     * @param Product the entity to save.
     * @return the persisted entity.
     */
	Product save(Product employee);

    /**
     * Get all the Product.
     *
     * @return the list of entities.
     */
    List<Product> findAll();


    /**
     * Get the "id" Product.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Product> findOne(Long id);

    /**
     * Delete the "id" Product.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<Product> findById(Long id);

	long findCount();

	

	

	
	


	
}
