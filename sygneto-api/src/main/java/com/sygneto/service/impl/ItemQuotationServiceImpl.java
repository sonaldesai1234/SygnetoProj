package com.sygneto.service.impl;

import com.sygneto.service.ItemQuotationService;
import com.sygneto.domain.ItemQuotation;
import com.sygneto.repository.ItemQuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemQuotation}.
 */
@Service
@Transactional
public class ItemQuotationServiceImpl implements ItemQuotationService {

    private final Logger log = LoggerFactory.getLogger(ItemQuotationServiceImpl.class);

    private final ItemQuotationRepository itemQuotationRepository;

    public ItemQuotationServiceImpl(ItemQuotationRepository itemQuotationRepository) {
        this.itemQuotationRepository = itemQuotationRepository;
    }

    /**
     * Save a itemQuotation.
     *
     * @param itemQuotation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemQuotation save(ItemQuotation itemQuotation) {
        log.debug("Request to save ItemQuotation : {}", itemQuotation);
        return itemQuotationRepository.save(itemQuotation);
    }

    /**
     * Get all the itemQuotations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemQuotation> findAll() {
        log.debug("Request to get all ItemQuotations");
        return itemQuotationRepository.findAll();
    }


    /**
     * Get one itemQuotation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemQuotation> findOne(Long id) {
        log.debug("Request to get ItemQuotation : {}", id);
        return itemQuotationRepository.findById(id);
    }

    /**
     * Delete the itemQuotation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemQuotation : {}", id);
        itemQuotationRepository.deleteById(id);
    }
}
