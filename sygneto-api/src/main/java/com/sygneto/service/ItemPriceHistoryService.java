package com.sygneto.service;

import com.sygneto.domain.ItemPriceHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ItemPriceHistory}.
 */
public interface ItemPriceHistoryService {

    /**
     * Save a itemPriceHistory.
     *
     * @param itemPriceHistory the entity to save.
     * @return the persisted entity.
     */
    ItemPriceHistory save(ItemPriceHistory itemPriceHistory);

    /**
     * Get all the itemPriceHistories.
     *
     * @return the list of entities.
     */
    List<ItemPriceHistory> findAll();


    /**
     * Get the "id" itemPriceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemPriceHistory> findOne(Long id);

    /**
     * Delete the "id" itemPriceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<ItemPriceHistory> findByPartyId(Long partyId);

	Optional<ItemPriceHistory> findByItemId(Long itemId);

	List<ItemPriceHistory> findByItemIdAndPartyId(Long itemId, Long partyId);
}
