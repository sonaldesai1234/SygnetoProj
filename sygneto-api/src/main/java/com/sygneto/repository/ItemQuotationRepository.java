package com.sygneto.repository;
import com.sygneto.domain.ItemQuotation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemQuotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemQuotationRepository extends JpaRepository<ItemQuotation, Long> {

}
