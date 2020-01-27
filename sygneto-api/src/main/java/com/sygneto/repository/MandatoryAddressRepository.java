package com.sygneto.repository;
import com.sygneto.domain.MandatoryAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MandatoryAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MandatoryAddressRepository extends JpaRepository<MandatoryAddress, Long> {

}
