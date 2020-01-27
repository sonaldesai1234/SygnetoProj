package com.sygneto.repository;
import com.sygneto.domain.ItemOutward;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemOutward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemOutwardRepository extends JpaRepository<ItemOutward, Long> {

	void deleteByItemOutwardId(Long itemOutwardId);

	@Query(value="SELECT COUNT(*) FROM ItemOutward")
	Long findCount();

	
	@Query(value = "SELECT sum(item_quantity) from item_outward WHERE item_id IN (:itemId)", nativeQuery = true)
	Long getSumReceived(@Param("itemId")Long itemId);

	/*@Query(value = "SELECT * from item_outward WHERE purchase_request_id IN (:prId) for update", nativeQuery = true)
	List<ItemOutward> pessimisticLock(@Param("prId")Long prId);*/

	@Query(value = "SELECT sum(item_quantity) from item_outward WHERE purchase_request_id = (:purchaseRequestId)", nativeQuery = true)
	Long findAllItemQty(@Param("purchaseRequestId")Long purchaseRequestId);
	
/*	@Query(value = "commit", nativeQuery = true)
	void commit();

	@Query(value = "rollback", nativeQuery = true)
	void rollback();*/
}
