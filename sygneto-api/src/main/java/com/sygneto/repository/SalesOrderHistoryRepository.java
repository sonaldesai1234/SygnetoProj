package com.sygneto.repository;
import com.sygneto.domain.SalesOrderHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalesOrderHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesOrderHistoryRepository extends JpaRepository<SalesOrderHistory, Long> {

}
