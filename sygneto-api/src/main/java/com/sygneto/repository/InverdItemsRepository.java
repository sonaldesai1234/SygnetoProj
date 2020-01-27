package com.sygneto.repository;
import com.sygneto.domain.CategoryGroup;
import com.sygneto.domain.InverdItems;
import com.sygneto.domain.Party;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemInward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InverdItemsRepository extends JpaRepository<InverdItems, Long> {
	
	@Query(value = "SELECT sum(quantity_Received) from inward_items WHERE item_id IN (:itemId)", nativeQuery = true)
	Long getSumReceived(@Param("itemId")Long itemId);

	@Query(value = "select ii.sales_order_id as salesOrderId, ii.purchase_request_id as purchaseRequestId, sum(ii.quantity_Received) - (select ifnull(sum(io.item_quantity),0) from item_outward as io where io.item_id = (:itemId) and io.inward_purchase_request_id = ii.purchase_request_id ) as quantity\n" + 
			"from inward_items as ii \n" + 
			"where ii.item_id =(:itemId) group by ii.purchase_request_id;", nativeQuery = true)
	ArrayList<Map<String, Object>> getQtyByItemId(@Param("itemId") Long itemId);

	@Query(value = "SELECT sum(quantity_Received) - (SELECT ifnull(sum(item_quantity),0) from item_outward WHERE item_id=(:itemId)) as qty from inward_items WHERE item_id =(:itemId)", nativeQuery = true)
	Long getTotalQtyInstock(@Param("itemId") Long itemId);
	
	@Query(value = "SELECT sum(quantity_Received) from inward_items WHERE purchase_request_id = (:purchaseRequestId)", nativeQuery = true)
	Long findBypr(@Param("purchaseRequestId") Long purchaseRequestId);

	/*@Query(value = "select purchase_request_id as purchaseRequestId , sum(quantity_Received ) as quantityReceived from inward_items where item_id = (:itemId) group by purchase_request_id;", nativeQuery = true)
	ArrayList<Map<String, Object>> getQtyByItemId(@Param("itemId") Long itemId);*/

	/*@Query(value = "SELECT purchase_request_id as PRID, quantity_Received as quantity from inward_items WHERE item_id IN (:itemId)", nativeQuery = true)
	ArrayList<Map<String, Object>> getSumReceivedByItemId(Long id);*/
	
	/*@Query(value = "SELECT sum(inward_items.already_Received)*sum(item_outward.item_quantity) from inward_items, item_outward WHERE inward_items.item_id IN (:itemId) and inward_items.item_id= item_outward.item_id", nativeQuery = true)
	Long getSumReceived(@Param("itemId")Long itemId);*/
	
	//@Query("SELECT e FROM Employee e WHERE e.employeeName IN (:names)")   
	//@Query(value = "SELECT c FROM Party c where  isCustomer = ?isCustomer")

	


	   //@Query(value = "SELECT max(age) from User where first_name <> ?1", nativeQuery = true)
	    
	    /*@Query(value = "SELECT sum(quantity_Received) from inverd_items where item_id = ?itemId")
	    Long getSumReceived(@Param("itemId")Long itemId);*/
	//List<CategoryGroup> findByItemId(Long itemId); 

	/*@Query("select CategoryGroup(p.item_id as itemId, "
			+ "sum(p.quantity) as quantity, "
			+ "sum(p.already_Received) as alreadyReceived ) "
			+ "from InverdItems p "
			+ "group by p.item_id")
         public List<CategoryGroup> groupBy();*/
	
	/* @Query("SELECT " +
	           "    CategoryGroup(v.alreadyReceived, COUNT(v)) " +
	           "FROM " +
	           "    InverdItems v " +
	           "GROUP BY " +
	           "    v.answer")
	    List<CategoryGroup> findSurveySum();*/

}
