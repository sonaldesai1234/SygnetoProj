package com.sygneto.repository;
import com.sygneto.domain.ItemPrice;
import com.sygneto.domain.ItemCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {

}
