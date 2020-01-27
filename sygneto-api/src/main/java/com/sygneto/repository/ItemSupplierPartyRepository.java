package com.sygneto.repository;
import com.sygneto.domain.ItemSupplierParty;
import com.sygneto.domain.ItemCategory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemSupplierPartyRepository extends JpaRepository<ItemSupplierParty, Long> {

	List<ItemSupplierParty> findByPartyPartyId(Long id);

	List<ItemSupplierParty> findByItemItemId(Long id);

	List<ItemSupplierParty> findByItemItemIdIn(HashSet<Long> str);

	List<ItemSupplierParty> findByPartyPartyIdIn(HashSet<Long> partyId);

	/*@Query(value = "SELECT * from item_supplier_party WHERE item_id IN (:str) group by party_id", nativeQuery = true)
	List<ItemSupplierParty> findByItemmm(HashSet<Long> str);*/


}
