package com.sygneto.service;

import com.sygneto.domain.TaskMgt;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TaskMgt}.
 */
public interface TaskMgtService {

    /**
     * Save a TaskMgt.
     *
     * @param itemQuotation the entity to save.
     * @return the persisted entity.
     */
	TaskMgt save(TaskMgt itemQuotation);

    /**
     * Get all the TaskMgt.
     *
     * @return the list of entities.
     */
    List<TaskMgt> findAll();


    /**
     * Get the "id" TaskMgt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskMgt> findOne(Long id);

    /**
     * Delete the "id" itemQuotation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
