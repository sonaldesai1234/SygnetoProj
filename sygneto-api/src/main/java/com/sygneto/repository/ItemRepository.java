package com.sygneto.repository;
import com.sygneto.domain.Item;
import com.sygneto.domain.ItemCategory;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByItemCategoryItemCategoryId(Long id);

	@Query(value="select count(*) from Item")
	Long findCount();

	

}
