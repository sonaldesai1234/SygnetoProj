package com.sygneto.service;

import com.sygneto.domain.ContactType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ContactType}.
 */
public interface ContactTypeService {

    /**
     * Save a contactType.
     *
     * @param contactType the entity to save.
     * @return the persisted entity.
     */
    ContactType save(ContactType contactType);

    /**
     * Get all the contactTypes.
     *
     * @return the list of entities.
     */
    List<ContactType> findAll();


    /**
     * Get the "id" contactType.
     *
     * @param contactType1 the id of the entity.
     * @return the entity.
     */
    

    /**
     * Delete the "id" contactType.
     *
     * @param contactType the id of the entity.
     */
    void delete(String contactType);

	Optional<ContactType> findOne(String contactType);

	Optional<ContactType> findOneByContactType(String contactType);

	
}
