package com.sygneto.service;

import com.sygneto.domain.AddressType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AddressType}.
 */
public interface AddressTypeService {

    /**
     * Save a addressType.
     *
     * @param addressType the entity to save.
     * @return the persisted entity.
     */
    AddressType save(AddressType addressType);

    /**
     * Get all the addressTypes.
     *
     * @return the list of entities.
     */
    List<AddressType> findAll();


    /**
     * Get the "id" addressType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressType> findOne(Long id);

    /**
     * Delete the "id" addressType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
