package com.sygneto.service.impl;

import com.sygneto.service.SalesBOMService;
import com.sygneto.domain.SalesBOM;
import com.sygneto.repository.SalesBOMRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SalesBOMService}.
 */
@Service
@Transactional
public class SalesBOMImpl implements SalesBOMService {

    private final Logger log = LoggerFactory.getLogger(SalesBOMImpl.class);

    private final SalesBOMRepository salesBOMRepository;

    public SalesBOMImpl(SalesBOMRepository salesBOMRepository) {
        this.salesBOMRepository = salesBOMRepository;
    }

    /**
     * Save a SalesBOM.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SalesBOM save(SalesBOM salesBOM) {
        log.debug("Request to save salesBOM : {}", salesBOM);
        return salesBOMRepository.save(salesBOM);
    }

    /**
     * Get all the SalesBOM.
     *
     * @return the list of entities.
     */
    @Override
    public List<SalesBOM> findAll() {
        log.debug("Request to get all salesBOM");
        return salesBOMRepository.findAll();
    }


    /**
     * Get one SalesBOM by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SalesBOM> findOne(Long id) {
        log.debug("Request to get salesBOM : {}", id);
        
        return salesBOMRepository.findById(id);
    }

    /**
     * Delete the SalesBOM by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete salesBOM : {}", id);
        salesBOMRepository.deleteById(id);
    }
}
