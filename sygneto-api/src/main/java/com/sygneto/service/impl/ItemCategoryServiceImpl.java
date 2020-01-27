package com.sygneto.service.impl;

import com.sygneto.service.ItemCategoryService;
import com.sygneto.domain.ItemCategory;
import com.sygneto.repository.ItemCategoryRepository;
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
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private final Logger log = LoggerFactory.getLogger(ItemCategoryServiceImpl.class);

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryServiceImpl(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemCategory save(ItemCategory employee) {
        log.debug("Request to save Employee : {}", employee);
        return itemCategoryRepository.save(employee);
    }

    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemCategory> findAll() {
        log.debug("Request to get all Employees");
        return itemCategoryRepository.findAll();
    }


    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemCategory> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        
        return itemCategoryRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        itemCategoryRepository.deleteById(id);
    }

	
	

	
}
