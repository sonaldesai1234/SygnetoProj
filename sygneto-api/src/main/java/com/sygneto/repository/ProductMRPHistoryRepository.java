package com.sygneto.repository;
import com.sygneto.domain.ProductMRPHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductMRPHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductMRPHistoryRepository extends JpaRepository<ProductMRPHistory, Long> {

	
	List<ProductMRPHistory> findAllByProductId(Long productId);

	


	

}
