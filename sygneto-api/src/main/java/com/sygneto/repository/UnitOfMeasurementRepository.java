package com.sygneto.repository;
import com.sygneto.domain.UnitOfMeasurement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the State entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitOfMeasurementRepository extends JpaRepository<UnitOfMeasurement, Long> {

}
