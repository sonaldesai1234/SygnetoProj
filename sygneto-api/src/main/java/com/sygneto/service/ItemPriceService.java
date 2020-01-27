package com.sygneto.service;

import com.sygneto.domain.ItemPrice;

import java.util.List;
import java.util.Optional;


public interface ItemPriceService {

   
	ItemPrice save(ItemPrice itemPrice);

   
    List<ItemPrice> findAll();


    
    Optional<ItemPrice> findOne(Long id);

   
    void delete(Long id);
}
