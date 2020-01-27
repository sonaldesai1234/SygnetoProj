package com.sygneto.service.impl;

import com.sygneto.service.PurchaseOrderItemsService;
import com.sygneto.domain.PurchaseOrderItems;
import com.sygneto.repository.PurchaseOrderItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PurchaseOrderOld}.
 */
@Service
@Transactional
public class PurchaseOrderItemsServiceImpl implements PurchaseOrderItemsService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderItemsServiceImpl.class);

    private final PurchaseOrderItemsRepository purchaseOrderRepository;

    public PurchaseOrderItemsServiceImpl(PurchaseOrderItemsRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrder the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PurchaseOrderItems save(PurchaseOrderItems purchaseOrder) {
        log.debug("Request to save PurchaseOrder : {}", purchaseOrder);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    /**
     * Get all the purchaseOrders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrderItems> findAll() {
        log.debug("Request to get all PurchaseOrders");
        return purchaseOrderRepository.findAll();
    }


    /**
     * Get one purchaseOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<PurchaseOrderItems> findOne(Long id) {
        log.debug("Request to get PurchaseOrder : {}", id);
        return purchaseOrderRepository.findById(id);
		//return null;
    }

    /**
     * Delete the purchaseOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrder : {}", id);
        purchaseOrderRepository.deleteById(id);
    }
}
