package com.sygneto.service;

import com.sygneto.domain.ActivityMgt;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TaskMgt}.
 */
public interface ActivityMgtService {

    /**
     * Save a ActivityMgt.
     *
     * @param ActivityMgt the entity to save.
     * @return the persisted entity.
     */
	ActivityMgt save(ActivityMgt activityMgt);

    /**
     * Get all the ActivityMgt.
     *
     * @return the list of entities.
     */
    List<ActivityMgt> findAll();


    /**
     * Get the "id" ActivityMgt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityMgt> findOne(Long id);

    /**
     * Delete the "id" ActivityMgt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


	List<ActivityMgt> findByReferenceId(Long id);
}
