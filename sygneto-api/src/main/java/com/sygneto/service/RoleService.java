package com.sygneto.service;

import com.sygneto.domain.Role;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Role.
 */
public interface RoleService {

    /**
     * Save a role.
     *
     * @param role the entity to save
     * @return the persisted entity
     */
    Role save(Role role);

    /**
     * Get all the roles.
     *
     * @return the list of entities
     */
    List<Role> findAll();


    /**
     * Get the "id" role.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Role> findOne(Long id);

    /**
     * Delete the "id" role.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
