package com.sygneto.repository;
import com.sygneto.domain.ContactType;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

	Optional<ContactType> findByContactType(String contactType);
	
	void deleteByContactType(String contactType);

	Optional<ContactType> findOneByContactType(String contactType);

}
