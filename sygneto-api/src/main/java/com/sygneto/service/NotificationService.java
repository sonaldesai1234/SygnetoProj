package com.sygneto.service;

import com.sygneto.domain.Notification;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Notification}.
 */
public interface NotificationService {

    /**
     * Save a Notification.
     *
     * @param sector the entity to save.
     * @return the persisted entity.
     */
	Notification save(Notification sector);

    /**
     * Get all the Notification.
     *
     * @return the list of entities.
     */
    List<Notification> findAll();


    /**
     * Get the "id" Notification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Notification> findOne(Long id);

    /**
     * Delete the "id" Notification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


}
