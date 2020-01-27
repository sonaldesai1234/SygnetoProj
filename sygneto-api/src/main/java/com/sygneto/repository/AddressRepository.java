package com.sygneto.repository;
import com.sygneto.domain.Address;
import com.sygneto.domain.Contact;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	@Query(value = "SELECT * FROM address where party_id IN (:partyId)", nativeQuery = true)
	List<Address> findAllByPartyId(@Param("partyId") Long partyId);

	List<Address> findByContactContactId(Long contactId);

	List<Address> findByEmployeesEmployeeId(Long employeeId);

	void deleteByEmployeesEmployeeId(Long employeeId);	

}
