package com.sygneto.repository;
import com.sygneto.domain.BillOfMaterial;
import com.sygneto.domain.CustomQueryForBOM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillOfMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillOfMaterialRepository extends JpaRepository<BillOfMaterial, Long> {

	List<BillOfMaterial> findByBomItemBomItemid(Long id);

//	@Query(value="select * from bill_of_material where product_id= (:productId) and version = (select max(version) from bill_of_material)", nativeQuery=true)
//	BillOfMaterial findByProductProductId(@Param("productId")Long productId);
	
	@Query(value="select * from bill_of_material where product_id= (:productId) and version= (:bomVersion) ", nativeQuery=true)
	BillOfMaterial findByProductProductId(@Param("productId")Long productId, @Param("bomVersion")float bomVersion);

	@Query(value="select product_id as productId,max(version) as version  from bill_of_material as bom  group by product_id;\n" + 
			"", nativeQuery=true)
	ArrayList<Map<String, String>> findAllByLetestVersion();

	/*@Query(value="select * from bill_of_material where product_id= (:keySet) and version= (:values) ", nativeQuery=true)
	List<BillOfMaterial> findByProductNversion(@Param("keySet") Set<String> keySet, @Param("values") Collection<String> values);
*/
	@Query(value="select * from bill_of_material where product_id= (:long1) and version= (:f) ", nativeQuery=true)
	BillOfMaterial findByProductNversion(@Param("long1") Long long1, @Param("f") float f);

	@Query("select new com.sygneto.domain.CustomQueryForBOM (bom.product.productId, max(bom.version))  from BillOfMaterial as bom  group by bom.product" + 
			"")
	public List<CustomQueryForBOM> findAllByversion();

	List<BillOfMaterial> findByProductProductId(Long productId);

}
