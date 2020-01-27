package com.sygneto.service;

import com.sygneto.domain.Children;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Children.
 */
public interface ChildrenService {

    /**
     * Save a children.
     *
     * @param children the entity to save
     * @return the persisted entity
     */
    Children save(Children children);

    /**
     * Get all the children.
     *
     * @return the list of entities
     */
    List<Children> findAll();


    /**
     * Get the "id" children.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Children> findOne(Long id);

    /**
     * Delete the "id" children.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	void deleteByNextAction(long parseLong);
}
