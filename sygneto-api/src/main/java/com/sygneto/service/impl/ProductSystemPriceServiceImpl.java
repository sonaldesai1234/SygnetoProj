package com.sygneto.service.impl;

import com.sygneto.service.ProductSystemPriceService;
import com.sygneto.domain.ProductMrp;
import com.sygneto.domain.ProductSystemPrice;
import com.sygneto.repository.ProductSystemPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductSystemPrice}.
 */
@Service
@Transactional
public class ProductSystemPriceServiceImpl implements ProductSystemPriceService {

    private final Logger log = LoggerFactory.getLogger(ProductSystemPriceServiceImpl.class);

    private final ProductSystemPriceRepository productSystemPriceRepository;

    public ProductSystemPriceServiceImpl(ProductSystemPriceRepository productSystemPriceRepository) {
        this.productSystemPriceRepository = productSystemPriceRepository;
    }

    /**
     * Save a productSystemPrice.
     *
     * @param productSystemPrice the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductSystemPrice save(ProductSystemPrice productSystemPrice) {
        log.debug("Request to save ProductSystemPrice : {}", productSystemPrice);
        return productSystemPriceRepository.save(productSystemPrice);
    }

    /**
     * Get all the productSystemPrices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductSystemPrice> findAll() {
        log.debug("Request to get all ProductSystemPrices");
        return productSystemPriceRepository.findAll();
    }


    /**
     * Get one productSystemPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSystemPrice> findOne(Long id) {
        log.debug("Request to get ProductSystemPrice : {}", id);
        return productSystemPriceRepository.findById(id);
    }

    /**
     * Delete the productSystemPrice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductSystemPrice : {}", id);
        productSystemPriceRepository.deleteById(id);
    }

	@Override
	public Optional<ProductSystemPrice> findById(Long id) {
		// TODO Auto-generated method stub
		return productSystemPriceRepository. findById(id);
	}
}
