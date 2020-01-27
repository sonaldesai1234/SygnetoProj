package com.sygneto.service;

import com.sygneto.domain.ItemCategory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Employee}.
 */
public interface ItemCategoryService {

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
	ItemCategory save(ItemCategory itemCategory);

    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
    List<ItemCategory> findAll();


    /**
     * Get the "id" employee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemCategory> findOne(Long id);

    /**
     * Delete the "id" employee.
     *
     * @param itemcat the id of the entity.
     */

	void delete(Long id);
}
