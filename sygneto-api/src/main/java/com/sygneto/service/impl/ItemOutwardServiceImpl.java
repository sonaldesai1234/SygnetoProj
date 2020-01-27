package com.sygneto.service.impl;

import com.sygneto.service.ItemOutwardService;
import com.sygneto.domain.ItemOutward;
import com.sygneto.repository.ItemOutwardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemOutward}.
 */
@Service
@Transactional
public class ItemOutwardServiceImpl implements ItemOutwardService {

    private final Logger log = LoggerFactory.getLogger(ItemOutwardServiceImpl.class);

    private final ItemOutwardRepository itemOutwardRepository;

    public ItemOutwardServiceImpl(ItemOutwardRepository itemOutwardRepository) {
        this.itemOutwardRepository = itemOutwardRepository;
    }

    /**
     * Save a itemOutward.
     *
     * @param itemOutward the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemOutward save(ItemOutward itemOutward) {
        log.debug("Request to save ItemOutward : {}", itemOutward);
        return itemOutwardRepository.save(itemOutward);
    }

    /**
     * Get all the itemOutwards.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemOutward> findAll() {
        log.debug("Request to get all ItemOutwards");
        return itemOutwardRepository.findAll();
    }


    /**
     * Get one itemOutward by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemOutward> findOne(Long id) {
        log.debug("Request to get ItemOutward : {}", id);
        return itemOutwardRepository.findById(id);
    }

    /**
     * Delete the itemOutward by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemOutward : {}", id);
        itemOutwardRepository.deleteById(id);
    }

	

	@Override
	public void deleteByItemOutwardId(Long itemOutwardId) {
		itemOutwardRepository.deleteByItemOutwardId(itemOutwardId);	
	}

	@Override
	public Long findCount() {
		// TODO Auto-generated method stub
		return itemOutwardRepository.findCount();
	}

/*	@Override
	public List<ItemOutward> pessimisticLock(Long prId) {
		// TODO Auto-generated method stub
		return itemOutwardRepository.pessimisticLock(prId);
	}

	@Override
	public Long findAllItemQty(Long purchaseRequestId) {
		// TODO Auto-generated method stub
		return itemOutwardRepository.findAllItemQty(purchaseRequestId);
	}*/

}
