package com.sygneto.service;

import com.sygneto.domain.MandatoryAddress;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MandatoryAddress}.
 */
public interface MandatoryAddressService {

    /**
     * Save a mandatoryAddress.
     *
     * @param mandatoryAddress the entity to save.
     * @return the persisted entity.
     */
    MandatoryAddress save(MandatoryAddress mandatoryAddress);

    /**
     * Get all the mandatoryAddresses.
     *
     * @return the list of entities.
     */
    List<MandatoryAddress> findAll();


    /**
     * Get the "id" mandatoryAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MandatoryAddress> findOne(Long id);

    /**
     * Delete the "id" mandatoryAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
