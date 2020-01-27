package com.sygneto.repository;
import com.sygneto.domain.PurchaseOrderItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderItemsRepository extends JpaRepository<PurchaseOrderItems, Long> {

}
