package com.sygneto.service.impl;

import com.sygneto.service.ItemPriceHistoryService;
import com.sygneto.domain.ItemPriceHistory;
import com.sygneto.repository.ItemPriceHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemPriceHistory}.
 */
@Service
@Transactional
public class ItemPriceHistoryServiceImpl implements ItemPriceHistoryService {

    private final Logger log = LoggerFactory.getLogger(ItemPriceHistoryServiceImpl.class);

    private final ItemPriceHistoryRepository itemPriceHistoryRepository;

    public ItemPriceHistoryServiceImpl(ItemPriceHistoryRepository itemPriceHistoryRepository) {
        this.itemPriceHistoryRepository = itemPriceHistoryRepository;
    }

    /**
     * Save a itemPriceHistory.
     *
     * @param itemPriceHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemPriceHistory save(ItemPriceHistory itemPriceHistory) {
        log.debug("Request to save ItemPriceHistory : {}", itemPriceHistory);
        return itemPriceHistoryRepository.save(itemPriceHistory);
    }

    /**
     * Get all the itemPriceHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemPriceHistory> findAll() {
        log.debug("Request to get all ItemPriceHistories");
        return itemPriceHistoryRepository.findAll();
    }


    /**
     * Get one itemPriceHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPriceHistory> findOne(Long id) {
        log.debug("Request to get ItemPriceHistory : {}", id);
        return itemPriceHistoryRepository.findById(id);
    }

    /**
     * Delete the itemPriceHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemPriceHistory : {}", id);
        itemPriceHistoryRepository.deleteById(id);
    }

	@Override
	public Optional<ItemPriceHistory> findByPartyId(Long partyId) {
		// TODO Auto-generated method stub
		return itemPriceHistoryRepository.findByPartyId(partyId);
	}

	@Override
	public Optional<ItemPriceHistory> findByItemId(Long itemId) {
		// TODO Auto-generated method stub
		return itemPriceHistoryRepository.findByItemId(itemId);
	}

	@Override
	public List<ItemPriceHistory> findByItemIdAndPartyId(Long itemId, Long partyId) {
		// TODO Auto-generated method stub
		return itemPriceHistoryRepository.findByItemIdAndPartyId(itemId,partyId);
	}
}
