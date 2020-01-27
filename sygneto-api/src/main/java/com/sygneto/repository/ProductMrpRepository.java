package com.sygneto.repository;
import com.sygneto.domain.Address;
import com.sygneto.domain.ProductMrp;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductMrp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductMrpRepository extends JpaRepository<ProductMrp, Long> {

	

	/*@Query(value = "SELECT c FROM Address c where c.party.partyId = :partyId")
	List<Address> findAllByPartyId(@Param("partyId") Long partyId);
	*/
	@Query(value = "from ProductMrp t where createdDate BETWEEN :validFrom AND :validTill")
	List<ProductMrp> findAllBetweenDates(@Param("validFrom")Instant validFrom, @Param("validTill") Instant validTill);

	

	


}
