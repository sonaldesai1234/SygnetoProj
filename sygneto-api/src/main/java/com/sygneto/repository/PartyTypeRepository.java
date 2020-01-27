package com.sygneto.repository;
import com.sygneto.domain.PartyType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PartyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyTypeRepository extends JpaRepository<PartyType, Long> {

	

	Object findByPartyType(String partyType);

	Optional<PartyType> findOneByPartyType(String partyType);

	void deleteByPartyType(String partyType);

	@Query(value="select count(*) from PartyType")
	Long countByPartyType();


}
