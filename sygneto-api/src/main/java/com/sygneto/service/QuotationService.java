package com.sygneto.service;

import com.sygneto.domain.Quotation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Quotation}.
 */
public interface QuotationService {

    /**
     * Save a quotation.
     *
     * @param quotation the entity to save.
     * @return the persisted entity.
     */
    Quotation save(Quotation quotation);

    /**
     * Get all the quotations.
     *
     * @return the list of entities.
     */
    List<Quotation> findAll();


    /**
     * Get the "id" quotation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Quotation> findOne(Long id);

    /**
     * Delete the "id" quotation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	
}
