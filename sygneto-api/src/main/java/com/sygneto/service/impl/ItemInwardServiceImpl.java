package com.sygneto.service.impl;

import com.sygneto.service.ItemInwardService;
import com.sygneto.domain.InverdItems;
import com.sygneto.domain.ItemInward;
import com.sygneto.repository.ItemInwardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemInward}.
 */
@Service
@Transactional
public class ItemInwardServiceImpl implements ItemInwardService {

    private final Logger log = LoggerFactory.getLogger(ItemInwardServiceImpl.class);

    private final ItemInwardRepository itemInwardRepository;

    public ItemInwardServiceImpl(ItemInwardRepository itemInwardRepository) {
        this.itemInwardRepository = itemInwardRepository;
    }

    /**
     * Save a itemInward.
     *
     * @param itemInward the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemInward save(ItemInward itemInward) {
        log.debug("Request to save ItemInward : {}", itemInward);
        return itemInwardRepository.save(itemInward);
    }

    /**
     * Get all the itemInwards.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemInward> findAll() {
        log.debug("Request to get all ItemInwards");
        return itemInwardRepository.findAll();
    }


    /**
     * Get one itemInward by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemInward> findOne(Long id) {
        log.debug("Request to get ItemInward : {}", id);
        return itemInwardRepository.findById(id);
    }

    /**
     * Delete the itemInward by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemInward : {}", id);
        itemInwardRepository.deleteById(id);
    }

	

	@Override
	public Long findByCount() {
		// TODO Auto-generated method stub
		return itemInwardRepository.findByCount();
	}

	@Override
	public List<ItemInward> findByPurchaseOrderPurchaseOrderId(Long poId) {
		// TODO Auto-generated method stub
		return itemInwardRepository.findByPurchaseOrderPurchaseOrderId(poId);
	}

	@Override
	public Long checkStatusOfPo(Long purchaseRequestId) {
		// TODO Auto-generated method stub
		return itemInwardRepository.checkStatusOfPo(purchaseRequestId);
	}

	/*@Override
	public List<ItemInward> findAllQuantityByItemId() {
		// TODO Auto-generated method stub
		return itemInwardRepository.findAllQuantityByItemId();
	}*/

	

	

	
}