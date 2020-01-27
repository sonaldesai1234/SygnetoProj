package com.sygneto.repository;
import com.sygneto.domain.Address;
import com.sygneto.domain.Employees;
import com.sygneto.domain.Party;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Party entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {


	Object findByPartyId(String partyId);

	Optional<Party> findOneByPartyId(Long partyId);

	/*List<Party> findByPartyStatus(String status);*/


	Optional<Party> findOneByPartyName(String partyName);

	//List<Party> findAllByIsCustomer(String isCustomer);

   //@Query(value = "SELECT c FROM Party c where  isCustomer = ?isCustomer")
	List<Party> findAllByIsCustomer(@Param("isCustomer") String isCustomer);

  	List<Party> findByIsCustomer(String isCus);

  		List<Party> findByIsSupplier(String isSup);

  		List<Party> findByIsOrganisation(String isOrg);

  		@Query(value="select count(*) from Party")
		Long countByPartyId();

  		@Query(value="select count(*) from Party where is_supplier = 'yes' or is_supplier='true'")
		Long countByIsSupplier();

  		@Query(value="select count(*) from Party where is_customer='yes' or is_customer='true'")
		Long countByIsCustomer();


/*List<Party> findByIsSupplier();

List<Party> findByIsCustomer();*/
}
