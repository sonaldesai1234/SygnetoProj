package com.sygneto.service.impl;

import com.sygneto.service.SalesOrderHistoryService;
import com.sygneto.domain.SalesOrderHistory;
import com.sygneto.repository.SalesOrderHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SalesOrderHistory}.
 */
@Service
@Transactional
public class SalesOrderHistoryServiceImpl implements SalesOrderHistoryService {

    private final Logger log = LoggerFactory.getLogger(SalesOrderHistoryServiceImpl.class);

    private final SalesOrderHistoryRepository salesOrderHistoryRepository;

    public SalesOrderHistoryServiceImpl(SalesOrderHistoryRepository salesOrderHistoryRepository) {
        this.salesOrderHistoryRepository = salesOrderHistoryRepository;
    }

    /**
     * Save a salesOrderHistory.
     *
     * @param salesOrderHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SalesOrderHistory save(SalesOrderHistory salesOrderHistory) {
        log.debug("Request to save SalesOrderHistory : {}", salesOrderHistory);
        return salesOrderHistoryRepository.save(salesOrderHistory);
    }

    /**
     * Get all the salesOrderHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SalesOrderHistory> findAll() {
        log.debug("Request to get all SalesOrderHistories");
        return salesOrderHistoryRepository.findAll();
    }


    /**
     * Get one salesOrderHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SalesOrderHistory> findOne(Long id) {
        log.debug("Request to get SalesOrderHistory : {}", id);
        return salesOrderHistoryRepository.findById(id);
    }

    /**
     * Delete the salesOrderHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesOrderHistory : {}", id);
        salesOrderHistoryRepository.deleteById(id);
    }
}
