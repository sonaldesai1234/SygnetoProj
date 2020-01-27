package com.sygneto.repository;
import com.sygneto.domain.InverdItems;
import com.sygneto.domain.ItemInward;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemInward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemInwardRepository extends JpaRepository<ItemInward, Long> {
	
	void deleteByItemInwardId(Long itemInwardId);

	@Query(value="select count(*) from ItemInward")
	Long findByCount();

	@Query(value = "SELECT * from item_inward where purchase_order_id IN (:poId)", nativeQuery = true)
	List<ItemInward> findByPurchaseOrderPurchaseOrderId(@Param("poId") Long poId);

	@Query(value = "select sum(poi.item_quantity ) - (select ifnull(sum(ii.quantity_Received),0) from inward_items as ii where purchase_request_id=(:purchaseRequestId)) as value from purchase_order_item as poi where poi.purchase_request_id=(:purchaseRequestId)", nativeQuery = true)
	Long checkStatusOfPo(@Param("purchaseRequestId") Long purchaseRequestId);
	

	/*@Query("select itemId,sum(quantity)from InverdItems group by itemId")
	List<ItemInward> findAllQuantityByItemId();
*/
	 

}
