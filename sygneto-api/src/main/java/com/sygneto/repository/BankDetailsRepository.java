package com.sygneto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sygneto.domain.BankDetails;

@SuppressWarnings("unused")
@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {

	@Query(value ="SELECT * from bank_details WHERE party_id = (:partyId) ", nativeQuery = true)
	List<BankDetails> findByPartyId(@Param("partyId")Long partyId);
	
	
	

}
