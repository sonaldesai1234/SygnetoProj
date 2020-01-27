package com.sygneto.service;

import com.sygneto.domain.ProductMrp;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

/**
 * Service Interface for managing {@link ProductMrp}.
 */
public interface ProductMrpService {

    /**
     * Save a productMrp.
     *
     * @param productMrp the entity to save.
     * @return the persisted entity.
     */
	
    ProductMrp save(ProductMrp productMrp);

    /**
     * Get all the productMrps.
     *
     * @return the list of entities.
     */
    List<ProductMrp> findAll();


    /**
     * Get the "id" productMrp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductMrp> findOne(Long id);

    /**
     * Delete the "id" productMrp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<ProductMrp> findById(Long id);

	List<ProductMrp> findAllBetweenDates(Instant validFrom, Instant validTill);

	
	

	

}
