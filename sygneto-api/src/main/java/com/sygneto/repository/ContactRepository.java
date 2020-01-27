package com.sygneto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sygneto.domain.Contact;

/**
 * Spring Data repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findOneByContactId(Long contactId);

	@Query(value = "SELECT c FROM Contact c where c.party.partyId = :partyId")
	List<Contact> findAllByPartyId(@Param("partyId") Long partyId);

	Optional<Contact> findByPartyPartyId(Long id);
}
