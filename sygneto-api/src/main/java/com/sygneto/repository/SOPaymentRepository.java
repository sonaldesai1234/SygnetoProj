package com.sygneto.repository;
import com.sygneto.domain.SOPayment;
import com.sygneto.domain.ItemCategory;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SOPaymentRepository extends JpaRepository<SOPayment, Long> {

	@Query(value ="SELECT * from so_payment WHERE sales_order_id = (:soId) ", nativeQuery = true)
	List<SOPayment> findBySalesOrderId(@Param("soId") Long soId);

}
