package com.sygneto.service.impl;

import com.sygneto.service.ProductMRPHistoryService;
import com.sygneto.domain.ProductMRPHistory;
import com.sygneto.repository.ProductMRPHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductMRPHistory}.
 */
@Service
@Transactional
public class ProductMRPHistoryServiceImpl implements ProductMRPHistoryService {

    private final Logger log = LoggerFactory.getLogger(ProductMRPHistoryServiceImpl.class);

    private final ProductMRPHistoryRepository productMRPHistoryRepository;

    public ProductMRPHistoryServiceImpl(ProductMRPHistoryRepository productMRPHistoryRepository) {
        this.productMRPHistoryRepository = productMRPHistoryRepository;
    }

    /**
     * Save a productMRPHistory.
     *
     * @param productMRPHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductMRPHistory save(ProductMRPHistory productMRPHistory) {
        log.debug("Request to save ProductMRPHistory : {}", productMRPHistory);
        return productMRPHistoryRepository.save(productMRPHistory);
    }

    /**
     * Get all the productMRPHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductMRPHistory> findAll() {
        log.debug("Request to get all ProductMRPHistories");
        return productMRPHistoryRepository.findAll();
    }


    /**
     * Get one productMRPHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductMRPHistory> findOne(Long id) {
        log.debug("Request to get ProductMRPHistory : {}", id);
        return productMRPHistoryRepository.findById(id);
    }

    /**
     * Delete the productMRPHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductMRPHistory : {}", id);
        productMRPHistoryRepository.deleteById(id);
    }

	

	@Override
	public List<ProductMRPHistory> findAllByProductId(Long productId) {
	
		return productMRPHistoryRepository.findAllByProductId(productId);
	}


	

	




}
