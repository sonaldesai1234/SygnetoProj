package com.sygneto.repository;
import com.sygneto.domain.PurchaseRequestItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseRequestItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseRequestItemRepository extends JpaRepository<PurchaseRequestItem, Long> {

}
