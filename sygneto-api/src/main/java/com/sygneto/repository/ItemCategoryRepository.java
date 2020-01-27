package com.sygneto.repository;
import com.sygneto.domain.Employees;
import com.sygneto.domain.ItemCategory;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

	
	
}
