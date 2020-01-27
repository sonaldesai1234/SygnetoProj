package com.sygneto.service;

import com.sygneto.domain.Contact;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Contact}.
 */
public interface ContactService {

    /**
     * Save a contact.
     *
     * @param contact the entity to save.
     * @return the persisted entity.
     */
    Contact save(Contact contact);

    /**
     * Get all the contacts.
     *
     * @return the list of entities.
     */
    List<Contact> findAll();


    /**
     * Get the "id" contact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Contact> findOne(Long id);

    /**
     * Delete the "id" contact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<Contact> findOneByContactId(Long contactId);

	List<Contact> findAllByPartyId(Long partyId);
}
