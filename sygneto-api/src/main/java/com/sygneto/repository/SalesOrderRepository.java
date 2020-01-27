package com.sygneto.repository;
import com.sygneto.domain.Address;
import com.sygneto.domain.CategoryGroup;
import com.sygneto.domain.Contact;
import com.sygneto.domain.SalesOrder;
import com.sygneto.domain.SalesOrderBOM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalesOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

	List<SalesOrder> findByPartyId(Long id);

	@Query(value ="SELECT * from sales_order WHERE sales_order_number = (:salesOrderNumber) and sales_version = (:f) ", nativeQuery = true)
	SalesOrder FindBySalesOrderNumber(@Param("salesOrderNumber") String salesOrderNumber, @Param("f") float f);

	

	SalesOrder findOneBySalesOrderId(Long salesOrderId);



/*	@Query(value="SELECT \n" + 
			"		count(CASE WHEN  MONTH(shipping_date) = 1 THEN 1 ELSE NULL END) January\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 2 THEN 1 ELSE NULL END) Feburary\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 3 THEN 1 ELSE NULL END) March\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 4 THEN 1 ELSE NULL END) April\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 5 THEN 1 ELSE NULL END) May\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 6 THEN 1 ELSE NULL END) June\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 7 THEN 1 ELSE NULL END) July\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 8 THEN 1 ELSE NULL END) August\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 9 THEN 1 ELSE NULL END) September\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 10 THEN 1 ELSE NULL END) October\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 11 THEN 1 ELSE NULL END) November\n" + 
			"       , COUNT(CASE WHEN  MONTH(shipping_date) = 12 THEN 1 ELSE NULL END) December\n" + 
			"  \n" + 
			"FROM sales_order  where is_delivered not in (:isDelivered)",nativeQuery=true)
	List<SalesOrder> findByIsDelivered(@Param("isDelivered")String isDelivered);
*/
	
	


	@Query(value="SELECT \n" + 
			"		count(CASE WHEN  MONTH(created_date) = 1 THEN 1 ELSE NULL END) January\n" + 
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
			"FROM sales_order  where status not in (:status)",nativeQuery=true)
	ArrayList<Map<String, Object>> findByIsDeliveredOpen(@Param("status") String status);
    
	@Query(value="SELECT \n" + 
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
			"FROM sales_order\n" + 
			"where status not in (\"Closed\");",nativeQuery=true)
	Object getOpenSoByMnthWise();

	@Query(value="SELECT \n" + 
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
			"FROM sales_order\n" + 
			"where status  in (\"Closed\");",nativeQuery=true)
	Object getClosedSoByMnthWise();

	
	@Query(value="SELECT \n" + 
			" COUNT(CASE WHEN status = \"Pending\"  THEN 1 ELSE NULL END)Pending,\n" + 
			" COUNT(CASE WHEN  status = \"Awaiting Payment\" THEN 1 ELSE NULL END)AwaitingPayment,\n" + 
			" COUNT(CASE WHEN  status = \"Awaiting Pickup\"  THEN 1 ELSE NULL END)AwaitingPickup ,\n" + 
			" COUNT(CASE WHEN status = \"Shipped\"  THEN 1 ELSE NULL END)Shipped,\n" + 
			" COUNT(CASE WHEN status = \"Completed\"  THEN 1 ELSE NULL END)Completed,\n" + 
			" COUNT(CASE WHEN status = \"Cancelled\"  THEN 1 ELSE NULL END)Cancelled,\n" + 
			" COUNT(CASE WHEN status = \"Declined\"  THEN 1 ELSE NULL END)Declined,\n" + 
			" COUNT(CASE WHEN status = \"Refunded\"  THEN 1 ELSE NULL END)Refunded, \n" + 
			" COUNT(CASE WHEN status = \"Closed\"  THEN 1 ELSE NULL END)Closed\n" + 
			"FROM sales_order ;\n" + 
			"		" ,nativeQuery=true)
	Map<String, Object> findByStatus();


	


	

	//public List<CategoryGroup> jointmultipleTable(@Param("id") Long id);
	/*@Query(value ="select new com.sygneto.domain.CategoryGroup (sp.product_id as productId, bi.item_id as itemId, bi.quantity * sp.quantity as quantity, sp.sales_order_id as salesOrderId)" + 
	"    from sales_order_item as sp,bill_of_material as bom, bom_item as bi where sp.sales_order_id = :id" + 
	"    and sp.product_Id = bom.product_id" + 
	"    and bom.product_bom_id = bi.product_bom_id", nativeQuery = true)
public List<CategoryGroup> jointmultipleTable(@Param("id") Long id);*/

	//w
     /*@Query("select new com.sygneto.domain.CategoryGroup (sp.productId, so.partyName, so.salesOrderId) from SalesOrder so join so.salesOrderProduct sp")
      public List<CategoryGroup> jointmultipleTable();*/
	//w
	/*@Query("select new com.sygneto.domain.CategoryGroup (sp.productId, so.partyName, so.salesOrderId) from SalesOrder so, SalesOrderProduct sp where so.salesOrderId=sp.salesOrder")
    public List<CategoryGroup> jointmultipleTable();*/
	///////
	/*@Query("select new com.sygneto.domain.CategoryGroup (sp.productId,sp.productName, sp.quantity, bi.itemId, bi.itemName, bi.quantity, so.salesOrderId, sp.quantity*bi.quantity ) from SalesOrder so, SalesOrderProduct sp, BillOfMaterial bom, BOMItem as bi where so.salesOrderId= :id and so.salesOrderId=sp.salesOrder and sp.productId=bom.product and bom.productBomId=bi.billOfMaterial ")
    public List<CategoryGroup> jointmultipleTable(@Param("id") Long id);*/

	/*@Query("select new com.sygneto.domain.SalesOrderBOM (so.salesOrderId,so.partyName,new com.sygneto.domain.CustomProduct(sp.productId,sp.productName, sp.quantity,new com.sygneto.domain.SalesOrderBOMItem(bi.itemId,bi.unitOfMeasurement, bi.itemCode,sp.quantity*bi.quantity,bi.itemName))) from SalesOrder so, SalesOrderProduct sp, BillOfMaterial bom, BOMItem as bi where so.salesOrderId= :id and so.salesOrderId=sp.salesOrder and sp.productId=bom.product and bom.productBomId=bi.billOfMaterial")
    public List<SalesOrderBOM> jointmultipleTable(@Param("id") Long id);*/
}
