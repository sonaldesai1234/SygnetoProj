package com.sygneto.repository;
import com.sygneto.domain.ItemOutward;
import com.sygneto.domain.PurchaseRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {

	List<PurchaseRequest> findAllByStatus(String status);


	@Query(value="select count(*)from PurchaseRequest ")
	Long findCount();


	@Query(value = "SELECT * from purchase_requests WHERE sales_order_id IN (:salesOrderId)", nativeQuery = true)
	List<PurchaseRequest> findBySalesOrderId(@Param("salesOrderId")Long salesOrderId);

	@Query(value ="select purchase_request_id,datediff(created_date,current_date())as open_for_how_many_days from purchase_requests" ,nativeQuery = true)
	ArrayList<Map<String, Object>> findTotalDaysPROpen();


	@Query(value = "SELECT * from purchase_requests WHERE  item_name IN (:itemName)", nativeQuery = true)
	List<PurchaseRequest> findByItemName(@Param("itemName")String itemName);

	@Query(value ="select * from purchase_requests where sales_order_id in(:salesOrderId) or item_id in(:itemId)", nativeQuery = true)
	List<PurchaseRequest> findBySalesOrderIdOrItemId(@Param("salesOrderId")Long salesOrderId, @Param("itemId") Long itemId);


	@Query(value="\n" + 
			"select purchase_request_id as status,\n" + 
			"count(*)as pr_count\n" + 
			"from  \n" + 
			"(\n" + 
			"select\n" + 
			"CASE \n" + 
			"	when purchase_requests.purchase_request_id in(select inward_items.purchase_request_id) and \n" + 
			"	inward_items.purchase_request_id in(select item_outward.purchase_request_id) then\n" + 
			"	case  purchase_requests.quantity > inward_items.quantity \n" + 
			"	when 0 then (case when (inward_items.quantity > item_outward.item_quantity and purchase_requests.quantity=item_outward.item_quantity)then 'close' else 'open' end)\n" + 
			"	when 1 then (case when (inward_items.quantity > item_outward.item_quantity and purchase_requests.quantity!=item_outward.item_quantity)  then 'open' else 'close' end)\n" + 
			"    end \n" + 
			"    END \n" + 
			"	as purchase_request_id\n" + 
			"	from inward_items inner join item_outward inner join purchase_requests\n" + 
			")d\n" + 
			"group by purchase_request_id", nativeQuery = true)
	ArrayList<Map<String, Object>> countOpenClosePR();



	@Query(value = "SELECT \n" + 
			"	    COUNT(CASE WHEN  MONTH(created_date) = 1 THEN 1 ELSE NULL END) January\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 2 THEN 1 ELSE NULL END) Feburary\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 3 THEN 1 ELSE NULL END) March\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 4 THEN 1 ELSE NULL END) April\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 5 THEN 1 ELSE NULL END) May\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 6 THEN 1 ELSE NULL END) June\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 7 THEN 1 ELSE NULL END) July\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 8 THEN 1 ELSE NULL END) August\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 9 THEN 1 ELSE NULL END) September\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 10 THEN 1 ELSE NULL END) October\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 11 THEN 1 ELSE NULL END) November\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 12 THEN 1 ELSE NULL END) December\n" + 
			"  \n" + 
			"FROM purchase_requests where status=\"active\";", nativeQuery = true)
	Object getActivePRByMnthWise();

	@Query(value = "SELECT \n" + 
			"	    COUNT(CASE WHEN  MONTH(created_date) = 1 THEN 1 ELSE NULL END) January\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 2 THEN 1 ELSE NULL END) Feburary\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 3 THEN 1 ELSE NULL END) March\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 4 THEN 1 ELSE NULL END) April\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 5 THEN 1 ELSE NULL END) May\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 6 THEN 1 ELSE NULL END) June\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 7 THEN 1 ELSE NULL END) July\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 8 THEN 1 ELSE NULL END) August\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 9 THEN 1 ELSE NULL END) September\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 10 THEN 1 ELSE NULL END) October\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 11 THEN 1 ELSE NULL END) November\n" + 
			"       , COUNT(CASE WHEN  MONTH(created_date) = 12 THEN 1 ELSE NULL END) December\n" + 
			"  \n" + 
			"FROM purchase_requests where status=\"closed\";", nativeQuery = true)
	Object getClosedPRByMnthWise();


	


	/*
	@Query(value = "SELECT * from purchase_requests WHERE sales_order_id IN (:salesOrderId)", nativeQuery = true)
	List<PurchaseRequest> findBySalesOrderId(@Param("salesOrderId") Long salesOrderId);
*/
	/*@Query(value = "SELECT * from item_outward WHERE purchase_request_id IN (:prId) for update", nativeQuery = true)
	List<ItemOutward> pessimisticLock(@Param("prId")Long prId);*/

	/*@Query(value = "SELECT * from purchase_requests WHERE purchase_request_id IN (:purchaseRequestId) for update", nativeQuery = true)
	Optional<PurchaseRequest> pessimisticLock(@Param("purchaseRequestId") Long purchaseRequestId);

	@Query(value = "commit", nativeQuery = true)
	void commit();

	@Query(value = "rollback", nativeQuery = true)
	void rollback();*/

/*	@Query(value = "SELECT quantity from purchase_requests WHERE purchase_request_id IN (:purchaseRequestId) ", nativeQuery = true)
	void findByPOIdNSum(Long purchaseRequestId, float sum);*/



}
