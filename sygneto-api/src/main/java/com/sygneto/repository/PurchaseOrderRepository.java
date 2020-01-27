package com.sygneto.repository;
import com.sygneto.domain.ItemInward;
import com.sygneto.domain.PurchaseOrder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.tools.Trace;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
	
	@Query(value = "select * from purchase_orders\n" + 
			"WHERE  MONTH(purchase_order_date) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) \n" + 
			"AND YEAR(purchase_order_date) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH);", nativeQuery = true)
	List<PurchaseOrder> findLastMonthPO();

	
	 
	/*@Query(value ="SELECT * FROM purchase_order WHERE YEAR(purchase_order_date) = YEAR((NOW())) AND MONTH(purchase_order_date) = MONTH(NOW()) - 1 ORDER BY `purchase_order_date` DESC LIMIT 4");
	List<PurchaseOrder> findLastMonthPO();
*/
	@Query(value = "select party_id as partyId, MONTH(purchase_order_date)as month, monthname(purchase_order_date)as monthname,COUNT(purchase_order_id) as poCount  from purchase_orders\n" + 
			"GROUP BY MONTH(purchase_order_date);", nativeQuery = true)
	ArrayList<Map<String, Object>> findByPartyNMonthWiseCount();

	@Query(value = "select party_id as partyId, MONTH(purchase_order_date)as month, monthname(purchase_order_date)as monthname,COUNT(purchase_order_id) as poCount  from purchase_orders\n" + 
			"GROUP BY MONTH(purchase_order_date);", nativeQuery = true)
	ArrayList<Map<String, Object>> findByQty();
	
	List<PurchaseOrder> findByStatusIn(List<String> str);



	@Query(value="select pr.party_id,\n" + 
			"p.purchase_order_date,i.entry_date as inward_entry_date,\n" + 
			"avg(datediff(i.entry_date,\n" + 
			"p.purchase_order_date))as Average_date \n" + 
			"from item_inward i \n" + 
			"join purchase_orders\n" + 
			"p on i.purchase_order_id=p.purchase_order_id\n" + 
			"join party pr on p.party_id = pr.party_id \n" + 
			"group by pr.party_id",nativeQuery=true)
	ArrayList<Map<String, Object>> findByPartyAvgDateDiff();


	@Query(value ="select * from purchase_orders where purchase_order_number in(:purchaseOrderNumber)",nativeQuery = true)
	List<PurchaseOrder> findByPurchaseOrderNumber(@Param("purchaseOrderNumber")String purchaseOrderNumber);


	
	@Query(value = "SELECT * from item_inward where purchase_order_id IN (:poId)", nativeQuery = true)
	List<ItemInward> findByPurchaseOrderPurchaseOrderId(@Param("poId") Long poId);

	
	//@Query(value ="select * from purchase_orders where sales_order_id in(:salesOrderId)",nativeQuery = true)
	List<PurchaseOrder> findByPurchaseOrderItemsSalesOrderId(Long salesOrderId);



	@Query(value ="select * from purchase_orders where purchase_order_id in(:purchaseOrderId) or sales_order_id in(:salesOrderId) ", nativeQuery = true)
	List<PurchaseOrder> findByPurchaseOrderIdOrSalesOrderId(@Param("purchaseOrderId")Long purchaseOrderId, @Param("salesOrderId")Long salesOrderId);


	@Query(value ="select * from purchase_order_item inner join purchase_orders on\n" + 
			"purchase_order_item.purchase_order_id=purchase_orders.purchase_order_id where\n" + 
			"purchase_order_item.item_id = (:itemId)",nativeQuery = true)
	List<PurchaseOrder> findByPurchaseOrderItemsItemId(@Param("itemId")Long itemId);

	
	@Query(value ="select * from purchase_orders where party_id = (:partyId)",nativeQuery = true)
	List<PurchaseOrder> findByPartyId(@Param("partyId")Long partyId);

	@Query(value ="select * from purchase_orders where created_date = (:createdDate)",nativeQuery = true)
	List<PurchaseOrder> findBetweenCreatedDate(Instant createdDate);

	List<PurchaseOrder> findAllByCreatedDateBetween(Instant instantstart, Instant endinstant);



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
			"FROM purchase_orders where status in (\"active\", \"Active\") ;", nativeQuery = true)
	Object getActivePoByMnthWise();


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
			"FROM purchase_orders where status not in (\"active\", \"Active\") ;", nativeQuery = true)
	Object getClosedPoByMnthWise();



	@Query(value ="SELECT \n" + 
			" COUNT(CASE WHEN status = \"OPEN\"  THEN 1 ELSE NULL END)Open,\n" + 
			" COUNT(CASE WHEN  status = \"CLOSED\" THEN 1 ELSE NULL END)Closed\n"+
			"FROM purchase_orders;",nativeQuery=true)
	Map<String, Object> findByStatus();



}
