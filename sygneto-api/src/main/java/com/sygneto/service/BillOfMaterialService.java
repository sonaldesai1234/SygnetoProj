package com.sygneto.service;

import com.sygneto.domain.BillOfMaterial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing {@link BillOfMaterial}.
 */
public interface BillOfMaterialService {

    /**
     * Save a BillOfMaterial.
     *
     * @param BillOfMaterial the entity to save.
     * @return the persisted entity.
     */
	BillOfMaterial save(BillOfMaterial employee);

    /**
     * Get all the BillOfMaterial.
     *
     * @return the list of entities.
     */
    List<BillOfMaterial> findAll();


    /**
     * Get the "id" BillOfMaterial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillOfMaterial> findOne(Long id);

    /**
     * Delete the "id" BillOfMaterial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	ArrayList<Map<String, String>> findAllByLetestVersion();

	BillOfMaterial findByProductNversion(Long long1, float f);

	List<BillOfMaterial> findByProductProductId(Long productId);

	//List<BillOfMaterial> saveOrUpdate(List<BillOfMaterial> billOfMaterial);
}
