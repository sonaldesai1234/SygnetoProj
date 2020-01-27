package com.sygneto.repository;
import com.sygneto.domain.ItemPriceHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemPriceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPriceHistoryRepository extends JpaRepository<ItemPriceHistory, Long> {

	Optional<ItemPriceHistory> findByPartyId(Long partyId);

	Optional<ItemPriceHistory> findByItemId(Long itemId);

	List<ItemPriceHistory> findByItemIdAndPartyId(Long itemId, Long partyId);



}
