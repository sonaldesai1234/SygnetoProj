package com.sygneto.service.impl;

import com.sygneto.service.ProductService;
import com.sygneto.domain.Product;
import com.sygneto.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductService}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Product save(Product employee) {
        log.debug("Request to save Employee : {}", employee);
        return productRepository.save(employee);
    }

    /**
     * Get all the Product.
     *
     * @return the list of entities.
     */
    @Override
    public List<Product> findAll() {
        log.debug("Request to get all Employees");
        return productRepository.findAll();
    }


    /**
     * Get one Product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Product> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        
        return productRepository.findById(id);
    }

    /**
     * Delete the Product by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        productRepository.deleteById(id);
    }

	@Override
	public Optional<Product> findById(Long id) {
		
		return productRepository.findById(id);
	}

	@Override
	public long findCount() {
		// TODO Auto-generated method stub
		return productRepository.findCount();
	}

	
	
}
