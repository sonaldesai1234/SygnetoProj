package com.sygneto.service.impl;

import com.sygneto.service.PurchaseRequestItemService;
import com.sygneto.domain.PurchaseRequestItem;
import com.sygneto.repository.PurchaseRequestItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PurchaseRequestItem}.
 */
@Service
@Transactional
public class PurchaseRequestItemServiceImpl implements PurchaseRequestItemService {

    private final Logger log = LoggerFactory.getLogger(PurchaseRequestItemServiceImpl.class);

    private final PurchaseRequestItemRepository purchaseRequestItemRepository;

    public PurchaseRequestItemServiceImpl(PurchaseRequestItemRepository purchaseRequestItemRepository) {
        this.purchaseRequestItemRepository = purchaseRequestItemRepository;
    }

    /**
     * Save a purchaseRequestItem.
     *
     * @param purchaseRequestItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseRequestItem save(PurchaseRequestItem purchaseRequestItem) {
        log.debug("Request to save PurchaseRequestItem : {}", purchaseRequestItem);
        return purchaseRequestItemRepository.save(purchaseRequestItem);
    }

    /**
     * Get all the purchaseRequestItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseRequestItem> findAll() {
        log.debug("Request to get all PurchaseRequestItems");
        return purchaseRequestItemRepository.findAll();
    }


    /**
     * Get one purchaseRequestItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseRequestItem> findOne(Long id) {
        log.debug("Request to get PurchaseRequestItem : {}", id);
        return purchaseRequestItemRepository.findById(id);
    }

    /**
     * Delete the purchaseRequestItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseRequestItem : {}", id);
        purchaseRequestItemRepository.deleteById(id);
    }
}
