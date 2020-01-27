package com.sygneto.service;

import com.sygneto.domain.Address;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Address}.
 */
public interface AddressService {

    /**
     * Save a Address.
     *
     * @param Address the entity to save.
     * @return the persisted entity.
     */
	Address save(Address address);

    /**
     * Get all the Address.
     *
     * @return the list of entities.
     */
    List<Address> findAll();


    /**
     * Get the "id" Address.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Address> findOne(Long id);

    /**
     * Delete the "id" Address.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<Address> findAllByPartyId(Long partyId);

	Optional<Address> findOneByPartId(Object party);
}
