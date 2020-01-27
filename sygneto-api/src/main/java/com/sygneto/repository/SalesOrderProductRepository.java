package com.sygneto.repository;
import com.sygneto.domain.CategoryGroup;
import com.sygneto.domain.Contact;
import com.sygneto.domain.SalesOrder;
import com.sygneto.domain.SalesOrderProduct;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalesOrderProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesOrderProductRepository extends JpaRepository<SalesOrderProduct, Long> {

	

	//List<SalesOrderProduct> findBySalesOrderSalesOrderId(Long salesOrderId);
	
/*	@Query(value ="select new com.sygneto.domain.CategoryGroup (sp.product_id as productId, bi.item_id as itemId, bi.quantity * sp.quantity as quantity, sp.sales_order_id as salesOrderId)" + 
			"    from sales_order_item as sp,bill_of_material as bom, bom_item as bi where sp.sales_order_id = :id" + 
			"    and sp.product_Id = bom.product_id" + 
			"    and bom.product_bom_id = bi.product_bom_id", nativeQuery = true)
	public List<CategoryGroup> jointmultipleTable(@Param("id") Long id);

	@Query("select new com.sygneto.domain.CategoryGroup (sp.productid, bi.itemId, sp.salesOrderId)" + 
			"    from sales_order_item as sp,bill_of_material as bom, bom_item as bi where sp.sales_order_id = :id" + 
			"    and sp.product_Id = bom.product_id" + 
			"    and bom.product_bom_id = bi.product_bom_id")
	public List<CategoryGroup> jointmultipleTable(@Param("id") Long id);*/
	

}
