package com.sygneto.service;

import com.sygneto.domain.InverdItems;
import com.sygneto.domain.ItemInward;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ItemInward}.
 */
public interface ItemInwardService {

    /**
     * Save a itemInward.
     *
     * @param itemInward the entity to save.
     * @return the persisted entity.
     */
    ItemInward save(ItemInward itemInward);

    /**
     * Get all the itemInwards.
     *
     * @return the list of entities.
     */
    List<ItemInward> findAll();


    /**
     * Get the "id" itemInward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemInward> findOne(Long id);

    /**
     * Delete the "id" itemInward.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


	Long findByCount();

	List<ItemInward> findByPurchaseOrderPurchaseOrderId(Long poId);

	Long checkStatusOfPo(Long purchaseRequestId);

//	List<ItemInward> findAllQuantityByItemId();


	


}
