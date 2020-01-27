package com.sygneto.service.impl;

import com.sygneto.service.SalesOrderProductService;
import com.sygneto.domain.SalesOrderProduct;
import com.sygneto.repository.SalesOrderProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SalesOrderProduct}.
 */
@Service
@Transactional
public class SalesOrderProductServiceImpl implements SalesOrderProductService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderProductServiceImpl.class);

    private final SalesOrderProductRepository salesOrderProductRepository;

    public SalesOrderProductServiceImpl(SalesOrderProductRepository salesOrderProductRepository) {
        this.salesOrderProductRepository = salesOrderProductRepository;
    }

    /**
     * Save a salesOrderProduct.
     *
     * @param salesOrderProduct the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SalesOrderProduct save(SalesOrderProduct salesOrderProduct) {
        log.debug("Request to save SalesOrderProduct : {}", salesOrderProduct);
        return salesOrderProductRepository.save(salesOrderProduct);
    }

    /**
     * Get all the salesOrderProducts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SalesOrderProduct> findAll() {
        log.debug("Request to get all SalesOrderProducts");
        return salesOrderProductRepository.findAll();
    }


    /**
     * Get one salesOrderProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SalesOrderProduct> findOne(Long id) {
        log.debug("Request to get SalesOrderProduct : {}", id);
        return salesOrderProductRepository.findById(id);
    }

    /**
     * Delete the salesOrderProduct by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesOrderProduct : {}", id);
        salesOrderProductRepository.deleteById(id);
    }
}
