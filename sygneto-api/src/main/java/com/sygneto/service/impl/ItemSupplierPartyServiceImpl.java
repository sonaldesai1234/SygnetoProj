package com.sygneto.service.impl;

import com.sygneto.service.ItemSupplierPartyService;
import com.sygneto.domain.ItemSupplierParty;
import com.sygneto.repository.ItemSupplierPartyRepository;
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
public class ItemSupplierPartyServiceImpl implements ItemSupplierPartyService {

    private final Logger log = LoggerFactory.getLogger(ItemSupplierPartyServiceImpl.class);

    private final ItemSupplierPartyRepository itemSupplierPartyRepository;

    public ItemSupplierPartyServiceImpl(ItemSupplierPartyRepository itemSupplierPartyRepository) {
        this.itemSupplierPartyRepository = itemSupplierPartyRepository;
    }

    @Override
    public ItemSupplierParty save(ItemSupplierParty item) {
        log.debug("Request to save ItemSupplierParty : {}", item);
        return itemSupplierPartyRepository.save(item);
    }

   
    @Override
    @Transactional(readOnly = true)
    public List<ItemSupplierParty> findAll() {
        log.debug("Request to get all ItemSupplierParty");
        return itemSupplierPartyRepository.findAll();
    }


   
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemSupplierParty> findOne(Long id) {
        log.debug("Request to get ItemSupplierParty : {}", id);
        
        return itemSupplierPartyRepository.findById(id);
    }

   
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemSupplierParty : {}", id);
        itemSupplierPartyRepository.deleteById(id);
    }
}
