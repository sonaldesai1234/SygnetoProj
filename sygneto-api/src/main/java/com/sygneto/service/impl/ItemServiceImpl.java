package com.sygneto.service.impl;

import com.sygneto.service.ItemService;
import com.sygneto.domain.Item;
import com.sygneto.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item save(Item item) {
        log.debug("Request to save Employee : {}", item);
        return itemRepository.save(item);
    }

   
    @Override
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        log.debug("Request to get all item");
        return itemRepository.findAll();
    }


   
    @Override
    @Transactional(readOnly = true)
    public Optional<Item> findOne(Long id) {
        log.debug("Request to get item : {}", id);
        
        return itemRepository.findById(id);
    }

   
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        itemRepository.deleteById(id);
    }

	@Override
	public Long findCount() {
		
		return itemRepository.findCount();
	}
}
