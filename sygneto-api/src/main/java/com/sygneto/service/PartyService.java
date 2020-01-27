package com.sygneto.service;

import com.sygneto.domain.Party;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Party}.
 */
public interface PartyService {

    /**
     * Save a party.
     *
     * @param party the entity to save.
     * @return the persisted entity.
     */
    Party save(Party party);

    /**
     * Get all the parties.
     *
     * @return the list of entities.
     */
    List<Party> findAll();


    /**
     * Get the "id" party.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Party> findOne(Long id);

    /**
     * Delete the "id" party.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<Party> findOneByPartyId(Long partyId);

	Optional<Party> findOneByPartyName(String partyName);

	List<Party> findAllByIsCustomer(String isCustomer);

	Long countByPartyId();

	Long countByIsSupplier();

	Long countByIsCustomer();	

}
