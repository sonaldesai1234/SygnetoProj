package com.sygneto.repository;
import com.sygneto.domain.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value="select count(*)from Product")
	long findCount();

	
}
