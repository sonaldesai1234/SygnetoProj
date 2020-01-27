package com.sygneto.service.impl;

import com.sygneto.service.ProductMrpService;
import com.sygneto.domain.ProductMrp;
import com.sygneto.repository.ProductMrpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductMrp}.
 */
@Service
@Transactional
public class ProductMrpServiceImpl implements ProductMrpService {

    private final Logger log = LoggerFactory.getLogger(ProductMrpServiceImpl.class);

    private final ProductMrpRepository productMrpRepository;

    public ProductMrpServiceImpl(ProductMrpRepository productMrpRepository) {
        this.productMrpRepository = productMrpRepository;
    }

    /**
     * Save a productMrp.
     *
     * @param productMrp the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductMrp save(ProductMrp productMrp) {
        log.debug("Request to save ProductMrp : {}", productMrp);
        return productMrpRepository.save(productMrp);
    }

    /**
     * Get all the productMrps.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductMrp> findAll() {
        log.debug("Request to get all ProductMrps");
        return productMrpRepository.findAll();
    }


    /**
     * Get one productMrp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductMrp> findOne(Long id) {
        log.debug("Request to get ProductMrp : {}", id);
        return productMrpRepository.findById(id);
    }

    /**
     * Delete the productMrp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductMrp : {}", id);
        productMrpRepository.deleteById(id);
    }

	@Override
	public Optional<ProductMrp> findById(Long id) {
		return productMrpRepository.findById(id);

	}


	@Override
	public List<ProductMrp> findAllBetweenDates(Instant validFrom, Instant validTill) {
		// TODO Auto-generated method stub
		return productMrpRepository.findAllBetweenDates(validFrom,validTill);
	}

	
	

	

	
}
