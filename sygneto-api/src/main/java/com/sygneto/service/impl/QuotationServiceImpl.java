package com.sygneto.service.impl;

import com.sygneto.service.QuotationService;
import com.sygneto.domain.Quotation;
import com.sygneto.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Quotation}.
 */
@Service
@Transactional
public class QuotationServiceImpl implements QuotationService {

    private final Logger log = LoggerFactory.getLogger(QuotationServiceImpl.class);

    private final QuotationRepository quotationRepository;

    public QuotationServiceImpl(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    /**
     * Save a quotation.
     *
     * @param quotation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Quotation save(Quotation quotation) {
        log.debug("Request to save Quotation : {}", quotation);
        return quotationRepository.save(quotation);
    }

    /**
     * Get all the quotations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Quotation> findAll() {
        log.debug("Request to get all Quotations");
        return quotationRepository.findAll();
    }


    /**
     * Get one quotation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Quotation> findOne(Long id) {
        log.debug("Request to get Quotation : {}", id);
        return quotationRepository.findById(id);
    }

    /**
     * Delete the quotation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quotation : {}", id);
        quotationRepository.deleteById(id);
    }

	
}
