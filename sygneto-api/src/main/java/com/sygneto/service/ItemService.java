package com.sygneto.service;

import com.sygneto.domain.Item;

import java.util.List;
import java.util.Optional;


public interface ItemService {

   
	Item save(Item itemCategory);

   
    List<Item> findAll();

    Optional<Item> findOne(Long id);

   
    void delete(Long id);


	Long findCount();
}
