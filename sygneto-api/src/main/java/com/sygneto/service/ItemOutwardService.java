package com.sygneto.service;

import com.sygneto.domain.ItemOutward;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ItemOutward}.
 */
public interface ItemOutwardService {

    /**
     * Save a itemOutward.
     *
     * @param itemOutward the entity to save.
     * @return the persisted entity.
     */
    ItemOutward save(ItemOutward itemOutward);

    /**
     * Get all the itemOutwards.
     *
     * @return the list of entities.
     */
    List<ItemOutward> findAll();


    /**
     * Get the "id" itemOutward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemOutward> findOne(Long id);

    /**
     * Delete the "id" itemOutward.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


     void deleteByItemOutwardId(Long itemOutwardId);

	Long findCount();

	/*List<ItemOutward> pessimisticLock(Long prId);

	Long findAllItemQty(Long purchaseRequestId);*/
}
