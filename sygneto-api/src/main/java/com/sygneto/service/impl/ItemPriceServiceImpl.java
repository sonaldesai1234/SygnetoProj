package com.sygneto.service.impl;

import com.sygneto.service.ItemPriceService;
import com.sygneto.domain.ItemPrice;
import com.sygneto.repository.ItemPriceRepository;

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
public class ItemPriceServiceImpl implements ItemPriceService {

    private final Logger log = LoggerFactory.getLogger(ItemPriceServiceImpl.class);

    private final ItemPriceRepository itemPriceRepository;

    public ItemPriceServiceImpl(ItemPriceRepository itemPriceRepository) {
        this.itemPriceRepository = itemPriceRepository;
    }

    @Override
    public ItemPrice save(ItemPrice ItemPrice) {
        log.debug("Request to save Employee : {}", ItemPrice);
        return itemPriceRepository.save(ItemPrice);
    }

   
    @Override
    @Transactional(readOnly = true)
    public List<ItemPrice> findAll() {
        log.debug("Request to get all ItemPrice");
        return itemPriceRepository.findAll();
    }


   
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemPrice> findOne(Long id) {
        log.debug("Request to get item : {}", id);
        
        return itemPriceRepository.findById(id);
    }

   
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        itemPriceRepository.deleteById(id);
    }
}
