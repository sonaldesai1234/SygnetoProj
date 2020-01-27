package com.sygneto.service;

import com.sygneto.domain.ItemSupplierParty;

import java.util.List;
import java.util.Optional;


public interface ItemSupplierPartyService {

   
	ItemSupplierParty save(ItemSupplierParty itemPrice);

   
    List<ItemSupplierParty> findAll();

    
    Optional<ItemSupplierParty> findOne(Long id);

    void delete(Long id);

}
