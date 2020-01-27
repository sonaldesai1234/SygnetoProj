package com.sygneto.repository;
import com.sygneto.domain.SalesBOMItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillOfMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesBOMItemRepository extends JpaRepository<SalesBOMItem, Long> {

}
