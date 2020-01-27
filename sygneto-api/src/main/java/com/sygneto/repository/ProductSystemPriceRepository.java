package com.sygneto.repository;
import com.sygneto.domain.ProductSystemPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductSystemPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSystemPriceRepository extends JpaRepository<ProductSystemPrice, Long> {

}
