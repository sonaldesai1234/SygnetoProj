package com.sygneto.service.impl;

import com.sygneto.service.BomItemService;
import com.sygneto.domain.BOMItem;
import com.sygneto.repository.BomItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BillOfMaterialService}.
 */
@Service
@Transactional
public class BomItemImpl implements BomItemService {

    private final Logger log = LoggerFactory.getLogger(BomItemImpl.class);

    private final BomItemRepository billOfMaterialRepository;

    public BomItemImpl(BomItemRepository billOfMaterialRepository) {
        this.billOfMaterialRepository = billOfMaterialRepository;
    }

    /**
     * Save a billOfMaterial.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BOMItem save(BOMItem billOfMaterial) {
        log.debug("Request to save Employee : {}", billOfMaterial);
        return billOfMaterialRepository.save(billOfMaterial);
    }

    /**
     * Get all the BillOfMaterial.
     *
     * @return the list of entities.
     */
    @Override
    public List<BOMItem> findAll() {
        log.debug("Request to get all billOfMaterial");
        return billOfMaterialRepository.findAll();
    }


    /**
     * Get one BillOfMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BOMItem> findOne(Long id) {
        log.debug("Request to get billOfMaterial : {}", id);
        
        return billOfMaterialRepository.findById(id);
    }

    /**
     * Delete the BillOfMaterial by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete billOfMaterial : {}", id);
        billOfMaterialRepository.deleteById(id);
    }
}
