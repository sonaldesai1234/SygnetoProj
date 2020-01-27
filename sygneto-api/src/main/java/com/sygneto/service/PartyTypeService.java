package com.sygneto.service;

import com.sygneto.domain.PartyType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PartyType}.
 */
public interface PartyTypeService {

    /**
     * Save a partyType.
     *
     * @param partyType the entity to save.
     * @return the persisted entity.
     */
    PartyType save(PartyType partyType);

    /**
     * Get all the partyTypes.
     *
     * @return the list of entities.
     */
    
    List<PartyType> findAll();


    /**
     * Get the "id" partyType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartyType> findOne(Long id);

    /**
     * Delete the "id" partyType.
     *
     * @param partyType the id of the entity.
     */
    void delete(String partyType);

	Optional<PartyType> findOneByPartyType(String partyType);

	Long countByPartyType();


}
